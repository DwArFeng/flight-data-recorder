package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.exception.PointNotExistsException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.TriggerHandler;
import com.dwarfeng.fdr.stack.service.EnabledFilterInfoLookupService;
import com.dwarfeng.fdr.stack.service.EnabledTriggerInfoLookupService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;

@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);

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
    private ServiceExceptionMapper sem;

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
                    //TODO
                    LOGGER.debug("数据信息未通过过滤, 过滤数据点信息: " + filteredValue);
                    return;
                }
            }
            // 3. 遍历所有触发器，获取所有的触发数据点。记录并广播触发信息。
            for (Trigger trigger : pointMeta.getTriggers()) {
                TriggeredValue triggeredValue = trigger.test(dataInfo);
                if (Objects.nonNull(triggeredValue)) {
                    //TODO
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
                //TODO
                LOGGER.debug("数据点实时数据记录使能, 实时数据信息: " + realtimeValue);
            }
            // 5. 如果数据点的持久数据使能，记录持久数据并广播。
            if (pointMeta.getPoint().isPersistenceEnabled()) {
                PersistenceValue persistenceValue = new PersistenceValue(
                        null,
                        pointKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue()
                );
                //TODO
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

        PointMeta pointMeta = infoFetcher.fetchPointMeta(pointKey);

        lock.writeLock().lock();
        try {
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
