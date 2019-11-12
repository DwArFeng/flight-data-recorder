package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.impl.bean.testbean.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.bean.testbean.bean.key.UuidKeyImpl;
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
    private PointImpl point;

    @Before
    public void setUp() throws Exception {
        key = UuidKeyImpl.of(UUIDUtil.toDenseString(UUID.randomUUID()));
        point = new PointImpl(
                key,
                "test-point",
                "string",
                true,
                "this is a test"
        );
    }

    @After
    public void tearDown() throws Exception {
        key = null;
        point = null;
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
            pointEntityCache.push(key, point, 10, TimeUnit.MINUTES);
            Point localPoint = pointEntityCache.get(key);
            assertEquals("string", localPoint.getType());
            assertTrue(localPoint.isPersistence());
            assertEquals("this is a test", localPoint.getRemark());
        } finally {
            pointEntityCache.delete(key);
        }
    }

    @Test
    public void testPush() throws CacheException {
        pointEntityCache.delete(key);
        try {
            pointEntityCache.push(key, point, 10, TimeUnit.MINUTES);
            assertTrue(pointEntityCache.exists(key));
        } finally {
            pointEntityCache.delete(key);
        }
    }

    @Test
    public void delete() throws CacheException {
        pointEntityCache.delete(key);
        try {
            pointEntityCache.push(key, point, 10, TimeUnit.MINUTES);
            assertTrue(pointEntityCache.exists(key));
            pointEntityCache.delete(key);
            assertFalse(pointEntityCache.exists(key));
        } finally {
            pointEntityCache.delete(key);
        }

    }
}