package com.dwarfeng.fdr.impl.maintain.self.service;

import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.cache.ChannelEntityCache;
import com.dwarfeng.fdr.stack.dao.ChannelDao;
import com.dwarfeng.fdr.stack.service.ChannelMaintainService;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ChannelMaintainServiceImplTest {

    @Autowired
    ChannelMaintainService channelMaintainService;
    @Autowired
    ChannelEntityCache channelEntityCache;
    @Autowired
    ChannelDao channelDao;
    @Autowired
    PointMaintainService pointMaintainService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Rollback(value = false)
    public void get() throws Exception {
        PointMaintainServiceImplTest.NameKeyImpl nameKey = new PointMaintainServiceImplTest.NameKeyImpl("test-point");
        Point point = new PointMaintainServiceImplTest.PointImpl(nameKey, "string", true, "this is a test");
        ChannelKeyImpl channelKey = new ChannelKeyImpl("test-point", "test-channel");
        Channel channel = new ChannelImpl(channelKey, true, true, "this is a test");

        try {
            if (channelMaintainService.exists(channelKey)) channelMaintainService.remove(channelKey);
            if (pointMaintainService.exists(nameKey)) pointMaintainService.remove(nameKey);
            pointMaintainService.add(point);
            channelMaintainService.add(channel);

            //删除缓存中的数据，第一次调用的是持久层中的数据。
            channelEntityCache.delete(channelKey);
            channel = channelMaintainService.get(channelKey);
            assertEquals(channelKey, channel.getKey());
            assertTrue(channel.isDefaultChannel());
            assertTrue(channel.isEnabled());
            assertEquals("this is a test", channel.getRemark());
            //第一次调用的是数据持久层中的数据，第二次调用的是缓存中的数据。
            channel = channelMaintainService.get(channelKey);
            assertEquals(channelKey, channel.getKey());
            assertTrue(channel.isDefaultChannel());
            assertTrue(channel.isEnabled());
            assertEquals("this is a test", channel.getRemark());
        } finally {
            if (channelMaintainService.exists(channelKey)) channelMaintainService.remove(channelKey);
            if (pointMaintainService.exists(nameKey)) pointMaintainService.remove(nameKey);
        }
    }

    @Test
    @Rollback(value = false)
    public void add() throws Exception {
        PointMaintainServiceImplTest.NameKeyImpl nameKey = new PointMaintainServiceImplTest.NameKeyImpl("test-point");
        Point point = new PointMaintainServiceImplTest.PointImpl(nameKey, "string", true, "this is a test");
        ChannelKeyImpl channelKey = new ChannelKeyImpl("test-point", "test-channel");
        Channel channel = new ChannelImpl(channelKey, true, true, "this is a test");

        try {
            if (channelMaintainService.exists(channelKey)) channelMaintainService.remove(channelKey);
            if (pointMaintainService.exists(nameKey)) pointMaintainService.remove(nameKey);
            pointMaintainService.add(point);
            channelMaintainService.add(channel);

            assertTrue(channelMaintainService.exists(channelKey));
            assertTrue(channelEntityCache.exists(channelKey));
            assertTrue(channelDao.exists(channelKey));
        } finally {
            if (channelMaintainService.exists(channelKey)) channelMaintainService.remove(channelKey);
            if (pointMaintainService.exists(nameKey)) pointMaintainService.remove(nameKey);
        }
    }


    public static class ChannelKeyImpl implements ChannelKey {

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
            return "ChannelKeyImpl{" +
                    "pointName='" + pointName + '\'' +
                    ", channelName='" + channelName + '\'' +
                    '}';
        }

    }


    public static class ChannelImpl implements Channel {

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
            return "ChannelImpl{" +
                    "key=" + key +
                    ", defaultChannel=" + defaultChannel +
                    ", enabled=" + enabled +
                    ", remark='" + remark + '\'' +
                    '}';
        }
    }

}