package com.dwarfeng.fdr.impl.service;

import com.alibaba.fastjson.JSONObject;
import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.fdr.impl.handler.preset.RangedIntegerFilterMaker;
import com.dwarfeng.fdr.impl.handler.preset.RangedIntegerTriggerMaker;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonTriggeredValue;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.service.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class RecordServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private FilterInfoMaintainService filterInfoMaintainService;
    @Autowired
    private TriggerInfoMaintainService triggerInfoMaintainService;
    @Autowired
    private PersistenceValueMaintainService persistenceValueMaintainService;
    @Autowired
    private RealtimeValueMaintainService realtimeValueMaintainService;
    @Autowired
    private FilteredValueMaintainService filteredValueMaintainService;
    @Autowired
    private TriggeredValueMaintainService triggeredValueMaintainService;

    @Autowired
    private RecordService recordService;

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
        RangedIntegerFilterMaker.Config config = new RangedIntegerFilterMaker.Config(
                Integer.MIN_VALUE,
                true,
                Integer.MAX_VALUE,
                true
        );
        FilterInfo filterInfo = new FilterInfo(
                null,
                point.getKey(),
                true,
                "This is a test",
                JSONObject.toJSONString(config),
                RangedIntegerFilterMaker.SUPPORT_TYPE
        );
        filterInfos.add(filterInfo);

        config = new RangedIntegerFilterMaker.Config(
                Integer.MIN_VALUE,
                true,
                1000,
                true
        );
        filterInfo = new FilterInfo(
                null,
                point.getKey(),
                true,
                "This is a test",
                JSONObject.toJSONString(config),
                RangedIntegerFilterMaker.SUPPORT_TYPE
        );
        filterInfos.add(filterInfo);
    }

    private void makeTriggerInfo() {
        RangedIntegerTriggerMaker.Config config = new RangedIntegerTriggerMaker.Config(
                Integer.MIN_VALUE,
                true,
                1000,
                false
        );
        TriggerInfo triggerInfo = new TriggerInfo(
                null,
                point.getKey(),
                true,
                "This is a test",
                JSONObject.toJSONString(config),
                RangedIntegerTriggerMaker.SUPPORT_TYPE
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
    public void record() throws Exception {
        point.setKey(pointMaintainService.insert(point));
        for (FilterInfo filterInfo : filterInfos) {
            filterInfo.setKey(filterInfoMaintainService.insert(filterInfo));
            filterInfo.setPointKey(point.getKey());
            filterInfoMaintainService.update(filterInfo);
        }
        for (TriggerInfo triggerInfo : triggerInfos) {
            triggerInfo.setKey(triggerInfoMaintainService.insert(triggerInfo));
            triggerInfo.setPointKey(point.getKey());
            triggerInfoMaintainService.update(triggerInfo);
        }
        try {
            record(new DataInfo(point.getKey().getLongId(), "aaa", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "bbb", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "ccc", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "112", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "113", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "114", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "115", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "116", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "1000", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "1001", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "1002", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "1003", new Date()));
            record(new DataInfo(point.getKey().getLongId(), "1004", new Date()));
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
        Thread.sleep(2000L);
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
        public void fireDataTriggered(FastJsonTriggeredValue triggeredValue) {
            CT.trace(triggeredValue);
        }
    }
}