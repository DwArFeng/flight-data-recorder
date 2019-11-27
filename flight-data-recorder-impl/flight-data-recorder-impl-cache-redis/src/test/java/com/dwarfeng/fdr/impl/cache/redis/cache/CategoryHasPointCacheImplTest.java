package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryHasPointCache;
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
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CategoryHasPointCacheImplTest {

    @Autowired
    private CategoryHasPointCache categoryHasPointCache;

    private Category parentCategory;
    private List<Point> points;

    @Before
    public void setUp() throws Exception {
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
    public void tearDown() throws Exception {
        parentCategory = null;
        points.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            if (categoryHasPointCache.exists(parentCategory.getKey())) {
                categoryHasPointCache.delete(parentCategory.getKey());
            }
            categoryHasPointCache.push(parentCategory.getKey(), points, 600000);
            List<Point> points = categoryHasPointCache.get(parentCategory.getKey(), 0, 5);
            assertEquals(5, points.size());
            List<UuidKey> keys = points.stream().map(Point::getKey).collect(Collectors.toList());
            List<UuidKey> childKeys = this.points.stream().map(Point::getKey).collect(Collectors.toList());
            assertTrue(keys.containsAll(childKeys));
        } finally {
            categoryHasPointCache.delete(parentCategory.getKey());
        }
    }


}