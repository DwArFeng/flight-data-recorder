package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryHasChildCache;
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
public class CategoryHasChildCacheImplTest {

    @Autowired
    private CategoryHasChildCache categoryHasChildCache;

    private Category parentCategory;
    private List<Category> childCategories;

    @Before
    public void setUp() throws Exception {
        parentCategory = new Category(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent",
                "test-parent"
        );
        childCategories = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Category childCategory = new Category(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                    parentCategory.getKey(),
                    "child-" + (i + 1),
                    "test-child"
            );
            childCategories.add(childCategory);
        }
    }

    @After
    public void tearDown() throws Exception {
        parentCategory = null;
        childCategories.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            if (categoryHasChildCache.exists(parentCategory.getKey())) {
                categoryHasChildCache.delete(parentCategory.getKey());
            }
            categoryHasChildCache.push(parentCategory.getKey(), childCategories, 600000);
            List<Category> categories = categoryHasChildCache.get(parentCategory.getKey(), 0, 5);
            assertEquals(5, categories.size());
            List<UuidKey> keys = categories.stream().map(Category::getKey).collect(Collectors.toList());
            List<UuidKey> childKeys = childCategories.stream().map(Category::getKey).collect(Collectors.toList());
            assertTrue(keys.containsAll(childKeys));
        } finally {
            categoryHasChildCache.delete(parentCategory.getKey());
        }
    }

}