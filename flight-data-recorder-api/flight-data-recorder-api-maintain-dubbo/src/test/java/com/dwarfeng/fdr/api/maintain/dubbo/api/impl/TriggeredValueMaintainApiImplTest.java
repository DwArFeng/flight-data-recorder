package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.TriggerInfoMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.TriggeredValueMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TriggeredValueMaintainApiImplTest {

    @Autowired
    private PointMaintainApi pointMaintainApi;
    @Autowired
    private TriggerInfoMaintainApi triggerInfoMaintainApi;
    @Autowired
    private TriggeredValueMaintainApi triggeredValueMaintainApi;

    private Point parentPoint;
    private TriggerInfo parentTriggerInfo;
    private List<TriggeredValue> triggeredValues;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null,
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        parentTriggerInfo = new TriggerInfo(
                null,
                parentPoint.getKey(),
                true,
                "parent-trigger-info",
                "this is a test"
        );
        triggeredValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TriggeredValue triggeredValue = new TriggeredValue(
                    null,
                    parentPoint.getKey(),
                    parentTriggerInfo.getKey(),
                    new Date(),
                    "triggered-value-" + i,
                    "this is a test"
            );
            triggeredValues.add(triggeredValue);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        parentTriggerInfo = null;
        triggeredValues.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainApi.insert(parentPoint));
            parentTriggerInfo.setKey(triggerInfoMaintainApi.insert(parentTriggerInfo));
            parentTriggerInfo.setPointKey(parentPoint.getKey());
            triggerInfoMaintainApi.update(parentTriggerInfo);
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValue.setKey(triggeredValueMaintainApi.insert(triggeredValue));
                triggeredValue.setPointKey(parentPoint.getKey());
                triggeredValue.setTriggerKey(parentTriggerInfo.getKey());
                triggeredValueMaintainApi.update(triggeredValue);
            }
        } finally {
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValueMaintainApi.delete(triggeredValue.getKey());
            }
            triggerInfoMaintainApi.delete(parentTriggerInfo.getKey());
            pointMaintainApi.delete(parentPoint.getKey());
        }
    }

}