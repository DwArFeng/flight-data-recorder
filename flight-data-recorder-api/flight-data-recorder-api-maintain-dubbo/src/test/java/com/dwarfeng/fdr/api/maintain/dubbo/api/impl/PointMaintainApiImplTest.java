package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.api.maintain.dubbo.api.CategoryMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
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
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent",
                "test-parent"
        );
        points = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Point point = new Point(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
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
            categoryMaintainApi.insert(parentCategory);
            for (Point point : points) {
                pointMaintainApi.insert(point);
            }
            assertEquals(5, pointMaintainApi.getPoints(parentCategory.getKey(), new LookupPagingInfo(0, 0)).getCount());
        } finally {
            for (Point point : points) {
                pointMaintainApi.delete(point.getKey());
            }
            categoryMaintainApi.delete(parentCategory.getKey());
        }
    }

}