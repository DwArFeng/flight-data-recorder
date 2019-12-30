package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.FilterInfoMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class FilterInfoMaintainApiImplTest {

    @Autowired
    private PointMaintainApi pointMaintainApi;
    @Autowired
    private FilterInfoMaintainApi filterInfoMaintainApi;

    private Point parentPoint;
    private List<FilterInfo> filterInfos;

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
        filterInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilterInfo filterInfo = new FilterInfo(
                    null,
                    parentPoint.getKey(),
                    true,
                    "filter-info-" + i,
                    "this is a test"
            );
            filterInfos.add(filterInfo);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        filterInfos.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainApi.insert(parentPoint));
            for (FilterInfo filterInfo : filterInfos) {
                filterInfo.setKey(filterInfoMaintainApi.insert(filterInfo));
                filterInfo.setPointKey(parentPoint.getKey());
                filterInfoMaintainApi.update(filterInfo);
            }
            assertEquals(5, filterInfoMaintainApi.getFilterInfos(parentPoint.getKey(), LookupPagingInfo.LOOKUP_ALL).getCount());
        } finally {
            for (FilterInfo filterInfo : filterInfos) {
                filterInfoMaintainApi.delete(filterInfo.getKey());
            }
            pointMaintainApi.delete(parentPoint.getKey());
        }
    }

}