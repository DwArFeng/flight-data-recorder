package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
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
public class TriggeredValueMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private TriggerInfoMaintainService triggerInfoMaintainService;
    @Autowired
    private TriggeredValueMaintainService triggeredValueMaintainService;

    private Point parentPoint;
    private TriggerInfo parentTriggerInfo;
    private List<TriggeredValue> triggeredValues;

    @Before
    public void setUp() {
        parentPoint = new Point(
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
                "this is a test",
                "test"
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
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            parentTriggerInfo.setKey(triggerInfoMaintainService.insert(parentTriggerInfo));
            parentTriggerInfo.setPointKey(parentPoint.getKey());
            triggerInfoMaintainService.update(parentTriggerInfo);
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValue.setKey(triggeredValueMaintainService.insert(triggeredValue));
                triggeredValue.setPointKey(parentPoint.getKey());
                triggeredValue.setTriggerKey(parentTriggerInfo.getKey());
                triggeredValueMaintainService.update(triggeredValue);
            }
        } finally {
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValueMaintainService.delete(triggeredValue.getKey());
            }
            triggerInfoMaintainService.delete(parentTriggerInfo.getKey());
            pointMaintainService.delete(parentPoint.getKey());
        }
    }
}