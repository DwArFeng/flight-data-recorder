package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.FilterInfoMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.FilteredValueMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class FilteredValueMaintainApiImplTest {

    @Autowired
    private PointMaintainApi pointMaintainApi;
    @Autowired
    private FilterInfoMaintainApi filterInfoMaintainApi;
    @Autowired
    private FilteredValueMaintainApi filteredValueMaintainApi;

    private Point parentPoint;
    private FilterInfo parentFilterInfo;
    private List<FilteredValue> filteredValues;

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
        parentFilterInfo = new FilterInfo(
                null,
                parentPoint.getKey(),
                true,
                "parent-filter-info",
                "this is a test"
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
    public void test() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainApi.insert(parentPoint));
            parentFilterInfo.setKey(filterInfoMaintainApi.insert(parentFilterInfo));
            parentFilterInfo.setPointKey(parentPoint.getKey());
            filterInfoMaintainApi.update(parentFilterInfo);
            for (FilteredValue filteredValue : filteredValues) {
                filteredValue.setKey(filteredValueMaintainApi.insert(filteredValue));
                filteredValue.setPointKey(parentPoint.getKey());
                filteredValue.setFilterKey(parentFilterInfo.getKey());
                filteredValueMaintainApi.update(filteredValue);
            }
        } finally {
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainApi.delete(filteredValue.getKey());
            }
            filterInfoMaintainApi.delete(parentFilterInfo.getKey());
            pointMaintainApi.delete(parentPoint.getKey());
        }
    }

}