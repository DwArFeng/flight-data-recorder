package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisPoint;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${cache.format.entity.point}")
    private String keyFormat;

    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    @TimeAnalyse
    public boolean exists(@NotNull UuidKey key) throws CacheException {
        try {
            return template.hasKey(uuidKey2String(key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    @TimeAnalyse
    public Point get(@NotNull UuidKey key) throws CacheException {
        try {
            RedisPoint redisPoint = template.opsForValue().get(uuidKey2String(key));
            return mapper.map(redisPoint, Point.class);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public void push(@NotNull UuidKey key, @NotNull Point point, @Min(0) long timeout) throws CacheException {
        try {
            RedisPoint redisPoint = mapper.map(point, RedisPoint.class);
            template.opsForValue().set(uuidKey2String(key), redisPoint, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public void delete(@NotNull UuidKey key) throws CacheException {
        try {
            template.delete(uuidKey2String(key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    private String uuidKey2String(UuidKey uuidKey) {
        return String.format(keyFormat, uuidKey.getUuid());
    }

}
