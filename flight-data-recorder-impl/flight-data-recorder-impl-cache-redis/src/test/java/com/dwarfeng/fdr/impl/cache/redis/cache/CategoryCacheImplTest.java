package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.cache.CategoryCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class CategoryCacheImplTest {

    @Autowired
    private CategoryCache cache;

    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent",
                "test-parent"
        );
    }

    @After
    public void tearDown() throws Exception {
        category = null;
    }

    @Test
    public void test() throws CacheException {
        cache.push(category.getKey(), category, 600000);
    }
}