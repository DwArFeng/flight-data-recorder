package com.dwarfeng.fdr.impl.service.record.service;

import com.dwarfeng.fdr.sdk.crud.*;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.cache.*;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.dao.RealtimeValueDao;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
import com.dwarfeng.fdr.stack.exception.*;
import com.dwarfeng.fdr.stack.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Component
@Validated
public class RecordServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceDelegate.class);

    @Autowired
    private AsyncBean asyncBean;

    @Autowired
    private PointCrudHelper pointCrudHelper;
    @Autowired
    private FilteredValueCrudHelper filteredValueCrudHelper;
    @Autowired
    private TriggeredValueCrudHelper triggeredValueCrudHelper;
    @Autowired
    private PersistenceValueCrudHelper persistenceValueCrudHelper;
    @Autowired
    private RealtimeValueCrudHelper realtimeValueCrudHelper;

    @Autowired
    private PointHasFilterInfoCache pointHasFilterInfoCache;
    @Autowired
    private PointHasTriggerInfoCache pointHasTriggerInfoCache;
    @Autowired
    private FilterInfoDao filterInfoDao;
    @Autowired
    private FilterInfoCache filterInfoCache;
    @Autowired
    private TriggerInfoDao triggerInfoDao;
    @Autowired
    private TriggerInfoCache triggerInfoCache;
    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCache realtimeValueCache;
    @Autowired
    private FilterHandler filterHandler;
    @Autowired
    private TriggerHandler triggerHandler;
    @Autowired
    private EventHandler eventHandler;

    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;
    @Value("${cache.timeout.entity.trigger_info}")
    private long triggerInfoTimeout;
    @Value("${cache.timeout.entity.realtime_value}")
    private long realtimeValueTimeout;

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public RecordResult record(@NotNull DataInfo dataInfo) throws ServiceException {
        //定义变量。
        GuidKey pointKey;
        Point point;
        List<FilterInfo> filterInfos;
        List<TriggerInfo> triggerInfos;
        List<Filter> filters = new ArrayList<>();
        List<Trigger> triggers = new ArrayList<>();
        //定义待返回结果。
        RecordResult recordResult = new RecordResult(
                dataInfo,
                false,
                null,
                false,
                new ArrayList<>(),
                false,
                null,
                false,
                null
        );

        /*
         * 数据记录详细逻辑。
         *   1. 获得数据点。
         *   2. 获得数据点的所有过滤器信息。
         *   3. 取出数据点的所有触发器信息。
         *   4. 过滤器信息构造过滤器。
         *   5. 触发器信息构造触发器。
         *   6. 过滤器进行过滤，判断不通过的情况。
         *   7. 如果通过过滤且数据点允许持久化，生成持久化数据值。
         *   8. 如果通过过滤且数据点允许实时记录，生成实时数据。
         *   9. 如果通过过滤，处理所有触发器，获取所有触发器信息。
         *   10. 处理记录结果并返回。
         */
        //1. 获得数据点。
        pointKey = new GuidKey(dataInfo.getPointGuid());
        try {
            LOGGER.debug("1. 获得数据点...");
            point = pointCrudHelper.noAdviseGet(pointKey);
        } catch (CacheException | DaoException e) {
            LOGGER.warn("数据点实体 " + pointKey + " 获取失败，将抛出异常...");
            throw new ServiceException(e);
        }
        //2. 获得数据点的所有过滤器信息。
        try {
            LOGGER.debug("2. 获得数据点的所有过滤器信息...");
            filterInfos = getFilterInfos(pointKey);
        } catch (CacheException | DaoException e) {
            LOGGER.warn("数据点实体 " + pointKey + " 对应的过滤器列表获取失败，将抛出异常...");
            throw new ServiceException(e);
        }
        //3. 取出数据点的所有触发器信息。
        try {
            LOGGER.debug("3. 取出数据点的所有触发器信息...");
            triggerInfos = getTriggerInfos(pointKey);
        } catch (CacheException | DaoException e) {
            LOGGER.warn("数据点实体 " + pointKey + " 对应的触发器列表获取失败，将抛出异常...");
            throw new ServiceException(e);
        }

        //4. 过滤器信息构造过滤器。
        try {
            LOGGER.debug("4. 过滤器信息构造过滤器...");
            for (FilterInfo filterInfo : filterInfos) {
                if (filterInfo.isEnabled()) {
                    LOGGER.debug("过滤器信息 " + filterInfo.toString() + " 使能, 将构造过滤器...");
                    filters.add(filterHandler.make(
                            filterInfo.getPointKey().getGuid(),
                            filterInfo.getKey().getGuid(),
                            filterInfo.getContent()
                    ));
                } else {
                    LOGGER.debug("过滤器信息 " + filterInfo.toString() + " 未使能, 将忽略...");
                }
            }
        } catch (FilterException e) {
            LOGGER.warn("过滤器构造失败, 将抛出异常...");
            throw new ServiceException(e);
        }
        //5. 触发器信息构造触发器。
        try {
            LOGGER.debug("5. 触发器信息构造触发器...");
            for (TriggerInfo triggerInfo : triggerInfos) {
                if (triggerInfo.isEnabled()) {
                    LOGGER.debug("触发器信息 " + triggerInfo.toString() + " 使能, 将构造触发器...");
                    triggers.add(triggerHandler.make(
                            triggerInfo.getPointKey().getGuid(),
                            triggerInfo.getKey().getGuid(),
                            triggerInfo.getContent()
                    ));
                } else {
                    LOGGER.debug("触发器信息 " + triggerInfo.toString() + " 未使能, 将忽略...");
                }
            }
        } catch (TriggerException e) {
            LOGGER.warn("触发器构造失败, 将抛出异常...");
            throw new ServiceException(e);
        }

        //6. 过滤器进行过滤，判断不通过的情况。
        try {
            for (Filter filter : filters) {
                LOGGER.debug("6. 过滤器进行过滤，判断不通过的情况...");
                FilteredValueConsumer consumer = new FilteredValueConsumer();
                filter.test(dataInfo, consumer);
                if (consumer.isFiltered()) {
                    LOGGER.debug("过滤器被触发, 相关点位为 " + consumer.getFilteredValue().toString() + " 将中断后续操作...");
                    recordResult.setFiltered(true);
                    recordResult.setFilteredValue(consumer.getFilteredValue());
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("过滤器过滤失败, 将抛出异常...");
            throw new ServiceException(e);
        }

        //7. 如果通过过滤且数据点允许持久化，生成持久化数据值。
        if (!recordResult.isFiltered() && point.isPersistenceEnabled()) {
            LOGGER.debug("7. 如果通过过滤且数据点允许持久化，生成持久化数据值... 满足");
            recordResult.setPersistenceRecorded(true);
            recordResult.setPersistenceValue(new PersistenceValue(
                    null,
                    pointKey,
                    dataInfo.getHappenedDate(),
                    dataInfo.getValue()
            ));
        } else {
            LOGGER.debug("7. 如果通过过滤且数据点允许持久化，生成持久化数据值... 不满足");
        }

        //8. 如果通过过滤且数据点允许实时记录，生成实时数据。
        if (!recordResult.isFiltered() && point.isRealtimeEnabled()) {
            LOGGER.debug("8. 如果通过过滤且数据点允许实时记录，生成实时数据... 满足");
            RealtimeValue realtimeValue = getRealtimeValueOrNull(pointKey);
            if (Objects.isNull(realtimeValue) || realtimeValue.getHappenedDate().getTime() < dataInfo.getHappenedDate().getTime()) {
                LOGGER.debug("8.1 如果数据点在此之前没有实时数据或旧的实时数据发生时间小于新的实时数据，生成实时数据... 满足");
                recordResult.setRealtimeRecorded(true);
                recordResult.setRealtimeValue(new RealtimeValue(
                        pointKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue()
                ));
            } else {
                LOGGER.debug("8.1 如果数据点在此之前没有实时数据或旧的实时数据发生时间小于新的实时数据，生成实时数据... 不满足");
            }
        } else {
            LOGGER.debug("8. 如果通过过滤且数据点允许实时记录，生成实时数据... 不满足");
        }

        //9. 如果通过过滤，处理所有触发器，获取所有触发器信息。
        if (!recordResult.isFiltered()) {
            try {
                LOGGER.debug("9. 如果通过过滤，处理所有触发器，获取所有触发器信息... 满足");
                for (Trigger trigger : triggers) {
                    TriggeredValueConsumer consumer = new TriggeredValueConsumer();
                    trigger.test(dataInfo, consumer);
                    if (consumer.isTriggered()) {
                        LOGGER.debug("过滤器被触发, 相关点位为 " + consumer.getTriggeredValue().toString() + " 将记录点位信息，以用于后续操作...");
                        recordResult.setTriggered(true);
                        recordResult.getTriggeredValues().add(consumer.getTriggeredValue());
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("触发器触发失败, 将抛出异常...");
                throw new ServiceException(e);
            }
        } else {
            LOGGER.debug("9. 如果通过过滤，处理所有触发器，获取所有触发器信息... 不满足");
        }

        //10. 处理记录结果并返回。
        LOGGER.debug("10. 处理记录结果并返回...");
        try {
            return processRecordResult(recordResult);
        } catch (Exception e) {
            LOGGER.warn("记录处理失败, 将抛出异常...");
            throw new ServiceException(ServiceExceptionCodes.UNDEFINE, e);
        }
    }

    public RecordResult processRecordResult(RecordResult recordResult) throws EventException, ServiceException {
        if (recordResult.isFiltered()) {
            LOGGER.debug("检测到数据点被过滤, 记录被过滤信息...");
            filteredValueCrudHelper.insert(recordResult.getFilteredValue());
            // 如果点位不通过过滤，则后续方法根本无法进行，所以不用判断之后的信息，直接返回。
            return recordResult;
        }
        if (recordResult.isPersistenceRecorded()) {
            LOGGER.debug("检测到数据允许记录持久化信息, 记录之...");
            persistenceValueCrudHelper.insert(recordResult.getPersistenceValue());
        }
        if (recordResult.isRealtimeRecorded()) {
            LOGGER.debug("检测到数据允许记录实时信息, 记录或更新之...");
            realtimeValueCrudHelper.insertOrUpdateRealtimeValue(recordResult.getRealtimeValue());
        }
        if (recordResult.isTriggered()) {
            LOGGER.debug("检测到数据被触发, 记录所有触发信息...");
            for (TriggeredValue triggeredValue : recordResult.getTriggeredValues()) {
                LOGGER.debug("记录单条触发信息 " + triggeredValue + "...");
                triggeredValueCrudHelper.insert(triggeredValue);
                LOGGER.debug("发送数据触发广播 " + triggeredValue + "...");
                eventHandler.fireDataTriggered(triggeredValue);
            }
        }
        return recordResult;
    }

    @TimeAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public RealtimeValue getRealtimeValueOrNull(GuidKey key) throws ServiceException {
        try {
            if (realtimeValueCache.exists(key)) {
                LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
                return realtimeValueCache.get(key);
            } else if (!realtimeValueDao.exists(key)) {
                LOGGER.debug("未能在持久层中找到 " + key.toString() + " 对应的值，返回null...");
                return null;
            } else {
                LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
                RealtimeValue realtimeValue = realtimeValueDao.get(key);
                LOGGER.debug("将读取到的值 " + realtimeValue.toString() + " 回写到缓存中...");
                realtimeValueCache.push(key, realtimeValue, realtimeValueTimeout);
                return realtimeValue;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private List<TriggerInfo> getTriggerInfos(GuidKey pointKey) throws CacheException, DaoException {
        List<TriggerInfo> triggerInfos;

        if (pointHasTriggerInfoCache.exists(pointKey)) {
            LOGGER.debug("在缓存中发现了 " + pointKey.toString() + " 对应的子项列表，直接返回缓存中的值...");
            triggerInfos = pointHasTriggerInfoCache.get(pointKey, LookupPagingInfo.LOOKUP_ALL);
        } else {
            LOGGER.debug("查询指定的TriggerInfo对应的子项...");
            triggerInfos = triggerInfoDao.getTriggerInfos(pointKey, LookupPagingInfo.LOOKUP_ALL);
            if (triggerInfos.size() > 0) {
                for (TriggerInfo triggerInfo : triggerInfos) {
                    LOGGER.debug("将查询到的的实体 " + triggerInfo.toString() + " 插入缓存中...");
                    triggerInfoCache.push(triggerInfo.getKey(), triggerInfo, triggerInfoTimeout);
                }
                LOGGER.debug("抓取实体 " + pointKey.toString() + " 对应的子项并插入缓存...");
                asyncBean.fetchTriggerInfo2Cache(pointKey);
            }
        }
        return triggerInfos;
    }

    private List<FilterInfo> getFilterInfos(GuidKey pointKey) throws CacheException, DaoException {
        List<FilterInfo> filterInfos;

        if (pointHasFilterInfoCache.exists(pointKey)) {
            LOGGER.debug("在缓存中发现了 " + pointKey.toString() + " 对应的子项列表，直接返回缓存中的值...");
            filterInfos = pointHasFilterInfoCache.get(pointKey, LookupPagingInfo.LOOKUP_ALL);
        } else {
            LOGGER.debug("查询指定的FilterInfo对应的子项...");
            filterInfos = filterInfoDao.getFilterInfos(pointKey, LookupPagingInfo.LOOKUP_ALL);
            if (filterInfos.size() > 0) {
                for (FilterInfo filterInfo : filterInfos) {
                    LOGGER.debug("将查询到的的实体 " + filterInfo.toString() + " 插入缓存中...");
                    filterInfoCache.push(filterInfo.getKey(), filterInfo, filterInfoTimeout);
                }
                LOGGER.debug("抓取实体 " + pointKey.toString() + " 对应的子项并插入缓存...");
                asyncBean.fetchFilterInfo2Cache(pointKey);
            }
        }
        return filterInfos;
    }

    @Component
    public static class AsyncBean {

        @Autowired
        private PointHasFilterInfoCache pointHasFilterInfoCache;
        @Autowired
        private PointHasTriggerInfoCache pointHasTriggerInfoCache;
        @Autowired
        private FilterInfoDao filterInfoDao;
        @Autowired
        private TriggerInfoDao triggerInfoDao;

        @Value("${cache.timeout.one_to_many.point_has_filter_info}")
        private long pointHasFilterInfoTimeout;
        @Value("${cache.batch_fetch_size.filter_info}")
        private int filterInfoFetchSize;
        @Value("${cache.timeout.one_to_many.point_has_trigger_info}")
        private long pointHasTriggerInfoTimeout;
        @Value("${cache.batch_fetch_size.trigger_info}")
        private int triggerInfoFetchSize;

        @Transactional(transactionManager = "hibernateTransactionManager")
        @TimeAnalyse
        @Async
        public void fetchTriggerInfo2Cache(GuidKey guidKey) {
            try {
                int totlePage;
                int currPage;
                long count = triggerInfoDao.getTriggerInfoCount(guidKey);
                totlePage = Math.max((int) Math.ceil((double) count / triggerInfoFetchSize), 1);
                currPage = 0;
                pointHasTriggerInfoCache.delete(guidKey);
                do {
                    LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, triggerInfoFetchSize);
                    List<TriggerInfo> childs = triggerInfoDao.getTriggerInfos(guidKey, lookupPagingInfo);
                    if (childs.size() > 0) {
                        pointHasTriggerInfoCache.push(guidKey, childs, pointHasTriggerInfoTimeout);
                    }
                } while (currPage < totlePage);
            } catch (Exception e) {
                LOGGER.warn("将分类 " + guidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
            }
        }

        @Transactional(transactionManager = "hibernateTransactionManager")
        @TimeAnalyse
        @Async
        public void fetchFilterInfo2Cache(GuidKey guidKey) {
            try {
                int totlePage;
                int currPage;
                long count = filterInfoDao.getFilterInfoCount(guidKey);
                totlePage = Math.max((int) Math.ceil((double) count / filterInfoFetchSize), 1);
                currPage = 0;
                pointHasFilterInfoCache.delete(guidKey);
                do {
                    LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, filterInfoFetchSize);
                    List<FilterInfo> childs = filterInfoDao.getFilterInfos(guidKey, lookupPagingInfo);
                    if (childs.size() > 0) {
                        pointHasFilterInfoCache.push(guidKey, childs, pointHasFilterInfoTimeout);
                    }
                } while (currPage < totlePage);
            } catch (Exception e) {
                LOGGER.warn("将分类 " + guidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
            }
        }
    }

    private static class FilteredValueConsumer implements Consumer<FilteredValue> {

        private boolean filtered = false;
        private FilteredValue filteredValue = null;

        public FilteredValueConsumer() {
        }

        @Override
        public void accept(FilteredValue filteredValue) {
            filtered = true;
            this.filteredValue = filteredValue;
        }

        public boolean isFiltered() {
            return filtered;
        }

        public FilteredValue getFilteredValue() {
            return filteredValue;
        }

        @Override
        public String toString() {
            return "FilteredValueConsumer{" +
                    "filtered=" + filtered +
                    ", filteredValue=" + filteredValue +
                    '}';
        }
    }

    private static class TriggeredValueConsumer implements Consumer<TriggeredValue> {

        private boolean triggered = false;
        private TriggeredValue triggeredValue = null;

        public TriggeredValueConsumer() {
        }

        @Override
        public void accept(TriggeredValue triggeredValue) {
            triggered = true;
            this.triggeredValue = triggeredValue;
        }

        public boolean isTriggered() {
            return triggered;
        }

        public TriggeredValue getTriggeredValue() {
            return triggeredValue;
        }

        @Override
        public String toString() {
            return "TriggeredValueConsumer{" +
                    "triggered=" + triggered +
                    ", triggeredValue=" + triggeredValue +
                    '}';
        }
    }
}
