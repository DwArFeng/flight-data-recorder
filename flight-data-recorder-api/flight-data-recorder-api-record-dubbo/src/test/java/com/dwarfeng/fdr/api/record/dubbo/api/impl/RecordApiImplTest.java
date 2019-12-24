package com.dwarfeng.fdr.api.record.dubbo.api.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.impl.handler.event.kafka.bean.entity.KafkaTriggeredValue;
import com.dwarfeng.fdr.impl.handler.fnt.preset.IntegerFilter;
import com.dwarfeng.fdr.impl.handler.fnt.preset.IntegerRangeTrigger;
import com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredFilterInfo;
import com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredTriggerInfo;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class RecordApiImplTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("pointMaintainService")
    private PointMaintainService pointMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("filterInfoMaintainService")
    private FilterInfoMaintainService filterInfoMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("triggerInfoMaintainService")
    private TriggerInfoMaintainService triggerInfoMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("recordService")
    private RecordService recordService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("persistenceValueMaintainService")
    private PersistenceValueMaintainService persistenceValueMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("realtimeValueMaintainService")
    private RealtimeValueMaintainService realtimeValueMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("filteredValueMaintainService")
    private FilteredValueMaintainService filteredValueMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("triggeredValueMaintainService")
    private TriggeredValueMaintainService triggeredValueMaintainService;

    private Point point;
    private List<PersistenceValue> persistenceValues;
    private RealtimeValue realtimeValue;
    private List<FilteredValue> filteredValues;
    private List<TriggeredValue> triggeredValues;

    private List<FilterInfo> filterInfos = new ArrayList<>();
    private List<TriggerInfo> triggerInfos = new ArrayList<>();

    @Before
    public void setUp() {
        point = new Point(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent-point",
                "test-point",
                true,
                true
        );

        persistenceValues = new ArrayList<>();
        realtimeValue = null;
        filteredValues = new ArrayList<>();
        triggeredValues = new ArrayList<>();

        //构造FilterInfo。
        makeFilterInfo();
        //构造TriggerInfo。
        makeTriggerInfo();
    }

    private void makeFilterInfo() {
        IntegerFilter.Config config = new IntegerFilter.Config(
                Integer.MIN_VALUE,
                true,
                Integer.MAX_VALUE,
                true
        );
        StructuredFilterInfo structuredFilterInfo = new StructuredFilterInfo(
                "integerFilter",
                config
        );
        FilterInfo filterInfo = new FilterInfo(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                point.getKey(),
                true,
                "This is a test",
                JSONObject.toJSONString(structuredFilterInfo, SerializerFeature.WriteClassName)
        );
        filterInfos.add(filterInfo);

        config = new IntegerFilter.Config(
                Integer.MIN_VALUE,
                true,
                1000,
                true
        );
        structuredFilterInfo = new StructuredFilterInfo(
                "integerFilter",
                config
        );
        filterInfo = new FilterInfo(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                point.getKey(),
                true,
                "This is a test",
                JSONObject.toJSONString(structuredFilterInfo, SerializerFeature.WriteClassName)
        );
        filterInfos.add(filterInfo);
    }

    private void makeTriggerInfo() {
        IntegerRangeTrigger.Config config = new IntegerRangeTrigger.Config(
                Integer.MIN_VALUE,
                true,
                1000,
                false
        );
        StructuredTriggerInfo structuredTriggerInfo = new StructuredTriggerInfo(
                "integerRangeTrigger",
                config
        );
        TriggerInfo triggerInfo = new TriggerInfo(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                point.getKey(),
                true,
                "This is a test",
                JSONObject.toJSONString(structuredTriggerInfo, SerializerFeature.WriteClassName)
        );
        triggerInfos.add(triggerInfo);
    }

    @After
    public void tearDown() {
        point = null;
        persistenceValues.clear();
        realtimeValue = null;
        filteredValues.clear();
        triggeredValues.clear();
        filterInfos.clear();
        triggerInfos.clear();
    }

    @Test
    public void record() throws ServiceException {
        pointMaintainService.insert(point);
        for (FilterInfo filterInfo : filterInfos) {
            filterInfoMaintainService.insert(filterInfo);
        }
        for (TriggerInfo triggerInfo : triggerInfos) {
            triggerInfoMaintainService.insert(triggerInfo);
        }
        try {
            record(new DataInfo(point.getKey().getUuid(), "aaa", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "bbb", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "ccc", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "112", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "113", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "114", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "115", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "116", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "1000", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "1001", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "1002", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "1003", new Date()));
            record(new DataInfo(point.getKey().getUuid(), "1004", new Date()));
        } finally {
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainService.delete(filteredValue.getKey());
            }
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValueMaintainService.delete(triggeredValue.getKey());
            }
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValueMaintainService.delete(persistenceValue.getKey());
            }
            if (Objects.nonNull(realtimeValue)) {
                realtimeValueMaintainService.delete(realtimeValue.getKey());
            }
            for (FilterInfo filterInfo : filterInfos) {
                filterInfoMaintainService.delete(filterInfo.getKey());
            }
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggerInfoMaintainService.delete(triggerInfo.getKey());
            }
            pointMaintainService.delete(point.getKey());
        }
    }

    private void record(DataInfo dataInfo) throws ServiceException {
        RecordResult recordResult = recordService.record(dataInfo);
        if (recordResult.isFiltered()) {
            filteredValues.add(recordResult.getFilteredValue());
        }
        if (recordResult.isPersistenceRecorded()) {
            persistenceValues.add(recordResult.getPersistenceValue());
        }
        if (recordResult.isRealtimeRecorded()) {
            realtimeValue = recordResult.getRealtimeValue();
        }
        if (recordResult.isTriggered()) {
            triggeredValues.addAll(recordResult.getTriggeredValues());
        }
    }

    @Component
    public static class KafkaConsumer {

        @KafkaListener(topics = "fdr.data_triggered")
        public void fireDataTriggered(KafkaTriggeredValue triggeredValue) {
            CT.trace(triggeredValue);
        }
    }
}