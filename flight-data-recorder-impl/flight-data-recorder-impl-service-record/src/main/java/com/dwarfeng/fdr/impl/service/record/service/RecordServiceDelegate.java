package com.dwarfeng.fdr.impl.service.record.service;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.*;
import com.dwarfeng.fdr.stack.dao.*;
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
import java.util.UUID;
import java.util.function.Consumer;

@Component
@Validated
public class RecordServiceDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceDelegate.class);

    @Autowired
    private ValidationHandler validationHandler;
    @Autowired
    private PointDao pointDao;
    @Autowired
    private PointCache pointCache;
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
    private FilteredValueDao filteredValueDao;
    @Autowired
    private FilteredValueCache filteredValueCache;
    @Autowired
    private PersistenceValueDao persistenceValueDao;
    @Autowired
    private PersistenceValueCache persistenceValueCache;
    @Autowired
    private RealtimeValueDao realtimeValueDao;
    @Autowired
    private RealtimeValueCache realtimeValueCache;
    @Autowired
    private TriggeredValueDao triggeredValueDao;
    @Autowired
    private TriggeredValueCache triggeredValueCache;
    @Autowired
    private FilterHandler filterHandler;
    @Autowired
    private TriggerHandler triggerHandler;
    @Autowired
    private EventHandler eventHandler;

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;
    @Value("${cache.timeout.entity.filter_info}")
    private long filterInfoTimeout;
    @Value("${cache.timeout.one_to_many.point_has_filter_info}")
    private long pointHasFilterInfoTimeout;
    @Value("${cache.batch_fetch_size.filter_info}")
    private int filterInfoFetchSize;
    @Value("${cache.timeout.entity.trigger_info}")
    private long triggerInfoTimeout;
    @Value("${cache.timeout.one_to_many.point_has_trigger_info}")
    private long pointHasTriggerInfoTimeout;
    @Value("${cache.batch_fetch_size.trigger_info}")
    private int triggerInfoFetchSize;
    @Value("${cache.timeout.entity.filtered_value}")
    private long filteredValueTimeout;
    @Value("${cache.timeout.entity.persistence_value}")
    private long persistenceValueTimeout;
    @Value("${cache.timeout.entity.realtime_value}")
    private long realtimeValueTimeout;
    @Value("${cache.timeout.entity.triggered_value}")
    private long triggeredValueTimeout;

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public RecordResult record(@NotNull DataInfo dataInfo) throws ServiceException {
        //定义变量。
        UuidKey pointKey;
        Point point;
        List<FilterInfo> filterInfos;
        List<TriggerInfo> triggerInfos;
        List<Filter> filters = new ArrayList<>();
        List<Trigger> triggers = new ArrayList<>();
        //待返回结果。
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
         *   1. 判断数据是否合法。
         *   2. 获得数据点。
         *   3. 获得数据点的所有过滤器信息。
         *   4. 取出数据点的所有触发器信息。
         *   5. 过滤器信息构造过滤器。
         *   6. 触发器信息构造触发器。
         *   7. 过滤器进行过滤，判断不通过的情况。
         *   8. 如果通过过滤且数据点允许持久化，生成持久化数据值。
         *   9. 如果通过过滤且数据点允许实时记录，生成实时数据。
         *   10. 如果通过过滤，处理所有触发器，获取所有触发器信息。
         *   11. 处理记录结果并返回。
         */

        //1. 判断数据是否合法。
        try {
            LOGGER.debug("1. 判断数据是否合法...");
            validationHandler.dataInfoValidation(dataInfo);
        } catch (ValidationException e) {
            LOGGER.warn("传入数据 " + dataInfo + " 验证失败，将抛出异常...");
            throw new ServiceException(e);
        }

        pointKey = new UuidKey(dataInfo.getPointUuid());

        //2. 获得数据点。
        try {
            LOGGER.debug("2. 获得数据点...");
            point = getPoint(pointKey);
        } catch (CacheException | DaoException e) {
            LOGGER.warn("数据点实体 " + pointKey + " 获取失败，将抛出异常...");
            throw new ServiceException(e);
        }
        //3. 获得数据点的所有过滤器信息。
        try {
            LOGGER.debug("3. 获得数据点的所有过滤器信息...");
            filterInfos = getFilterInfos(pointKey);
        } catch (CacheException | DaoException e) {
            LOGGER.warn("数据点实体 " + pointKey + " 对应的过滤器列表获取失败，将抛出异常...");
            throw new ServiceException(e);
        }
        //4. 取出数据点的所有触发器信息。
        try {
            LOGGER.debug("4. 取出数据点的所有触发器信息...");
            triggerInfos = getTriggerInfos(pointKey);
        } catch (CacheException | DaoException e) {
            LOGGER.warn("数据点实体 " + pointKey + " 对应的触发器列表获取失败，将抛出异常...");
            throw new ServiceException(e);
        }

        //5. 过滤器信息构造过滤器。
        try {
            LOGGER.debug("5. 过滤器信息构造过滤器...");
            for (FilterInfo filterInfo : filterInfos) {
                if (filterInfo.isEnabled()) {
                    LOGGER.debug("过滤器信息 " + filterInfo.toString() + " 使能, 将构造过滤器...");
                    filters.add(filterHandler.make(
                            filterInfo.getPointKey().getUuid(),
                            filterInfo.getKey().getUuid(),
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
        //6. 触发器信息构造触发器。
        try {
            LOGGER.debug("6. 触发器信息构造触发器...");
            for (TriggerInfo triggerInfo : triggerInfos) {
                if (triggerInfo.isEnabled()) {
                    LOGGER.debug("触发器信息 " + triggerInfo.toString() + " 使能, 将构造触发器...");
                    triggers.add(triggerHandler.make(
                            triggerInfo.getPointKey().getUuid(),
                            triggerInfo.getKey().getUuid(),
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

        //7. 过滤器进行过滤，判断不通过的情况。
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

        //8. 如果通过过滤且数据点允许持久化，生成持久化数据值。
        if (!recordResult.isFiltered() && point.isPersistenceEnabled()) {
            LOGGER.debug("8. 如果通过过滤且数据点允许持久化，生成持久化数据值... 满足");
            recordResult.setPersistenceRecorded(true);
            recordResult.setPersistenceValue(new PersistenceValue(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                    pointKey,
                    dataInfo.getHappenedDate(),
                    dataInfo.getValue()
            ));
        } else {
            LOGGER.debug("8. 如果通过过滤且数据点允许持久化，生成持久化数据值... 不满足");
        }

        //9. 如果通过过滤且数据点允许实时记录，生成实时数据。
        if (!recordResult.isFiltered() && point.isRealtimeEnabled()) {
            LOGGER.debug("9. 如果通过过滤且数据点允许实时记录，生成实时数据... 满足");
            recordResult.setRealtimeRecorded(true);
            recordResult.setRealtimeValue(new RealtimeValue(
                    pointKey,
                    dataInfo.getHappenedDate(),
                    dataInfo.getValue()
            ));
        } else {
            LOGGER.debug("9. 如果通过过滤且数据点允许实时记录，生成实时数据... 不满足");
        }

        //10. 如果通过过滤，处理所有触发器，获取所有触发器信息。

        if (!recordResult.isFiltered()) {
            try {
                LOGGER.debug("10. 如果通过过滤，处理所有触发器，获取所有触发器信息... 满足");
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
            LOGGER.debug("11. 如果通过过滤，处理所有触发器，获取所有触发器信息... 不满足");
        }

        //11. 处理记录结果并返回。
        LOGGER.debug("11. 处理记录结果并返回...");
        try {
            return processRecordResult(recordResult);
        } catch (Exception e) {
            LOGGER.warn("记录处理失败, 将抛出异常...");
            throw new ServiceException(ServiceExceptionCodes.UNDEFINE, e);
        }
    }

    public RecordResult processRecordResult(RecordResult recordResult) throws DaoException, CacheException, ValidationException, EventException {
        if (recordResult.isFiltered()) {
            LOGGER.debug("检测到数据点被过滤, 记录被过滤信息...");
            insertFilteredValue(recordResult.getFilteredValue());
            // 如果点位不通过过滤，则后续方法根本无法进行，所以不用判断之后的信息，直接返回。
            return recordResult;
        }
        if (recordResult.isPersistenceRecorded()) {
            LOGGER.debug("检测到数据允许记录持久化信息, 记录之...");
            insertPersistenceValue(recordResult.getPersistenceValue());
        }
        if (recordResult.isRealtimeRecorded()) {
            LOGGER.debug("检测到数据允许记录实时信息, 记录或更新之...");
            insertOrUpdateRealtimeValue(recordResult.getRealtimeValue());
        }
        if (recordResult.isTriggered()) {
            LOGGER.debug("检测到数据被触发, 记录所有触发信息...");
            for (TriggeredValue triggeredValue : recordResult.getTriggeredValues()) {
                LOGGER.debug("记录单条触发信息 " + triggeredValue + "...");
                insertTriggeredValue(triggeredValue);
                LOGGER.debug("发送数据触发广播 " + triggeredValue + "...");
                eventHandler.fireDataTriggered(triggeredValue);
            }
        }
        return recordResult;
    }

    @TimeAnalyse
    public void insertFilteredValue(FilteredValue filteredValue) throws ValidationException, CacheException, DaoException {
        validationHandler.filteredValueValidation(filteredValue);

        if (filteredValueCache.exists(filteredValue.getKey()) || filteredValueDao.exists(filteredValue.getKey())) {
            LOGGER.debug("指定的实体 " + filteredValue.toString() + " 已经存在，无法插入...");
            throw new IllegalStateException("指定的实体 " + filteredValue.toString() + " 已经存在，无法插入...");
        } else {
            LOGGER.debug("将指定的实体 " + filteredValue.toString() + " 插入数据访问层中...");
            filteredValueDao.insert(filteredValue);
            LOGGER.debug("将指定的实体 " + filteredValue.toString() + " 插入缓存中...");
            filteredValueCache.push(filteredValue.getKey(), filteredValue, filteredValueTimeout);
        }
    }

    @TimeAnalyse
    public void insertPersistenceValue(PersistenceValue persistenceValue) throws ValidationException, CacheException, DaoException {
        validationHandler.persistenceValueValidation(persistenceValue);

        if (persistenceValueCache.exists(persistenceValue.getKey()) || persistenceValueDao.exists(persistenceValue.getKey())) {
            LOGGER.debug("指定的实体 " + persistenceValue.toString() + " 已经存在，无法插入...");
            throw new IllegalStateException("指定的实体 " + persistenceValue.toString() + " 已经存在，无法插入...");
        } else {
            LOGGER.debug("将指定的实体 " + persistenceValue.toString() + " 插入数据访问层中...");
            persistenceValueDao.insert(persistenceValue);
            LOGGER.debug("将指定的实体 " + persistenceValue.toString() + " 插入缓存中...");
            persistenceValueCache.push(persistenceValue.getKey(), persistenceValue, persistenceValueTimeout);
        }
    }

    @TimeAnalyse
    public void insertOrUpdateRealtimeValue(RealtimeValue realtimeValue) throws ValidationException, CacheException, DaoException {
        validationHandler.realtimeValueValidation(realtimeValue);

        if (realtimeValueCache.exists(realtimeValue.getKey()) || realtimeValueDao.exists(realtimeValue.getKey())) {
            LOGGER.debug("指定的实体 " + realtimeValue.toString() + " 已经存在，执行更新操作...");
            realtimeValueDao.update(realtimeValue);
        } else {
            LOGGER.debug("指定的实体 " + realtimeValue.toString() + " 不存在，执行插入操作...");
            LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入数据访问层中...");
            realtimeValueDao.insert(realtimeValue);
        }
        LOGGER.debug("将指定的实体 " + realtimeValue.toString() + " 插入缓存中...");
        realtimeValueCache.push(realtimeValue.getKey(), realtimeValue, realtimeValueTimeout);
    }

    @TimeAnalyse
    public void insertTriggeredValue(TriggeredValue triggeredValue) throws ValidationException, CacheException, DaoException {
        validationHandler.triggeredValueValidation(triggeredValue);

        if (triggeredValueCache.exists(triggeredValue.getKey()) || triggeredValueDao.exists(triggeredValue.getKey())) {
            LOGGER.debug("指定的实体 " + triggeredValue.toString() + " 已经存在，无法插入...");
            throw new IllegalStateException("指定的实体 " + triggeredValue.toString() + " 已经存在，无法插入...");
        } else {
            LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入数据访问层中...");
            triggeredValueDao.insert(triggeredValue);
            LOGGER.debug("将指定的实体 " + triggeredValue.toString() + " 插入缓存中...");
            triggeredValueCache.push(triggeredValue.getKey(), triggeredValue, triggeredValueTimeout);
        }
    }

    @TimeAnalyse
    public List<TriggerInfo> getTriggerInfos(UuidKey pointKey) throws CacheException, DaoException {
        List<TriggerInfo> triggerInfos;

        if (pointHasTriggerInfoCache.exists(pointKey)) {
            LOGGER.debug("在缓存中发现了 " + pointKey.toString() + " 对应的子项列表，直接返回缓存中的值...");
            long count = pointHasTriggerInfoCache.size(pointKey);
            triggerInfos = pointHasTriggerInfoCache.get(pointKey, 0, (int) count);
        } else {
            LOGGER.debug("查询指定的TriggerInfo对应的子项...");
            triggerInfos = triggerInfoDao.getTriggerInfos(pointKey, LookupPagingInfo.LOOKUP_ALL);
            if (triggerInfos.size() > 0) {
                for (TriggerInfo triggerInfo : triggerInfos) {
                    LOGGER.debug("将查询到的的实体 " + triggerInfo.toString() + " 插入缓存中...");
                    triggerInfoCache.push(triggerInfo.getKey(), triggerInfo, triggerInfoTimeout);
                }
                LOGGER.debug("抓取实体 " + pointKey.toString() + " 对应的子项并插入缓存...");
                fetchTriggerInfo2Cache(pointKey);
            }
        }

        return triggerInfos;
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    @Async
    public void fetchTriggerInfo2Cache(UuidKey uuidKey) {
        try {
            int totlePage;
            int currPage;
            long count = triggerInfoDao.getTriggerInfoCount(uuidKey);
            totlePage = Math.max((int) Math.ceil((double) count / triggerInfoFetchSize), 1);
            currPage = 0;
            pointHasTriggerInfoCache.delete(uuidKey);
            do {
                LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, triggerInfoFetchSize);
                List<TriggerInfo> childs = triggerInfoDao.getTriggerInfos(uuidKey, lookupPagingInfo);
                if (childs.size() > 0) {
                    pointHasTriggerInfoCache.push(uuidKey, childs, pointHasTriggerInfoTimeout);
                }
            } while (currPage < totlePage);
        } catch (Exception e) {
            LOGGER.warn("将分类 " + uuidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
        }
    }

    @TimeAnalyse
    public List<FilterInfo> getFilterInfos(UuidKey pointKey) throws CacheException, DaoException {
        List<FilterInfo> filterInfos;

        if (pointHasFilterInfoCache.exists(pointKey)) {
            LOGGER.debug("在缓存中发现了 " + pointKey.toString() + " 对应的子项列表，直接返回缓存中的值...");
            long count = pointHasFilterInfoCache.size(pointKey);
            filterInfos = pointHasFilterInfoCache.get(pointKey, 0, (int) count);
        } else {
            LOGGER.debug("查询指定的FilterInfo对应的子项...");
            filterInfos = filterInfoDao.getFilterInfos(pointKey, LookupPagingInfo.LOOKUP_ALL);
            if (filterInfos.size() > 0) {
                for (FilterInfo filterInfo : filterInfos) {
                    LOGGER.debug("将查询到的的实体 " + filterInfo.toString() + " 插入缓存中...");
                    filterInfoCache.push(filterInfo.getKey(), filterInfo, filterInfoTimeout);
                }
                LOGGER.debug("抓取实体 " + pointKey.toString() + " 对应的子项并插入缓存...");
                fetchFilterInfo2Cache(pointKey);
            }
        }

        return filterInfos;
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    @Async
    public void fetchFilterInfo2Cache(UuidKey uuidKey) {
        try {
            int totlePage;
            int currPage;
            long count = filterInfoDao.getFilterInfoCount(uuidKey);
            totlePage = Math.max((int) Math.ceil((double) count / filterInfoFetchSize), 1);
            currPage = 0;
            pointHasFilterInfoCache.delete(uuidKey);
            do {
                LookupPagingInfo lookupPagingInfo = new LookupPagingInfo(true, currPage++, filterInfoFetchSize);
                List<FilterInfo> childs = filterInfoDao.getFilterInfos(uuidKey, lookupPagingInfo);
                if (childs.size() > 0) {
                    pointHasFilterInfoCache.push(uuidKey, childs, pointHasFilterInfoTimeout);
                }
            } while (currPage < totlePage);
        } catch (Exception e) {
            LOGGER.warn("将分类 " + uuidKey.toString() + " 的子项添加进入缓存时发生异常，异常信息如下", e);
        }
    }

    @TimeAnalyse
    public Point getPoint(UuidKey key) throws CacheException, DaoException {
        if (pointCache.exists(key)) {
            LOGGER.debug("在缓存中发现了 " + key.toString() + " 对应的值，直接返回该值...");
            return pointCache.get(key);
        } else {
            LOGGER.debug("未能在缓存中发现 " + key.toString() + " 对应的值，读取数据访问层...");
            Point point = pointDao.get(key);
            LOGGER.debug("将读取到的值 " + point.toString() + " 回写到缓存中...");
            pointCache.push(key, point, pointTimeout);
            return point;
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
