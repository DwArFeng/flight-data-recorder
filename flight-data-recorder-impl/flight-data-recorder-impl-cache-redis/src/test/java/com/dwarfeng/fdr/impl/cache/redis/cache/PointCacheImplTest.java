package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.cache.PointCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PointCacheImplTest {

    @Autowired
    private PointCache pointCache;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExists() throws CacheException {
        NameKeyImpl nameKeyImpl = new NameKeyImpl("test-point");
        assertFalse(pointCache.exists(nameKeyImpl));
    }

    @Test
    public void get() {
    }

    @Test
    public void testGet() {
    }

    @Test
    public void testPush() throws CacheException {
        NameKeyImpl nameKeyImpl = new NameKeyImpl("test-point");
        PointImpl pointImpl = new PointImpl(nameKeyImpl, "string", false, "this is a test");
        pointCache.push(nameKeyImpl, pointImpl, 10, TimeUnit.MINUTES);
        assertTrue(pointCache.exists(nameKeyImpl));
    }

    @Test
    public void delete() throws CacheException {

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

            if (!Objects.equals(this.getName(), that.getName())) return false;

            return true;
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