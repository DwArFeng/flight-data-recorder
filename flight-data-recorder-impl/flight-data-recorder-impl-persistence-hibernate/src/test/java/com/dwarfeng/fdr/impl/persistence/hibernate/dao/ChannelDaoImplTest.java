package com.dwarfeng.fdr.impl.persistence.hibernate.dao;

import com.dwarfeng.fdr.impl.persistence.hibernate.dao.PointDaoImplTest.NameKeyImpl;
import com.dwarfeng.fdr.impl.persistence.hibernate.dao.PointDaoImplTest.PointImpl;
import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.dao.ChannelDao;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.exception.DaoException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ChannelDaoImplTest {

    @Autowired
    private PointDao pointDao;
    @Autowired
    private ChannelDao channelDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    @Transactional
    public void testGet() throws DaoException {
        PointImpl pointImpl = new PointImpl();
        pointImpl.setKey(new NameKeyImpl("test-point"));
        pointImpl.setPersistence(false);
        pointImpl.setRemark("this is a test");
        pointImpl.setType("string");
        pointDao.insert(pointImpl);
        ChannelImpl channelImpl = new ChannelImpl();
        channelImpl.setKey(new ChannelKeyImpl("test-point", "test-channel"));
        channelImpl.setDefaultChannel(false);
        channelImpl.setEnabled(false);
        channelImpl.setRemark("this is a test");
        channelDao.insert(channelImpl);
        Channel channel = channelDao.get(new ChannelKeyImpl("test-point", "test-channel"));
        assertFalse(channel.isDefaultChannel());
        assertFalse(channel.isEnabled());
        assertEquals("this is a test", channel.getRemark());
    }

    @Test
    @Transactional
    public void testInsert() throws DaoException {
        PointImpl pointImpl = new PointImpl();
        pointImpl.setKey(new NameKeyImpl("test-point"));
        pointImpl.setPersistence(false);
        pointImpl.setRemark("this is a test");
        pointImpl.setType("string");
        pointDao.insert(pointImpl);
        ChannelImpl channelImpl = new ChannelImpl();
        channelImpl.setKey(new ChannelKeyImpl("test-point", "test-channel"));
        channelImpl.setDefaultChannel(false);
        channelImpl.setEnabled(false);
        channelImpl.setRemark("this is a test");
        channelDao.insert(channelImpl);
    }

    @Test
    @Transactional
    public void testUpdate() throws DaoException {
        PointImpl pointImpl = new PointImpl();
        pointImpl.setKey(new NameKeyImpl("test-point"));
        pointImpl.setPersistence(false);
        pointImpl.setRemark("this is a test");
        pointImpl.setType("string");
        pointDao.insert(pointImpl);
        ChannelImpl channelImpl = new ChannelImpl();
        channelImpl.setKey(new ChannelKeyImpl("test-point", "test-channel"));
        channelImpl.setDefaultChannel(false);
        channelImpl.setEnabled(false);
        channelImpl.setRemark("this is a test");
        channelDao.insert(channelImpl);
        channelImpl.setDefaultChannel(true);
        channelImpl.setEnabled(true);
        channelImpl.setRemark("this is an update test");
        channelDao.update(channelImpl);
        Channel channel = channelDao.get(new ChannelKeyImpl("test-point", "test-channel"));
        assertTrue(channel.isDefaultChannel());
        assertTrue(channel.isEnabled());
        assertEquals("this is an update test", channel.getRemark());
    }

    @Test
    @Transactional
    public void testDelete() throws DaoException {
        PointImpl pointImpl = new PointImpl();
        pointImpl.setKey(new NameKeyImpl("test-point"));
        pointImpl.setPersistence(false);
        pointImpl.setRemark("this is a test");
        pointImpl.setType("string");
        pointDao.insert(pointImpl);
        ChannelImpl channelImpl = new ChannelImpl();
        channelImpl.setKey(new ChannelKeyImpl("test-point", "test-channel"));
        channelImpl.setDefaultChannel(false);
        channelImpl.setEnabled(false);
        channelImpl.setRemark("this is a test");
        channelDao.insert(channelImpl);
        channelDao.delete(new ChannelKeyImpl("test-point", "test-channel"));
        Channel channel = channelDao.get(new ChannelKeyImpl("test-point", "test-channel"));
        assertNull(channel);
    }

    public static class ChannelKeyImpl implements ChannelKey {

        private static final long serialVersionUID = -4137565402535373484L;

        private String pointName;
        private String channelName;

        public ChannelKeyImpl() {
        }

        public ChannelKeyImpl(String pointName, String channelName) {
            this.pointName = pointName;
            this.channelName = channelName;
        }

        @Override
        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }

        @Override
        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (Objects.isNull(o)) return false;
            if (!(o instanceof ChannelKey)) return false;

            ChannelKey that = (ChannelKey) o;

            if (!Objects.equals(this.getPointName(), that.getPointName())) return false;
            if (!Objects.equals(this.getChannelName(), that.getChannelName())) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = getPointName() != null ? getPointName().hashCode() : 0;
            result = 31 * result + (getChannelName() != null ? getChannelName().hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "ChannelKeyImpl [pointName=" + pointName + ", channelName=" + channelName + "]";
        }

    }

    public static class ChannelImpl implements Channel {

        private static final long serialVersionUID = 842176455905671112L;

        private ChannelKey key;
        private boolean defaultChannel;
        private boolean enabled;
        private String remark;

        public ChannelImpl() {
        }

        public ChannelImpl(ChannelKey key, boolean defaultChannel, boolean enabled, String remark) {
            this.key = key;
            this.defaultChannel = defaultChannel;
            this.enabled = enabled;
            this.remark = remark;
        }

        @Override
        public ChannelKey getKey() {
            return key;
        }

        public void setKey(ChannelKey key) {
            this.key = key;
        }

        @Override
        public boolean isDefaultChannel() {
            return defaultChannel;
        }

        public void setDefaultChannel(boolean defaultChannel) {
            this.defaultChannel = defaultChannel;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
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
            return "ChannelImpl [key=" + key + ", defaultChannel=" + defaultChannel + ", enabled=" + enabled
                    + ", remark=" + remark + "]";
        }

    }

}
