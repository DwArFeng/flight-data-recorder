package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.cache.ChannelEntityCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ChannelEntityCacheImplTest {

    @Autowired
    private ChannelEntityCache channelEntityCache;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void exists() throws CacheException {
        channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
        try {
            ChannelKeyImpl channelKeyImpl = new ChannelKeyImpl("test-point", "test-channel");
            assertFalse(channelEntityCache.exists(channelKeyImpl));
        } finally {
            channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
        }
    }

    @Test
    public void testGet() throws CacheException {
        channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
        try {
            assertNull(channelEntityCache.get(new ChannelKeyImpl("test-point", "test-channel")));
            ChannelKeyImpl channelKeyImpl = new ChannelKeyImpl("test-point", "test-channel");
            ChannelImpl channelImpl = new ChannelImpl(channelKeyImpl, true, true, "this is a test");
            channelEntityCache.push(channelKeyImpl, channelImpl, 10, TimeUnit.MINUTES);
            Channel channel = channelEntityCache.get(new ChannelKeyImpl("test-point", "test-channel"));
            assertTrue(channel.isDefaultChannel());
            assertTrue(channel.isEnabled());
            assertEquals("this is a test", channel.getRemark());
        } finally {
            channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
        }
    }

    @Test
    public void testPush() throws CacheException {
        channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
        try {
            ChannelKeyImpl channelKeyImpl = new ChannelKeyImpl("test-point", "test-channel");
            ChannelImpl channelImpl = new ChannelImpl(channelKeyImpl, true, true, "this is a test");
            channelEntityCache.push(channelKeyImpl, channelImpl, 10, TimeUnit.MINUTES);
            assertTrue(channelEntityCache.exists(channelKeyImpl));
        } finally {
            channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
        }
    }

    @Test
    public void delete() throws CacheException {
        channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
        try {
            ChannelKeyImpl channelKeyImpl = new ChannelKeyImpl("test-point", "test-channel");
            ChannelImpl channelImpl = new ChannelImpl(channelKeyImpl, true, true, "this is a test");
            channelEntityCache.push(channelKeyImpl, channelImpl, 10, TimeUnit.MINUTES);
            assertTrue(channelEntityCache.exists(new ChannelKeyImpl("test-point", "test-channel")));
            channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
            assertFalse(channelEntityCache.exists(new ChannelKeyImpl("test-point", "test-channel")));
        } finally {
            channelEntityCache.delete(new ChannelKeyImpl("test-point", "test-channel"));
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