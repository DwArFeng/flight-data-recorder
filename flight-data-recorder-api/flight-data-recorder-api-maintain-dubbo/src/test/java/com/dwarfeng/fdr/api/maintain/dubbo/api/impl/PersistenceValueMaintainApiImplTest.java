package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PersistenceValueMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
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
public class PersistenceValueMaintainApiImplTest {

    @Autowired
    private PointMaintainApi pointMaintainApi;
    @Autowired
    private PersistenceValueMaintainApi persistenceValueMaintainApi;

    private Point parentPoint;
    private List<PersistenceValue> persistenceValues;

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
        persistenceValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PersistenceValue persistenceValue = new PersistenceValue(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                    parentPoint.getKey(),
                    new Date(),
                    "persistemce-value-" + i
            );
            persistenceValues.add(persistenceValue);
        }
    }

    @After
    public void tearDown() throws Exception {
        parentPoint = null;
        persistenceValues.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            pointMaintainApi.insert(parentPoint);
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValueMaintainApi.insert(persistenceValue);
            }
        } finally {
            pointMaintainApi.delete(parentPoint.getKey());
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValueMaintainApi.delete(persistenceValue.getKey());
            }
        }
    }

}