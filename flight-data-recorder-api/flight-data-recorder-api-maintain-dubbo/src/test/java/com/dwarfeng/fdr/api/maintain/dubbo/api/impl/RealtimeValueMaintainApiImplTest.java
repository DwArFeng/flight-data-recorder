package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.RealtimeValueMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class RealtimeValueMaintainApiImplTest {

    @Autowired
    private PointMaintainApi pointMaintainApi;
    @Autowired
    private RealtimeValueMaintainApi realtimeValueMaintainApi;

    private Point parentPoint;
    private RealtimeValue realtimeValue;

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
        realtimeValue = new RealtimeValue(
                parentPoint.getKey(),
                new Date(),
                "realtime-value"
        );
    }

    @After
    public void tearDown() {
        parentPoint = null;
        realtimeValue = null;
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainApi.insert(parentPoint));
            realtimeValue.setKey(parentPoint.getKey());
            realtimeValueMaintainApi.insert(realtimeValue);
        } finally {
            realtimeValueMaintainApi.delete(realtimeValue.getKey());
            pointMaintainApi.delete(parentPoint.getKey());
        }
    }

}