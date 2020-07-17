package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.impl.handler.mapper.CountMapperRegistry;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.MappingLookupService;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class MappingLookupServiceImplTest {

    @Autowired
    private MappingLookupService mappingLookupService;
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

            List<TimedValue> lookup = mappingLookupService.lookupPersistence(parentPoint.getKey(), startDate, endDate);
            assertEquals(5, lookup.size());
            for (int i = 0; i < lookup.size(); i++) {
                TimedValue lookupEntity = lookup.get(i);
                PersistenceValue originalEntity = persistenceValues.get(i);
                assertEquals(lookupEntity.getHappenedDate(), originalEntity.getHappenedDate());
                assertEquals(lookupEntity.getValue(), originalEntity.getValue());
            }

            lookup = mappingLookupService.mappingPersistence(parentPoint.getKey(), startDate, endDate,
                    CountMapperRegistry.MAPPER_TYPE, new Object[]{});
            assertEquals(1, lookup.size());
            assertEquals("5", lookup.get(0).getValue());
        } finally {
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValueMaintainService.delete(persistenceValue.getKey());
            }
            pointMaintainService.delete(parentPoint.getKey());
        }
    }
}
