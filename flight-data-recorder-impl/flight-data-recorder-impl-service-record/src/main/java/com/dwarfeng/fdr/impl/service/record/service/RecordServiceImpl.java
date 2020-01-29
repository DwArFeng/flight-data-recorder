package com.dwarfeng.fdr.impl.service.record.service;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.exception.EventException;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.fdr.stack.service.*;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
    private ServiceExceptionMapper sem;

    @Autowired
    private EnabledFilterInfoLookupService enabledFilterInfoLookupService;
    @Autowired
    private EnabledTriggerInfoLookupService enabledTriggerInfoLookupService;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager")
    public RecordResult record(@NotNull DataInfo dataInfo) throws ServiceException {
        //定义变量。
        LongIdKey pointKey;
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
        pointKey = new LongIdKey(dataInfo.getPointLongId());
        try {
            LOGGER.debug("1. 获得数据点...");
            point = pointMaintainService.get(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow(
                    "数据点实体 " + pointKey + " 获取失败，将抛出异常...",
                    LogLevel.WARN, sem, e);
        }
        //2. 获得数据点的所有过滤器信息。
        try {
            LOGGER.debug("2. 获得数据点的所有过滤器信息...");
            filterInfos = enabledFilterInfoLookupService.getEnabledFilterInfos(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow(
                    "数据点实体 " + pointKey + " 对应的过滤器列表获取失败，将抛出异常",
                    LogLevel.WARN, sem, e);
        }
        //3. 取出数据点的所有触发器信息。
        try {
            LOGGER.debug("3. 取出数据点的所有触发器信息...");
            triggerInfos = enabledTriggerInfoLookupService.getEnabledTriggerInfos(pointKey);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow(
                    "数据点实体 " + pointKey + " 对应的触发器列表获取失败，将抛出异常...",
                    LogLevel.WARN, sem, e);
        }

        //4. 过滤器信息构造过滤器。
        try {
            LOGGER.debug("4. 过滤器信息构造过滤器...");
            for (FilterInfo filterInfo : filterInfos) {
                if (filterInfo.isEnabled()) {
                    LOGGER.debug("过滤器信息 " + filterInfo.toString() + " 使能, 将构造过滤器...");
                    filters.add(filterHandler.make(
                            filterInfo.getPointKey().getLongId(),
                            filterInfo.getKey().getLongId(),
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
                            triggerInfo.getPointKey().getLongId(),
                            triggerInfo.getKey().getLongId(),
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
            RealtimeValue realtimeValue = null;
            if (realtimeValueMaintainService.exists(pointKey)) {
                realtimeValue = realtimeValueMaintainService.get(pointKey);
            }
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
            filteredValueMaintainService.insert(recordResult.getFilteredValue());
            // 如果点位不通过过滤，则后续方法根本无法进行，所以不用判断之后的信息，直接返回。
            return recordResult;
        }
        if (recordResult.isPersistenceRecorded()) {
            LOGGER.debug("检测到数据允许记录持久化信息, 记录之...");
            persistenceValueMaintainService.insert(recordResult.getPersistenceValue());
        }
        if (recordResult.isRealtimeRecorded()) {
            LOGGER.debug("检测到数据允许记录实时信息, 记录或更新之...");
            if (realtimeValueMaintainService.exists(recordResult.getRealtimeValue().getKey())) {
                realtimeValueMaintainService.update(recordResult.getRealtimeValue());
            } else {
                realtimeValueMaintainService.insert(recordResult.getRealtimeValue());
            }
        }
        if (recordResult.isTriggered()) {
            LOGGER.debug("检测到数据被触发, 记录所有触发信息...");
            for (TriggeredValue triggeredValue : recordResult.getTriggeredValues()) {
                LOGGER.debug("记录单条触发信息 " + triggeredValue + "...");
                triggeredValueMaintainService.insert(triggeredValue);
                LOGGER.debug("发送数据触发广播 " + triggeredValue + "...");
                eventHandler.fireDataTriggered(triggeredValue);
            }
        }
        return recordResult;
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
