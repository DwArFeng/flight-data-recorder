package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dutil.basic.cna.model.SyncMapModel;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.EnabledFilterMeta;
import com.dwarfeng.fdr.stack.bean.dto.EnabledTriggerMeta;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.exception.PointNotExistsException;
import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.fdr.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private FilteredValueMaintainService filteredValueMaintainService;
    @Autowired
    private TriggeredValueMaintainService triggeredValueMaintainService;
    @Autowired
    private PersistenceValueMaintainService persistenceValueMaintainService;
    @Autowired
    private RealtimeValueMaintainService realtimeValueMaintainService;

    @Autowired
    private FilterHandler filterHandler;
    @Autowired
    private TriggerHandler triggerHandler;
    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private EnabledFilterInfoLookupService enabledFilterInfoLookupService;
    @Autowired
    private EnabledTriggerInfoLookupService enabledTriggerInfoLookupService;

    @Autowired
    private SyncMapModel<LongIdKey, FilterMeta> filterMetaSyncMapModel;
    @Autowired
    private SyncMapModel<LongIdKey, TriggerMeta> triggerMetaSyncMapModel;

    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public void record(@NotNull DataInfo dataInfo) throws ServiceException {
        try {
            LOGGER.debug("记录数据信息: " + dataInfo);
            // 0. 获取dataInfo中的数据点ID。
            LongIdKey pointKey = new LongIdKey(dataInfo.getPointLongId());
            // 1. 判断数据点是否存在，并获取数据点。
            Point point = getPoint(pointKey);
            // 2. 获取数据点所有的过滤器。
            List<Filter> filters = getFilters(pointKey);
            // 3. 判断数据点是否通过所有的过滤器，任意一个过滤器未通过时，记录并广播过滤点信息并中止整个记录过程。
            for (Filter filter : filters) {
                FilteredValue filteredValue = filter.test(dataInfo);
                if (Objects.nonNull(filteredValue)) {
                    filteredValue.setKey(filteredValueMaintainService.insertOrUpdate(filteredValue));
                    eventHandler.fireDataFiltered(filteredValue);
                    LOGGER.debug("数据信息未通过过滤, 过滤数据点信息: " + filteredValue);
                    return;
                }
            }
            // 4. 获取数据点所有的触发器。
            List<Trigger> triggers = getTriggers(pointKey);
            // 5. 遍历所有触发器，获取所有的触发数据点。记录并广播触发信息。
            for (Trigger trigger : triggers) {
                TriggeredValue triggeredValue = trigger.test(dataInfo);
                if (Objects.nonNull(triggeredValue)) {
                    triggeredValue.setKey(triggeredValueMaintainService.insertOrUpdate(triggeredValue));
                    eventHandler.fireDataTriggered(triggeredValue);
                    LOGGER.debug("数据信息满足触发条件, 触发数据点信息: " + triggeredValue);
                }
            }
            // 6. 如果数据点的实时数据使能且数据的发生时间晚于之前的实时数据发生时间，记录实时数据并广播。
            if (point.isRealtimeEnabled() && isLastRealtimeValue(pointKey, dataInfo.getHappenedDate())) {
                RealtimeValue realtimeValue = new RealtimeValue(
                        pointKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue()
                );
                realtimeValueMaintainService.insertOrUpdate(realtimeValue);
                eventHandler.fireRealtimeUpdated(realtimeValue);
                LOGGER.debug("数据点实时数据记录使能, 实时数据信息: " + realtimeValue);
            }
            // 7. 如果数据点的持久数据使能，记录持久数据并广播。
            if (point.isPersistenceEnabled()) {
                PersistenceValue persistenceValue = new PersistenceValue(
                        null,
                        pointKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue()
                );
                persistenceValue.setKey(persistenceValueMaintainService.insertOrUpdate(persistenceValue));
                eventHandler.firePersistenceRecorded(persistenceValue);
                LOGGER.debug("数据点持久数据记录使能, 持久数据信息: " + persistenceValue);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("记录数据点时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    private Point getPoint(LongIdKey pointKey) throws Exception {
        if (!pointMaintainService.exists(pointKey)) {
            throw new PointNotExistsException(pointKey);
        }
        return pointMaintainService.get(pointKey);
    }

    private List<Filter> getFilters(LongIdKey pointKey) throws Exception {
        filterMetaSyncMapModel.getLock().writeLock().lock();
        try {
            //1. 如果不缓存中存在指定的元数据。
            if (filterMetaSyncMapModel.containsKey(pointKey)) {
                // 1.1 取出元数据。
                FilterMeta filterMeta = filterMetaSyncMapModel.get(pointKey);
                // 1.2 判断元数据是否过时，并执行相应的调度。
                if (!enabledFilterInfoLookupService.checkSerialVersion(pointKey, filterMeta.getSerialVersion())) {
                    EnabledFilterMeta enabledFilterInfos = enabledFilterInfoLookupService.getEnabledFilterInfos(pointKey);
                    filterMeta.setSerialVersion(enabledFilterInfos.getSerialVersion());
                    List<Filter> filters = new ArrayList<>();
                    for (FilterInfo filterInfo : enabledFilterInfos.getFilterInfos()) {
                        filters.add(filterHandler.make(filterInfo));
                    }
                    filterMeta.setFilters(filters);
                }
                filterMeta.setLastUsedDate(new Date());
                filterMetaSyncMapModel.put(pointKey, filterMeta);
                return filterMeta.getFilters();
            }
            //2. 如果不缓存中不存在指定的元数据。
            else {
                // 2.1 直接从服务中获取元数据。
                EnabledFilterMeta enabledFilterInfos = enabledFilterInfoLookupService.getEnabledFilterInfos(pointKey);
                // 2.2 将服务器中获取的元数据构造成服务中的元数据，并添加进缓存。
                List<Filter> filters = new ArrayList<>();
                for (FilterInfo filterInfo : enabledFilterInfos.getFilterInfos()) {
                    filters.add(filterHandler.make(filterInfo));
                }
                FilterMeta filterMeta = new FilterMeta(
                        enabledFilterInfos.getSerialVersion(),
                        filters,
                        new Date()
                );
                filterMetaSyncMapModel.put(pointKey, filterMeta);
                // 2.3 返回。
                return filterMeta.getFilters();
            }
        } finally {
            filterMetaSyncMapModel.getLock().writeLock().unlock();
        }
    }

    private List<Trigger> getTriggers(LongIdKey pointKey) throws Exception {
        triggerMetaSyncMapModel.getLock().writeLock().lock();
        try {
            //1. 如果不缓存中存在指定的元数据。
            if (triggerMetaSyncMapModel.containsKey(pointKey)) {
                // 1.1 取出元数据。
                TriggerMeta triggerMeta = triggerMetaSyncMapModel.get(pointKey);
                // 1.2 判断元数据是否过时，并执行相应的调度。
                if (!enabledTriggerInfoLookupService.checkSerialVersion(pointKey, triggerMeta.getSerialVersion())) {
                    EnabledTriggerMeta enabledTriggerInfos = enabledTriggerInfoLookupService.getEnabledTriggerInfos(pointKey);
                    triggerMeta.setSerialVersion(enabledTriggerInfos.getSerialVersion());
                    List<Trigger> triggers = new ArrayList<>();
                    for (TriggerInfo triggerInfo : enabledTriggerInfos.getTriggerInfos()) {
                        triggers.add(triggerHandler.make(triggerInfo));
                    }
                    triggerMeta.setTriggers(triggers);
                }
                triggerMeta.setLastUsedDate(new Date());
                triggerMetaSyncMapModel.put(pointKey, triggerMeta);
                return triggerMeta.getTriggers();
            }
            //2. 如果不缓存中不存在指定的元数据。
            else {
                // 2.1 直接从服务中获取元数据。
                EnabledTriggerMeta enabledTriggerInfos = enabledTriggerInfoLookupService.getEnabledTriggerInfos(pointKey);
                // 2.2 将服务器中获取的元数据构造成服务中的元数据，并添加进缓存。
                List<Trigger> triggers = new ArrayList<>();
                for (TriggerInfo triggerInfo : enabledTriggerInfos.getTriggerInfos()) {
                    triggers.add(triggerHandler.make(triggerInfo));
                }
                TriggerMeta triggerMeta = new TriggerMeta(
                        enabledTriggerInfos.getSerialVersion(),
                        triggers,
                        new Date()
                );
                triggerMetaSyncMapModel.put(pointKey, triggerMeta);
                // 2.3 返回。
                return triggerMeta.getTriggers();
            }
        } finally {
            triggerMetaSyncMapModel.getLock().writeLock().unlock();
        }
    }

    private boolean isLastRealtimeValue(LongIdKey pointKey, Date happenedDate) throws Exception {
        if (realtimeValueMaintainService.exists(pointKey)) {
            RealtimeValue realtimeValue = realtimeValueMaintainService.get(pointKey);
            return happenedDate.compareTo(realtimeValue.getHappenedDate()) > 0;
        } else {
            return true;
        }
    }

    public static class FilterMeta implements Bean {

        private static final long serialVersionUID = -7766487046031921961L;

        private long serialVersion;
        private List<Filter> filters;
        private Date lastUsedDate;

        public FilterMeta() {
        }

        public FilterMeta(long serialVersion, List<Filter> filters, Date lastUsedDate) {
            this.serialVersion = serialVersion;
            this.filters = filters;
            this.lastUsedDate = lastUsedDate;
        }

        public long getSerialVersion() {
            return serialVersion;
        }

        public void setSerialVersion(long serialVersion) {
            this.serialVersion = serialVersion;
        }

        public List<Filter> getFilters() {
            return filters;
        }

        public void setFilters(List<Filter> filters) {
            this.filters = filters;
        }

        public Date getLastUsedDate() {
            return lastUsedDate;
        }

        public void setLastUsedDate(Date lastUsedDate) {
            this.lastUsedDate = lastUsedDate;
        }

        @Override
        public String toString() {
            return "FilterMeta{" +
                    "serialVersion=" + serialVersion +
                    ", filters=" + filters +
                    ", lastUsedDate=" + lastUsedDate +
                    '}';
        }
    }

    public static class TriggerMeta implements Bean {

        private static final long serialVersionUID = 1747848935468792269L;

        private long serialVersion;
        private List<Trigger> triggers;
        private Date lastUsedDate;

        public TriggerMeta() {
        }

        public TriggerMeta(long serialVersion, List<Trigger> triggers, Date lastUsedDate) {
            this.serialVersion = serialVersion;
            this.triggers = triggers;
            this.lastUsedDate = lastUsedDate;
        }

        public long getSerialVersion() {
            return serialVersion;
        }

        public void setSerialVersion(long serialVersion) {
            this.serialVersion = serialVersion;
        }

        public List<Trigger> getTriggers() {
            return triggers;
        }

        public void setTriggers(List<Trigger> triggers) {
            this.triggers = triggers;
        }

        public Date getLastUsedDate() {
            return lastUsedDate;
        }

        public void setLastUsedDate(Date lastUsedDate) {
            this.lastUsedDate = lastUsedDate;
        }

        @Override
        public String toString() {
            return "TriggerMeta{" +
                    "serialVersion=" + serialVersion +
                    ", triggers=" + triggers +
                    ", lastUsedDate=" + lastUsedDate +
                    '}';
        }
    }

    @Component
    public static class TaskManager {

        @Autowired
        private SyncMapModel<LongIdKey, FilterMeta> filterMetaSyncMapModel;
        @Autowired
        private SyncMapModel<LongIdKey, TriggerMeta> triggerMetaSyncMapModel;

        @Value("${task.filter_cache_cleanup.offset}")
        private long filterCleanupOffset;
        @Value("${task.trigger_cache_cleanup.offset}")
        private long triggerCleanupOffset;

        @Scheduled(cron = "${task.filter_cache_cleanup.cron}")
        public void filterMapCleanup() {
            filterMetaSyncMapModel.getLock().writeLock().lock();
            try {
                List<LongIdKey> collect = filterMetaSyncMapModel.entrySet().stream()
                        .filter(entry -> (System.currentTimeMillis() - entry.getValue().getLastUsedDate().getTime()) >= filterCleanupOffset)
                        .map(Map.Entry::getKey).collect(Collectors.toList());
                filterMetaSyncMapModel.keySet().removeAll(collect);
            } finally {
                filterMetaSyncMapModel.getLock().writeLock().unlock();
            }
        }

        @Scheduled(cron = "${task.trigger_cache_cleanup.cron}")
        public void triggerMapCleanup() {
            triggerMetaSyncMapModel.getLock().writeLock().lock();
            try {
                List<LongIdKey> collect = triggerMetaSyncMapModel.entrySet().stream()
                        .filter(entry -> (System.currentTimeMillis() - entry.getValue().getLastUsedDate().getTime()) >= triggerCleanupOffset)
                        .map(Map.Entry::getKey).collect(Collectors.toList());
                triggerMetaSyncMapModel.keySet().removeAll(collect);
            } finally {
                triggerMetaSyncMapModel.getLock().writeLock().unlock();
            }
        }
    }
}
