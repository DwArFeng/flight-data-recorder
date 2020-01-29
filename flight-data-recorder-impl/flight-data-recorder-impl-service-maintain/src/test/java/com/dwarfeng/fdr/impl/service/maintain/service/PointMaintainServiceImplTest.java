package com.dwarfeng.fdr.impl.service.maintain.service;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.CategoryMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
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
public class PointMaintainServiceImplTest {

    @Autowired
    private CategoryMaintainService categoryMaintainService;
    @Autowired
    private PointMaintainService pointMaintainService;

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
            parentCategory.setKey(categoryMaintainService.insert(parentCategory));
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                point.setCategoryKey(parentCategory.getKey());
                pointMaintainService.update(point);
            }
            assertEquals(5, pointMaintainService.lookup(PointMaintainService.CHILD_FOR_CATEGORY, new Object[]{parentCategory.getKey()}).getCount());
        } finally {
            for (Point point : points) {
                pointMaintainService.delete(point.getKey());
            }
            categoryMaintainService.delete(parentCategory.getKey());
        }
    }

}