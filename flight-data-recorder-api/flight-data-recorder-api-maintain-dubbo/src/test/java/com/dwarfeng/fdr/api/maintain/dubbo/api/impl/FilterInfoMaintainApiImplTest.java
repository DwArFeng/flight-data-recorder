package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.api.maintain.dubbo.api.FilterInfoMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
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
import java.util.List;
import java.util.UUID;

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
    public void setUp() throws Exception {
        parentPoint = new Point(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        filterInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilterInfo filterInfo = new FilterInfo(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                    parentPoint.getKey(),
                    true,
                    "filter-info-" + i,
                    "this is a test"
            );
            filterInfos.add(filterInfo);
        }
    }

    @After
    public void tearDown() throws Exception {
        parentPoint = null;
        filterInfos.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            pointMaintainApi.insert(parentPoint);
            for (FilterInfo filterInfo : filterInfos) {
                filterInfoMaintainApi.insert(filterInfo);
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