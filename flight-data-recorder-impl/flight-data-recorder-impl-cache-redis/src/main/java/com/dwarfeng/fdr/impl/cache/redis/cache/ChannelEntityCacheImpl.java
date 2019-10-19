package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.ChannelImpl;
import com.dwarfeng.fdr.impl.cache.redis.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.ChannelKeyImpl;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.cache.ChannelEntityCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Repository
public class ChannelEntityCacheImpl extends AbstractBaseCache<ChannelKey, Channel> implements ChannelEntityCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEntityCacheImpl.class);

    @Autowired
    private Mapper mapper;
    @Value("${key_format.entity.channel}")
    private String keyFormat;

    @Override
    protected Object key2Object(ChannelKey key) {
        return String.format(keyFormat, key.getPointName(), key.getChannelName());
    }

    @Override
    protected Object value2Object(Channel value) {
        return mapper.map(value, ChannelImpl.class);
    }

    @Override
    protected Channel object2Value(Object object) {
        return (Channel) object;
    }
}
