package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisRealtimeValue;
import com.dwarfeng.fdr.impl.cache.redis.formatter.Formatter;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
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
public class RealtimeValueCacheDelegate {

    @Autowired
    private RedisTemplate<String, RedisRealtimeValue> template;
    @Autowired
    private Mapper mapper;
    @Autowired
    @Qualifier("uuidKeyFormatter")
    private Formatter<UuidKey> formatter;

    @Value("${cache.prefix.entity.realtime_value}")
    private String keyPrefix;

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public boolean exists(@NotNull UuidKey key) throws CacheException {
        try {
            return template.hasKey(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public RealtimeValue get(@NotNull UuidKey key) throws CacheException {
        try {
            RedisRealtimeValue redisRealtimeValue = template.opsForValue().get(formatter.format(keyPrefix, key));
            return mapper.map(redisRealtimeValue, RealtimeValue.class);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void push(@NotNull UuidKey key, @NotNull RealtimeValue realtimeValue, @Min(0) long timeout) throws CacheException {
        try {
            RedisRealtimeValue redisRealtimeValue = mapper.map(realtimeValue, RedisRealtimeValue.class);
            template.opsForValue().set(formatter.format(keyPrefix, key), redisRealtimeValue, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void delete(@NotNull UuidKey key) throws CacheException {
        try {
            template.delete(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }
}
