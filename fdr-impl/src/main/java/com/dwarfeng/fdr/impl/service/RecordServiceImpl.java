package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.exception.PointNotExistsException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.TriggerHandler;
import com.dwarfeng.fdr.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReadWriteLock;

@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);
    private static final double PERFORMANCE_WARN_THRESHOLD = 2.0 / 3.0;

    @Autowired
    @Qualifier("recordServiceLock")
    private ReadWriteLock lock;

    @Autowired
    private Map<LongIdKey, PointMeta> pointMetaMap;
    @Autowired
    private Set<LongIdKey> notExistsPoints;

    @Autowired
    private InfoFetcher infoFetcher;
    @Autowired
    private RealtimeBlock realtimeBlock;
    @Autowired
    private PersistenceBlock persistenceBlock;

    @Autowired
    private KeyFetcher<LongIdKey> keyFetcher;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private ServiceExceptionMapper sem;

    @Value("${task.period.realtime_block.push_data}")
    private long realtimeBlockPushDataPeriod;
    @Value("${task.period.persistence_block.push_data}")
    private long persistenceBlockPushDataPeriod;

    private Set<ScheduledFuture<?>> scheduledFutures = new HashSet<>();

    @PostConstruct
    public void init() {
        ScheduledFuture<?> schedule;
        schedule = threadPoolTaskScheduler.scheduleAtFixedRate(() -> realtimeBlock.pushData(), new Date(System.currentTimeMillis() + realtimeBlockPushDataPeriod), realtimeBlockPushDataPeriod);
        scheduledFutures.add(schedule);
        schedule = threadPoolTaskScheduler.scheduleAtFixedRate(() -> persistenceBlock.pushData(), new Date(System.currentTimeMillis() + persistenceBlockPushDataPeriod), persistenceBlockPushDataPeriod);
        scheduledFutures.add(schedule);
    }

    @PreDestroy
    public void dispose() {
        scheduledFutures.forEach(scheduledFuture -> scheduledFuture.cancel(false));
        scheduledFutures.clear();
        realtimeBlock.pushData();
        persistenceBlock.pushData();
    }

    /**
     * 优化的记录方法。
     * <p>该记录方法经过优化，在记录期间，绝大部分数据不需要与缓存和数据访问层进行任何交互。尽一切可能的优化了执行效率。</p>
     * <p>仅当数据点第一次被调用的时候，该方法才会访问缓存和数据访问层，将元数据取出并缓存在内存后便不再需要继续访问。</p>
     *
     * @param dataInfo 指定的数据信息。
     * @throws ServiceException 服务异常。
     * @since 1.2.0
     */
    @Override
    @BehaviorAnalyse
    public void record(@NotNull DataInfo dataInfo) throws ServiceException {
        try {
            // 0. 记录日志，准备工作。
            LOGGER.debug("记录数据信息: " + dataInfo);
            LongIdKey pointKey = new LongIdKey(dataInfo.getPointLongId());
            // 1. 获取 PointMeta。
            PointMeta pointMeta = getPointMeta(pointKey, pointMetaMap, notExistsPoints);
            // 2. 判断数据点是否通过所有的过滤器，任意一个过滤器未通过时，记录并广播过滤点信息并中止整个记录过程。
            for (Filter filter : pointMeta.getFilters()) {
                FilteredValue filteredValue = filter.test(dataInfo);
                if (Objects.nonNull(filteredValue)) {
                    filteredValue.setKey(keyFetcher.fetchKey());
                    persistenceBlock.appendFilteredValue(filteredValue);
                    //TODO 事件
                    LOGGER.debug("数据信息未通过过滤, 过滤数据点信息: " + filteredValue);
                    return;
                }
            }
            // 3. 遍历所有触发器，获取所有的触发数据点。记录并广播触发信息。
            for (Trigger trigger : pointMeta.getTriggers()) {
                TriggeredValue triggeredValue = trigger.test(dataInfo);
                if (Objects.nonNull(triggeredValue)) {
                    triggeredValue.setKey(keyFetcher.fetchKey());
                    persistenceBlock.appendTriggeredValue(triggeredValue);
                    //TODO 事件
                    LOGGER.debug("数据信息满足触发条件, 触发数据点信息: " + triggeredValue);
                }
            }
            // 4. 如果数据点的实时数据使能且数据的发生时间晚于之前的实时数据发生时间，记录实时数据并广播。
            if (pointMeta.getPoint().isRealtimeEnabled()) {
                RealtimeValue realtimeValue = new RealtimeValue(
                        pointKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue()
                );
                realtimeBlock.append(realtimeValue);
                LOGGER.debug("数据点实时数据记录使能, 实时数据信息: " + realtimeValue);
            }
            // 5. 如果数据点的持久数据使能，记录持久数据并广播。
            if (pointMeta.getPoint().isPersistenceEnabled()) {
                PersistenceValue persistenceValue = new PersistenceValue(
                        keyFetcher.fetchKey(),
                        pointKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue()
                );
                persistenceBlock.appendPersistenceValue(persistenceValue);
                //TODO 事件
                LOGGER.debug("数据点持久数据记录使能, 持久数据信息: " + persistenceValue);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("记录数据信息时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    private PointMeta getPointMeta(LongIdKey pointKey, Map<LongIdKey, PointMeta> pointMetaMap,
                                   Set<LongIdKey> notExistsPoints) throws Exception {
        lock.readLock().lock();
        try {
            if (notExistsPoints.contains(pointKey)) {
                throw new PointNotExistsException(pointKey);
            }

            if (pointMetaMap.containsKey(pointKey)) {
                return pointMetaMap.get(pointKey);
            }
        } finally {
            lock.readLock().unlock();
        }

        lock.writeLock().lock();
        try {
            // 二次确认。
            if (notExistsPoints.contains(pointKey)) {
                throw new PointNotExistsException(pointKey);
            }
            if (pointMetaMap.containsKey(pointKey)) {
                return pointMetaMap.get(pointKey);
            }

            PointMeta pointMeta = infoFetcher.fetchPointMeta(pointKey);

            if (Objects.isNull(pointMeta)) {
                notExistsPoints.add(pointKey);
                throw new PointNotExistsException(pointKey);
            } else {
                pointMetaMap.put(pointKey, pointMeta);
                return pointMeta;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Component
    public static class InfoFetcher {

        @Autowired
        private PointMaintainService pointMaintainService;
        @Autowired
        private EnabledFilterInfoLookupService enabledFilterInfoLookupService;
        @Autowired
        private EnabledTriggerInfoLookupService enabledTriggerInfoLookupService;

        @Autowired
        private FilterHandler filterHandler;
        @Autowired
        private TriggerHandler triggerHandler;

        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
        public PointMeta fetchPointMeta(LongIdKey pointKey) throws Exception {
            if (!pointMaintainService.exists(pointKey)) {
                return null;
            }

            Point point = pointMaintainService.get(pointKey);
            List<FilterInfo> filterInfos = enabledFilterInfoLookupService.getEnabledFilterInfos(pointKey);
            List<TriggerInfo> triggerInfos = enabledTriggerInfoLookupService.getEnabledTriggerInfos(pointKey);

            List<Filter> filters = new ArrayList<>();
            List<Trigger> triggers = new ArrayList<>();

            for (FilterInfo filterInfo : filterInfos) {
                filters.add(filterHandler.make(filterInfo));
            }
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggers.add(triggerHandler.make(triggerInfo));
            }

            return new PointMeta(
                    point,
                    filters,
                    triggers
            );
        }
    }

    @Component
    public static class RealtimeBlock {

        private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeBlock.class);

        @Autowired
        @Qualifier("realtimeBlockLock")
        private ReadWriteLock lock;

        @Autowired
        private Map<LongIdKey, RealtimeValue> realtimeValueMap;

        @Autowired
        private PointMaintainService pointMaintainService;
        @Autowired
        private RealtimeValueMaintainService realtimeValueMaintainService;

        @Value("${task.period.realtime_block.push_data}")
        private long pushDataPeriod;

        public void append(@NonNull RealtimeValue realtimeValue) {
            lock.writeLock().lock();
            try {
                if (realtimeValueMap.containsKey(realtimeValue.getKey())) {
                    int compareResult = realtimeValue.getHappenedDate()
                            .compareTo(realtimeValueMap.get(realtimeValue.getKey()).getHappenedDate());
                    if (compareResult > 0) {
                        realtimeValueMap.put(realtimeValue.getKey(), realtimeValue);
                    }
                } else {
                    realtimeValueMap.put(realtimeValue.getKey(), realtimeValue);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager")
        public void pushData() {
            TimeMeasurer tm = new TimeMeasurer();
            tm.start();
            try {
                Map<LongIdKey, RealtimeValue> detachedMap;
                lock.writeLock().lock();
                try {
                    detachedMap = new HashMap<>(realtimeValueMap);
                    realtimeValueMap.clear();
                } finally {
                    lock.writeLock().unlock();
                }

                LOGGER.info("RealtimeBlock.pushData 方法周期性执行，推送实时数据 " + detachedMap.size() + "个...");
                for (Map.Entry<LongIdKey, RealtimeValue> entry : detachedMap.entrySet()) {
                    if (realtimeValueMaintainService.exists(entry.getKey())) {
                        if (!pointMaintainService.exists(entry.getKey())) {
                            break;
                        }
                        int compareResult = entry.getValue().getHappenedDate()
                                .compareTo(realtimeValueMaintainService.get(entry.getKey()).getHappenedDate());
                        if (compareResult > 0) {
                            realtimeValueMaintainService.update(entry.getValue());
                        }
                    } else {
                        realtimeValueMaintainService.insert(entry.getValue());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("推送实时数据时发生异常", e);
            } finally {
                tm.stop();
                long timeMs = tm.getTimeMs();
                if (((double) timeMs / pushDataPeriod) >= PERFORMANCE_WARN_THRESHOLD) {
                    String format = String.format("RealtimeBlock.pushData 方法执行耗时与执行周期之比为 %.2f, 超过警戒阈值, " +
                            "请留意可能引发的性能问题", ((double) timeMs / pushDataPeriod));
                    LOGGER.warn(format);
                }
                if (timeMs >= pushDataPeriod) {
                    LOGGER.error("RealtimeBlock.pushData 方法执行耗时超过周期间隔，程序正在面临性能问题");
                }
            }
        }
    }

    @Component
    public static class PersistenceBlock {

        @Autowired
        @Qualifier("persistenceBlockLock")
        private ReadWriteLock lock;

        @Autowired
        private List<PersistenceValue> persistenceValues;
        @Autowired
        private List<FilteredValue> filteredValues;
        @Autowired
        private List<TriggeredValue> triggeredValues;

        @Autowired
        private PointMaintainService pointMaintainService;
        @Autowired
        private PersistenceValueMaintainService persistenceValueMaintainService;
        @Autowired
        private FilteredValueMaintainService filteredValueMaintainService;
        @Autowired
        private TriggeredValueMaintainService triggeredValueMaintainService;

        @Value("${task.period.persistence_block.push_data}")
        private long pushDataPeriod;

        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager")
        public void appendPersistenceValue(PersistenceValue persistenceValue) {
            lock.writeLock().lock();
            try {
                persistenceValues.add(persistenceValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        public void appendFilteredValue(FilteredValue filteredValue) {
            lock.writeLock().lock();
            try {
                filteredValues.add(filteredValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        public void appendTriggeredValue(TriggeredValue triggeredValue) {
            lock.writeLock().lock();
            try {
                triggeredValues.add(triggeredValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        @BehaviorAnalyse
        @Transactional(transactionManager = "hibernateTransactionManager")
        public void pushData() {
            TimeMeasurer tm = new TimeMeasurer();
            tm.start();
            try {
                List<PersistenceValue> detachedPersistenceValues;
                List<FilteredValue> detachedFilteredValues;
                List<TriggeredValue> detachedTriggeredValues;
                lock.writeLock().lock();
                try {
                    detachedPersistenceValues = new ArrayList<>(persistenceValues);
                    detachedFilteredValues = new ArrayList<>(filteredValues);
                    detachedTriggeredValues = new ArrayList<>(triggeredValues);
                    persistenceValues.clear();
                    filteredValues.clear();
                    triggeredValues.clear();
                } finally {
                    lock.writeLock().unlock();
                }

                LOGGER.info("PersistenceBlock.pushData 方法周期性执行，推送持久数据 " + detachedPersistenceValues.size()
                        + " 个, 过滤数据 " + detachedFilteredValues.size() + " 个, 触发数据 "
                        + detachedTriggeredValues.size() + " 个...");
                Set<LongIdKey> notExistsPoints = new HashSet<>();
                Set<LongIdKey> existsPoints = new HashSet<>();
                for (Iterator<PersistenceValue> iter = detachedPersistenceValues.iterator(); iter.hasNext(); ) {
                    PersistenceValue persistenceValue = iter.next();
                    if (existsPoints.contains(persistenceValue.getPointKey())) {
                        continue;
                    }
                    if (notExistsPoints.contains(persistenceValue.getPointKey())) {
                        iter.remove();
                        continue;
                    }
                    if (pointMaintainService.exists(persistenceValue.getPointKey())) {
                        existsPoints.add(persistenceValue.getPointKey());
                    } else {
                        notExistsPoints.add(persistenceValue.getPointKey());
                        iter.remove();
                    }
                }
                for (Iterator<FilteredValue> iter = detachedFilteredValues.iterator(); iter.hasNext(); ) {
                    FilteredValue filteredValue = iter.next();
                    if (existsPoints.contains(filteredValue.getPointKey())) {
                        continue;
                    }
                    if (notExistsPoints.contains(filteredValue.getPointKey())) {
                        iter.remove();
                        continue;
                    }
                    if (pointMaintainService.exists(filteredValue.getPointKey())) {
                        existsPoints.add(filteredValue.getPointKey());
                    } else {
                        notExistsPoints.add(filteredValue.getPointKey());
                        iter.remove();
                    }
                }
                for (Iterator<TriggeredValue> iter = detachedTriggeredValues.iterator(); iter.hasNext(); ) {
                    TriggeredValue triggeredValue = iter.next();
                    if (existsPoints.contains(triggeredValue.getPointKey())) {
                        continue;
                    }
                    if (notExistsPoints.contains(triggeredValue.getPointKey())) {
                        iter.remove();
                        continue;
                    }
                    if (pointMaintainService.exists(triggeredValue.getPointKey())) {
                        existsPoints.add(triggeredValue.getPointKey());
                    } else {
                        notExistsPoints.add(triggeredValue.getPointKey());
                        iter.remove();
                    }
                }
                persistenceValueMaintainService.batchInsert(detachedPersistenceValues);
                filteredValueMaintainService.batchInsert(detachedFilteredValues);
                triggeredValueMaintainService.batchInsert(detachedTriggeredValues);
            } catch (Exception e) {
                LOGGER.error("推送持久数据时发生异常", e);
            } finally {
                tm.stop();
                long timeMs = tm.getTimeMs();
                if (((double) timeMs / pushDataPeriod) >= PERFORMANCE_WARN_THRESHOLD) {
                    String format = String.format("PersistenceBlock.pushData 方法执行耗时与执行周期之比为 %.2f, 超过警戒阈值, " +
                            "请留意可能引发的性能问题", ((double) timeMs / pushDataPeriod));
                    LOGGER.warn(format);
                }
                if (timeMs >= pushDataPeriod) {
                    LOGGER.error("PersistenceBlock.pushData 方法执行耗时超过周期间隔，程序正在面临性能问题");
                }
            }
        }
    }

    @Component
    public static class EventBlock {

    }

    /**
     * 数据点元数据。
     *
     * @author DwArFeng
     * @since 1.2.0.a
     */
    public static class PointMeta implements Dto {

        private static final long serialVersionUID = -3206751219977190478L;

        private Point point;
        private List<Filter> filters;
        private List<Trigger> triggers;

        public PointMeta() {
        }

        public PointMeta(Point point, List<Filter> filters, List<Trigger> triggers) {
            this.point = point;
            this.filters = filters;
            this.triggers = triggers;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public List<Filter> getFilters() {
            return filters;
        }

        public void setFilters(List<Filter> filters) {
            this.filters = filters;
        }

        public List<Trigger> getTriggers() {
            return triggers;
        }

        public void setTriggers(List<Trigger> triggers) {
            this.triggers = triggers;
        }

        @Override
        public String toString() {
            return "PointMeta{" +
                    "point=" + point +
                    ", filters=" + filters +
                    ", triggers=" + triggers +
                    '}';
        }
    }
}
