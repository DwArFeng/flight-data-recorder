package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.ValueImpl;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.cache.ValueEntityCache;
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
public class ValueEntityCacheImpl implements ValueEntityCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueEntityCacheImpl.class);

    @Autowired
    private RedisTemplate<UuidKeyImpl, ValueImpl> redisTemplate;
    @Autowired
    private Mapper mapper;

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public boolean exists(NameKey key) throws CacheException {
        UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
        try {
            return redisTemplate.hasKey(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法判断指定的键是否存在: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public Value get(NameKey key) throws CacheException {
        UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
        try {
            return redisTemplate.opsForValue().get(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public Value get(NameKey key, Value defaultValue) throws CacheException {
        UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
        try {
            return Optional.ofNullable((Value) redisTemplate.opsForValue().get(keyImpl)).orElse(defaultValue);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public void push(NameKey key, Value value, long timeout, TimeUnit timeUnit) throws CacheException {
        UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
        ValueImpl valueImpl = mapper.map(value, ValueImpl.class);
        try {
            redisTemplate.opsForValue().set(keyImpl, valueImpl);
            redisTemplate.expire(keyImpl, timeout, timeUnit);
        } catch (Exception e) {
            throw new CacheException("无法设置指定键的值: [键]" + keyImpl.toString() + " [值]" + valueImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public void delete(NameKey key) throws CacheException {
        UuidKeyImpl keyImpl = mapper.map(key, UuidKeyImpl.class);
        try {
            redisTemplate.delete(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法删除指定键的值: " + keyImpl.toString(), e);
        }
    }
}
