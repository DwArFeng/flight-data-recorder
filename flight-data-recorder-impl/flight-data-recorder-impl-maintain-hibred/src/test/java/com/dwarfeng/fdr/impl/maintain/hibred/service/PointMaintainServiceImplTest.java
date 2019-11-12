package com.dwarfeng.fdr.impl.maintain.hibred.service;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.impl.bean.testbean.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.bean.testbean.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.cache.PointEntityCache;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PointMaintainServiceImplTest {

    @Autowired
    PointMaintainService pointMaintainService;
    @Autowired
    PointEntityCache pointEntityCache;
    @Autowired
    PointDao pointDao;

    private com.dwarfeng.fdr.impl.bean.testbean.bean.key.UuidKeyImpl key;
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
    @Rollback(value = false)
    public void get() throws Exception {
        try {
            if (pointMaintainService.exists(key)) pointMaintainService.remove(key);
            pointMaintainService.add(point);

            //删除缓存中的数据，第一次调用的是持久层中的数据。
            pointEntityCache.delete(key);
            Point localPoint = pointMaintainService.get(key);
            assertEquals(key, localPoint.getKey());
            assertEquals("string", localPoint.getType());
            assertTrue(localPoint.isPersistence());
            assertEquals("this is a test", localPoint.getRemark());
            //第一次调用的是数据持久层中的数据，第二次调用的是缓存中的数据。
            localPoint = pointMaintainService.get(key);
            assertEquals(key, localPoint.getKey());
            assertEquals("string", localPoint.getType());
            assertTrue(localPoint.isPersistence());
            assertEquals("this is a test", localPoint.getRemark());
        } finally {
            if (pointMaintainService.exists(key)) pointMaintainService.remove(key);
        }
    }

    @Test
    @Rollback(value = false)
    public void add() throws Exception {
        try {
            if (pointMaintainService.exists(key)) pointMaintainService.remove(key);
            pointMaintainService.add(point);
            assertTrue(pointMaintainService.exists(key));
            assertTrue(pointEntityCache.exists(key));
            assertTrue(pointDao.exists(key));
        } finally {
            if (pointMaintainService.exists(key)) pointMaintainService.remove(key);
        }
    }

}