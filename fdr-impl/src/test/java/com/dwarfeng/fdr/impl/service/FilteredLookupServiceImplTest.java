package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import com.dwarfeng.fdr.stack.service.FilteredLookupService;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import org.apache.commons.beanutils.BeanUtils;
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
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class FilteredLookupServiceImplTest {

    @Autowired
    private FilteredLookupService filteredLookupService;
    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private FilterInfoMaintainService filterInfoMaintainService;
    @Autowired
    private FilteredValueMaintainService filteredValueMaintainService;

    private Point parentPoint;
    private FilterInfo parentFilterInfo;
    private List<FilteredValue> filteredValues;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        parentFilterInfo = new FilterInfo(
                null,
                parentPoint.getKey(),
                true,
                "parent-filter-info",
                "this is a test",
                "test"
        );
        filteredValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilteredValue filteredValue = new FilteredValue(
                    null,
                    parentPoint.getKey(),
                    parentFilterInfo.getKey(),
                    new Date(),
                    "filtered-value-" + i,
                    "this is a test"
            );
            filteredValues.add(filteredValue);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        parentFilterInfo = null;
        filteredValues.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            parentFilterInfo.setKey(filterInfoMaintainService.insert(parentFilterInfo));
            parentFilterInfo.setPointKey(parentPoint.getKey());
            filterInfoMaintainService.update(parentFilterInfo);
            Date startDate = new Date();
            for (FilteredValue filteredValue : filteredValues) {
                filteredValue.setHappenedDate(new Date());
                filteredValue.setKey(filteredValueMaintainService.insert(filteredValue));
                filteredValue.setPointKey(parentPoint.getKey());
                filteredValue.setFilterKey(parentFilterInfo.getKey());
                filteredValueMaintainService.update(filteredValue);
            }
            Date endDate = new Date();

            PagedData<FilteredValue> lookup = filteredLookupService.lookupForPoint(parentPoint.getKey(), startDate, endDate);
            assertEquals(5, lookup.getCount());
            assertEquals(0, lookup.getCurrentPage());
            assertEquals(1, lookup.getTotalPages());
            for (int i = 0; i < lookup.getData().size(); i++) {
                Map<String, String> lookupEntity = BeanUtils.describe(lookup.getData().get(i));
                Map<String, String> originalEntity = BeanUtils.describe(filteredValues.get(i));
                assertEquals(lookupEntity, originalEntity);
            }

            lookup = filteredLookupService.lookupForPoint(parentPoint.getKey(), startDate, endDate, new PagingInfo(1, 2));
            assertEquals(5, lookup.getCount());
            assertEquals(1, lookup.getCurrentPage());
            assertEquals(3, lookup.getTotalPages());

            for (int i = 0; i < lookup.getData().size(); i++) {
                Map<String, String> lookupEntity = BeanUtils.describe(lookup.getData().get(i));
                Map<String, String> originalEntity = BeanUtils.describe(filteredValues.get(i + 2));
                assertEquals(lookupEntity, originalEntity);
            }


            lookup = filteredLookupService.lookupForFilter(parentFilterInfo.getKey(), startDate, endDate);
            assertEquals(5, lookup.getCount());
            assertEquals(0, lookup.getCurrentPage());
            assertEquals(1, lookup.getTotalPages());
            for (int i = 0; i < lookup.getData().size(); i++) {
                Map<String, String> lookupEntity = BeanUtils.describe(lookup.getData().get(i));
                Map<String, String> originalEntity = BeanUtils.describe(filteredValues.get(i));
                assertEquals(lookupEntity, originalEntity);
            }

            lookup = filteredLookupService.lookupForFilter(parentFilterInfo.getKey(), startDate, endDate, new PagingInfo(1, 2));
            assertEquals(5, lookup.getCount());
            assertEquals(1, lookup.getCurrentPage());
            assertEquals(3, lookup.getTotalPages());

            for (int i = 0; i < lookup.getData().size(); i++) {
                Map<String, String> lookupEntity = BeanUtils.describe(lookup.getData().get(i));
                Map<String, String> originalEntity = BeanUtils.describe(filteredValues.get(i + 2));
                assertEquals(lookupEntity, originalEntity);
            }
        } finally {
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainService.delete(filteredValue.getKey());
            }
            filterInfoMaintainService.delete(parentFilterInfo.getKey());
            pointMaintainService.delete(parentPoint.getKey());
        }
    }

}