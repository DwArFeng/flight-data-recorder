package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.PersistenceLookupService;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PersistenceLookupServiceImplTest {

    @Autowired
    private PersistenceLookupService persistenceLookupService;
    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private PersistenceValueMaintainService persistenceValueMaintainService;

    private Point parentPoint;
    private List<PersistenceValue> persistenceValues;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        persistenceValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PersistenceValue persistenceValue = new PersistenceValue(
                    null,
                    parentPoint.getKey(),
                    new Date(),
                    "persistemce-value-" + i
            );
            persistenceValues.add(persistenceValue);
        }
    }

    @Test
    public void test() throws Exception {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            Date startDate = new Date();
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValue.setHappenedDate(new Date());
                persistenceValue.setKey(persistenceValueMaintainService.insert(persistenceValue));
                persistenceValue.setPointKey(parentPoint.getKey());
                persistenceValueMaintainService.update(persistenceValue);
                Thread.sleep(10);
            }
            Date endDate = new Date();

            PagedData<PersistenceValue> lookup = persistenceLookupService.lookup(parentPoint.getKey(), startDate, endDate);
            assertEquals(5, lookup.getCount());
            assertEquals(0, lookup.getCurrentPage());
            assertEquals(1, lookup.getTotalPages());
            for (int i = 0; i < lookup.getData().size(); i++) {
                Map<String, String> lookupEntity = BeanUtils.describe(lookup.getData().get(i));
                Map<String, String> originalEntity = BeanUtils.describe(persistenceValues.get(i));
                assertEquals(lookupEntity, originalEntity);
            }

            lookup = persistenceLookupService.lookup(parentPoint.getKey(), startDate, endDate, new PagingInfo(1, 2));
            assertEquals(5, lookup.getCount());
            assertEquals(1, lookup.getCurrentPage());
            assertEquals(3, lookup.getTotalPages());

            for (int i = 0; i < lookup.getData().size(); i++) {
                Map<String, String> lookupEntity = BeanUtils.describe(lookup.getData().get(i));
                Map<String, String> originalEntity = BeanUtils.describe(persistenceValues.get(i + 2));
                assertEquals(lookupEntity, originalEntity);
            }
        } finally {
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValueMaintainService.delete(persistenceValue.getKey());
            }
            pointMaintainService.delete(parentPoint.getKey());
        }
    }
}