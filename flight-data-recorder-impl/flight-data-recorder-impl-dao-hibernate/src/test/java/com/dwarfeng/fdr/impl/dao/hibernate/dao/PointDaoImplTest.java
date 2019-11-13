package com.dwarfeng.fdr.impl.dao.hibernate.dao;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.impl.bean.testbean.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.bean.testbean.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PointDaoImplTest {

    @Autowired
    private PointDao pointDao;

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
    @Transactional
    public void testGet() throws DaoException {
        pointDao.insert(point);
        Point localPoint = pointDao.get(key);
        assertTrue(localPoint.isPersistence());
        assertEquals("this is a test", localPoint.getRemark());
        assertEquals("string", localPoint.getType());
    }

    @Test
    @Transactional
    public void testInsert() throws DaoException {
        pointDao.insert(point);
    }

    @Test
    @Transactional
    public void testUpdate() throws DaoException {
        pointDao.insert(point);
        point.setPersistence(true);
        point.setRemark("this is an update test");
        point.setType("integer");
        pointDao.update(point);
        Point localPoint = pointDao.get(key);
        assertTrue(localPoint.isPersistence());
        assertEquals("this is an update test", localPoint.getRemark());
        assertEquals("integer", localPoint.getType());
    }

    @Test
    @Transactional
    public void testDelete() throws DaoException {
        pointDao.insert(point);
        pointDao.delete(key);
        Point point = pointDao.get(new UuidKeyImpl("test-point"));
        assertNull(point);
    }

}
