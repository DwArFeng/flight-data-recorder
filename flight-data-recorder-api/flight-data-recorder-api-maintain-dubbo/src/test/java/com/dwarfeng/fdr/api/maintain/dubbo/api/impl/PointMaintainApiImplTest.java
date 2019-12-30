package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.api.maintain.dubbo.api.CategoryMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
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
public class PointMaintainApiImplTest {

    @Autowired
    private CategoryMaintainApi categoryMaintainApi;
    @Autowired
    private PointMaintainApi pointMaintainApi;

    private Category parentCategory;
    private List<Point> points;

    @Before
    public void setUp() {
        parentCategory = new Category(
                null,
                null,
                "parent",
                "test-parent"
        );
        points = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Point point = new Point(
                    null,
                    parentCategory.getKey(),
                    "point-" + (i + 1),
                    "test-point",
                    true,
                    true
            );
            points.add(point);
        }
    }

    @After
    public void tearDown() {
        parentCategory = null;
        points.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentCategory.setKey(categoryMaintainApi.insert(parentCategory));
            for (Point point : points) {
                point.setKey(pointMaintainApi.insert(point));
                point.setCategoryKey(parentCategory.getKey());
                pointMaintainApi.update(point);
            }
            assertEquals(5, pointMaintainApi.getPoints(parentCategory.getKey(), LookupPagingInfo.LOOKUP_ALL).getCount());
        } finally {
            for (Point point : points) {
                pointMaintainApi.delete(point.getKey());
            }
            categoryMaintainApi.delete(parentCategory.getKey());
        }
    }

}