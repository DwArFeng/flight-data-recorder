package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.PointImpl;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.sdk.aspect.TimeAnalyseAspect;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.cache.PointCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 数据点缓存的实现。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Repository
public class PointCacheImpl implements PointCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointCacheImpl.class);

    @Autowired
    private RedisTemplate<NameKeyImpl, PointImpl> redisTemplate;
    @Autowired
    private Mapper mapper;

    @Override
    @TimeAnalyseAspect.TimeAnalyse
    public boolean exists(NameKey key) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        try {
            return redisTemplate.hasKey(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法判断指定的键是否存在: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAspect.TimeAnalyse
    public Point get(NameKey key) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        try {
            return redisTemplate.opsForValue().get(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAspect.TimeAnalyse
    public Point get(NameKey key, Point defaultValue) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        try {
            return Optional.ofNullable((Point) redisTemplate.opsForValue().get(keyImpl)).orElse(defaultValue);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAspect.TimeAnalyse
    public void push(NameKey key, Point point, long timeout, TimeUnit timeUnit) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        PointImpl pointImpl = mapper.map(point, PointImpl.class);
        try {
            redisTemplate.opsForValue().set(keyImpl, pointImpl);
            redisTemplate.expire(keyImpl, timeout, timeUnit);
        } catch (Exception e) {
            throw new CacheException("无法设置指定键的值: [键]" + keyImpl.toString() + " [值]" + pointImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAspect.TimeAnalyse
    public void delete(NameKey key) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        try {
            redisTemplate.delete(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法删除指定键的值: " + keyImpl.toString(), e);
        }
    }
}
