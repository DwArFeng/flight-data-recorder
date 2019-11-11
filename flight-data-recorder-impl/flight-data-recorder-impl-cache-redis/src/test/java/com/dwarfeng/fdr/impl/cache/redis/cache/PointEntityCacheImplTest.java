package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.impl.cache.redis.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.cache.PointEntityCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PointEntityCacheImplTest {

    @Autowired
    private PointEntityCache pointEntityCache;

    private UuidKeyImpl key;

    @Before
    public void setUp() throws Exception {
        key = UuidKeyImpl.of(UUIDUtil.toDenseString(UUID.randomUUID()));
    }

    @After
    public void tearDown() throws Exception {
        key = null;
    }

    @Test
    public void testExists() throws CacheException {
        pointEntityCache.delete(key);
        try {
            assertFalse(pointEntityCache.exists(key));
        } finally {
            pointEntityCache.delete(key);
        }
    }

    @Test
    public void testGet() throws CacheException {
        pointEntityCache.delete(key);
        try {
            assertNull(pointEntityCache.get(key));
            PointImpl pointImpl = new PointImpl(key, "test-point", "string", false, "this is a test");
            pointEntityCache.push(key, pointImpl, 10, TimeUnit.MINUTES);
            Point point = pointEntityCache.get(key);
            assertEquals("string", point.getType());
            assertFalse(point.isPersistence());
            assertEquals("this is a test", point.getRemark());
        } finally {
            pointEntityCache.delete(key);
        }
    }

    @Test
    public void testPush() throws CacheException {
        pointEntityCache.delete(key);
        try {
            PointImpl pointImpl = new PointImpl(key, "test-point", "string", false, "this is a test");
            pointEntityCache.push(key, pointImpl, 10, TimeUnit.MINUTES);
            assertTrue(pointEntityCache.exists(key));
        } finally {
            pointEntityCache.delete(key);
        }
    }

    @Test
    public void delete() throws CacheException {
        pointEntityCache.delete(key);
        try {
            PointImpl pointImpl = new PointImpl(key, "point-test", "string", false, "this is a test");
            pointEntityCache.push(key, pointImpl, 10, TimeUnit.MINUTES);
            assertTrue(pointEntityCache.exists(key));
            pointEntityCache.delete(key);
            assertFalse(pointEntityCache.exists(key));
        } finally {
            pointEntityCache.delete(key);
        }

    }
}