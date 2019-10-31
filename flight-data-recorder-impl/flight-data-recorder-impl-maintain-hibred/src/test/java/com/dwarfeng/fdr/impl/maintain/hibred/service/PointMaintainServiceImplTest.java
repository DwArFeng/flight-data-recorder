package com.dwarfeng.fdr.impl.maintain.hibred.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
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

import java.util.Objects;

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

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Rollback(value = false)
    public void get() throws Exception {
        NameKeyImpl nameKey = new NameKeyImpl("test-point");
        Point point = new PointImpl(nameKey, "string", true, "this is a test");

        try {
            if (pointMaintainService.exists(nameKey)) pointMaintainService.remove(nameKey);
            pointMaintainService.add(point);

            //删除缓存中的数据，第一次调用的是持久层中的数据。
            pointEntityCache.delete(nameKey);
            point = pointMaintainService.get(nameKey);
            assertEquals(nameKey, point.getKey());
            assertEquals("string", point.getType());
            assertTrue(point.isPersistence());
            assertEquals("this is a test", point.getRemark());
            //第一次调用的是数据持久层中的数据，第二次调用的是缓存中的数据。
            point = pointMaintainService.get(nameKey);
            assertEquals(nameKey, point.getKey());
            assertEquals("string", point.getType());
            assertTrue(point.isPersistence());
            assertEquals("this is a test", point.getRemark());
        } finally {
            if (pointMaintainService.exists(nameKey)) pointMaintainService.remove(nameKey);
        }
    }

    @Test
    @Rollback(value = false)
    public void add() throws Exception {
        NameKeyImpl nameKey = new NameKeyImpl("test-point");
        PointImpl point = new PointImpl(nameKey, "string", true, "this is a test");

        try {
            if (pointMaintainService.exists(nameKey)) pointMaintainService.remove(nameKey);
            pointMaintainService.add(point);
            assertTrue(pointMaintainService.exists(nameKey));
            assertTrue(pointEntityCache.exists(nameKey));
            assertTrue(pointDao.exists(nameKey));
        } finally {
            if (pointMaintainService.exists(nameKey)) pointMaintainService.remove(nameKey);
        }
    }

    public static class NameKeyImpl implements NameKey {

        private static final long serialVersionUID = -6372678944223074527L;

        private String name;

        public NameKeyImpl() {
        }

        public NameKeyImpl(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (Objects.isNull(o)) return false;
            if (!(o instanceof NameKey)) return false;

            NameKey that = (NameKey) o;

            return Objects.equals(this.getName(), that.getName());
        }

        @Override
        public int hashCode() {
            return getName() != null ? getName().hashCode() : 0;
        }

        @Override
        public String toString() {
            return "NameKeyImpl [name=" + name + "]";
        }

    }

    public static class PointImpl implements Point {

        private static final long serialVersionUID = 5413283787016073082L;

        private NameKey key;
        private String type;
        private boolean isPersistence;
        private String remark;

        public PointImpl() {
        }

        public PointImpl(NameKey key, String type, boolean isPersistence, String remark) {
            this.key = key;
            this.type = type;
            this.isPersistence = isPersistence;
            this.remark = remark;
        }

        @Override
        public NameKey getKey() {
            return key;
        }

        public void setKey(NameKey key) {
            this.key = key;
        }

        @Override
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public boolean isPersistence() {
            return isPersistence;
        }

        public void setPersistence(boolean isPersistence) {
            this.isPersistence = isPersistence;
        }

        @Override
        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public String toString() {
            return "PointImpl [key=" + key + ", type=" + type + ", isPersistence=" + isPersistence + ", remark="
                    + remark + "]";
        }

    }

}