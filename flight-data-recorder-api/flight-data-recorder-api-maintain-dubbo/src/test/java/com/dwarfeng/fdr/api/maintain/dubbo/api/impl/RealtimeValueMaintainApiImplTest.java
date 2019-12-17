package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.RealtimeValueMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
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
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class RealtimeValueMaintainApiImplTest {

    @Autowired
    private PointMaintainApi pointMaintainApi;
    @Autowired
    private RealtimeValueMaintainApi realtimeValueMaintainApi;

    private Point parentPoint;
    private List<RealtimeValue> realtimeValues;

    @Before
    public void setUp() throws Exception {
        parentPoint = new Point(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        realtimeValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RealtimeValue realtimeValue = new RealtimeValue(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                    parentPoint.getKey(),
                    new Date(),
                    "realtime-value-" + i
            );
            realtimeValues.add(realtimeValue);
        }
    }

    @After
    public void tearDown() throws Exception {
        parentPoint = null;
        realtimeValues.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            pointMaintainApi.insert(parentPoint);
            for (RealtimeValue realtimeValue : realtimeValues) {
                realtimeValueMaintainApi.insert(realtimeValue);
            }
        } finally {
            pointMaintainApi.delete(parentPoint.getKey());
            for (RealtimeValue realtimeValue : realtimeValues) {
                realtimeValueMaintainApi.delete(realtimeValue.getKey());
            }
        }
    }

}