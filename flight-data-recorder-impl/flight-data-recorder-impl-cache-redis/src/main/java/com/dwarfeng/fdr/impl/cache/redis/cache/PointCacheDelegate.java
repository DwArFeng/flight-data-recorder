package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisPoint;
import com.dwarfeng.fdr.impl.cache.redis.formatter.Formatter;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

@Component
@Validated
public class PointCacheDelegate {

    @Autowired
    private RedisTemplate<String, RedisPoint> template;
    @Autowired
    private Mapper mapper;
    @Autowired
    @Qualifier("guidKeyFormatter")
    private Formatter<GuidKey> formatter;

    @Value("${cache.prefix.entity.point}")
    private String keyPrefix;

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public boolean exists(@NotNull GuidKey key) throws CacheException {
        try {
            return template.hasKey(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public Point get(@NotNull GuidKey key) throws CacheException {
        try {
            RedisPoint redisPoint = template.opsForValue().get(formatter.format(keyPrefix, key));
            return mapper.map(redisPoint, Point.class);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void push(@NotNull GuidKey key, @NotNull Point point, @Min(0) long timeout) throws CacheException {
        try {
            RedisPoint redisPoint = mapper.map(point, RedisPoint.class);
            template.opsForValue().set(formatter.format(keyPrefix, key), redisPoint, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void delete(@NotNull GuidKey key) throws CacheException {
        try {
            template.delete(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }
}
