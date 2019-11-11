package com.dwarfeng.fdr.impl.persistence.hibernate.dao;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.impl.bean.validate.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.bean.validate.bean.key.UuidKeyImpl;
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

    @Before
    public void setUp() throws Exception {
        key = UuidKeyImpl.of(UUIDUtil.toDenseString(UUID.randomUUID()));
    }

    @After
    public void tearDown() throws Exception {
        key = null;
    }

    @Test
    @Transactional
    public void testGet() throws DaoException {
        PointImpl pointImpl = new PointImpl();
        pointImpl.setKey(key);
        pointImpl.setPersistence(false);
        pointImpl.setName("test-point");
        pointImpl.setRemark("this is a test");
        pointImpl.setType("string");
        pointDao.insert(pointImpl);
        Point point = pointDao.get(key);
        assertFalse(point.isPersistence());
        assertEquals("this is a test", point.getRemark());
        assertEquals("string", point.getType());
    }

    @Test
    @Transactional
    public void testInsert() throws DaoException {
        PointImpl pointImpl = new PointImpl();
        pointImpl.setKey(new UuidKeyImpl("test-point"));
        pointImpl.setPersistence(false);
        pointImpl.setRemark("this is a test");
        pointImpl.setType("string");
        pointDao.insert(pointImpl);
    }

    @Test
    @Transactional
    public void testUpdate() throws DaoException {
        PointImpl pointImpl = new PointImpl();
        pointImpl.setKey(new UuidKeyImpl("test-point"));
        pointImpl.setPersistence(false);
        pointImpl.setRemark("this is a test");
        pointImpl.setType("string");
        pointDao.insert(pointImpl);
        pointImpl.setPersistence(true);
        pointImpl.setRemark("this is an update test");
        pointImpl.setType("integer");
        pointDao.update(pointImpl);
        Point point = pointDao.get(new UuidKeyImpl("test-point"));
        assertTrue(point.isPersistence());
        assertEquals("this is an update test", point.getRemark());
        assertEquals("integer", point.getType());
    }

    @Test
    @Transactional
    public void testDelete() throws DaoException {
        PointImpl pointImpl = new PointImpl();
        pointImpl.setKey(new UuidKeyImpl("test-point"));
        pointImpl.setPersistence(false);
        pointImpl.setRemark("this is a test");
        pointImpl.setType("string");
        pointDao.insert(pointImpl);
        pointDao.delete(new UuidKeyImpl("test-point"));
        Point point = pointDao.get(new UuidKeyImpl("test-point"));
        assertNull(point);
    }

}
