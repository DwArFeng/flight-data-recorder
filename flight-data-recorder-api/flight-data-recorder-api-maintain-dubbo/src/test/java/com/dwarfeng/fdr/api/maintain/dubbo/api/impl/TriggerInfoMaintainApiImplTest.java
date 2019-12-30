package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.TriggerInfoMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.exception.ServiceException;
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
public class TriggerInfoMaintainApiImplTest {

    @Autowired
    private PointMaintainApi pointMaintainApi;
    @Autowired
    private TriggerInfoMaintainApi triggerInfoMaintainApi;

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
            parentPoint.setKey(pointMaintainApi.insert(parentPoint));
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggerInfo.setKey(triggerInfoMaintainApi.insert(triggerInfo));
                triggerInfo.setPointKey(parentPoint.getKey());
                triggerInfoMaintainApi.update(triggerInfo);
            }
            assertEquals(5, triggerInfoMaintainApi.getTriggerInfos(parentPoint.getKey(), LookupPagingInfo.LOOKUP_ALL).getCount());
        } finally {
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggerInfoMaintainApi.delete(triggerInfo.getKey());
            }
            pointMaintainApi.delete(parentPoint.getKey());
        }
    }

}