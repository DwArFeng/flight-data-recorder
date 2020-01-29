package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TriggerInfoMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private TriggerInfoMaintainService triggerInfoMaintainService;

    private Point parentPoint;
    private List<TriggerInfo> triggerInfos;

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
        triggerInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TriggerInfo triggerInfo = new TriggerInfo(
                    null,
                    parentPoint.getKey(),
                    true,
                    "trigger-info-" + i,
                    "this is a test"
            );
            triggerInfos.add(triggerInfo);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        triggerInfos.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggerInfo.setKey(triggerInfoMaintainService.insert(triggerInfo));
                triggerInfo.setPointKey(parentPoint.getKey());
                triggerInfoMaintainService.update(triggerInfo);
            }
            assertEquals(5, triggerInfoMaintainService.lookup(TriggerInfoMaintainService.CHILD_FOR_POINT, new Object[]{parentPoint.getKey()}).getCount());
        } finally {
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggerInfoMaintainService.delete(triggerInfo.getKey());
            }
            pointMaintainService.delete(parentPoint.getKey());
        }
    }

}