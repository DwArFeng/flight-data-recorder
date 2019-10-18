package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.ChannelImpl;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.ChannelKeyImpl;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.cache.ChannelEntityCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ChannelEntityCacheImpl implements ChannelEntityCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEntityCacheImpl.class);

    @Autowired
    private RedisTemplate<ChannelKeyImpl, ChannelImpl> redisTemplate;
    @Autowired
    private Mapper mapper;

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public boolean exists(ChannelKey key) throws CacheException {
        ChannelKeyImpl keyImpl = mapper.map(key, ChannelKeyImpl.class);
        try {
            return redisTemplate.hasKey(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法判断指定的键是否存在: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public Channel get(ChannelKey key) throws CacheException {
        ChannelKeyImpl keyImpl = mapper.map(key, ChannelKeyImpl.class);
        try {
            return redisTemplate.opsForValue().get(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public Channel get(ChannelKey key, Channel defaultValue) throws CacheException {
        ChannelKeyImpl keyImpl = mapper.map(key, ChannelKeyImpl.class);
        try {
            return Optional.ofNullable((Channel) redisTemplate.opsForValue().get(keyImpl)).orElse(defaultValue);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public void push(ChannelKey key, Channel channel, long timeout, TimeUnit timeUnit) throws CacheException {
        ChannelKeyImpl keyImpl = mapper.map(key, ChannelKeyImpl.class);
        ChannelImpl channelImpl = mapper.map(channel, ChannelImpl.class);
        try {
            redisTemplate.opsForValue().set(keyImpl, channelImpl);
            redisTemplate.expire(keyImpl, timeout, timeUnit);
        } catch (Exception e) {
            throw new CacheException("无法设置指定键的值: [键]" + keyImpl.toString() + " [值]" + channelImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public void delete(ChannelKey key) throws CacheException {
        ChannelKeyImpl keyImpl = mapper.map(key, ChannelKeyImpl.class);
        try {
            redisTemplate.delete(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法删除指定键的值: " + keyImpl.toString(), e);
        }
    }
}
