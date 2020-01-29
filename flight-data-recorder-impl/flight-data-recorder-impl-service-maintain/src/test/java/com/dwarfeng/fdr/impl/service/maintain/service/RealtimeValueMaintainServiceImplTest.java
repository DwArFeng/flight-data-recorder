package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.RealtimeValueMaintainService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
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
public class RealtimeValueMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private RealtimeValueMaintainService realtimeValueMaintainService;

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
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            realtimeValue.setKey(parentPoint.getKey());
            realtimeValueMaintainService.insert(realtimeValue);
        } finally {
            realtimeValueMaintainService.delete(realtimeValue.getKey());
            pointMaintainService.delete(parentPoint.getKey());
        }
    }

}