package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisFilteredValue;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisGuidKey;
import com.dwarfeng.fdr.impl.cache.redis.formatter.Formatter;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Validated
public class FilteredValueCacheDelegate {

    @Autowired
    private RedisTemplate<String, RedisFilteredValue> template;
    @Autowired
    private Mapper mapper;
    @Autowired
    @Qualifier("guidKeyFormatter")
    private Formatter<GuidKey> formatter;

    @Value("${cache.prefix.entity.filtered_value}")
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
    public FilteredValue get(@NotNull GuidKey key) throws CacheException {
        try {
            RedisFilteredValue redisFilteredValue = template.opsForValue().get(formatter.format(keyPrefix, key));
            return mapper.map(redisFilteredValue, FilteredValue.class);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void push(@NotNull GuidKey key, @NotNull FilteredValue filteredValue, @Min(0) long timeout) throws CacheException {
        try {
            RedisFilteredValue redisFilteredValue = mapper.map(filteredValue, RedisFilteredValue.class);
            template.opsForValue().set(formatter.format(keyPrefix, key), redisFilteredValue, timeout, TimeUnit.MILLISECONDS);
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

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void deleteAllByPoint(GuidKey pointKey) throws CacheException {
        try {
            RedisGuidKey redisGuidKey = mapper.map(pointKey, RedisGuidKey.class);
            Set<String> keys = template.keys(keyPrefix + "*");
            for (String key : keys) {
                RedisFilteredValue redisFilteredValue = template.opsForValue().get(key);
                if (Objects.equals(redisGuidKey, redisFilteredValue.getPointKey())) {
                    template.delete(key);
                }
            }
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void deleteAllByFilterInfo(GuidKey filterInfoKey) throws CacheException {
        try {
            RedisGuidKey redisGuidKey = mapper.map(filterInfoKey, RedisGuidKey.class);
            Set<String> keys = template.keys(keyPrefix + "*");
            for (String key : keys) {
                RedisFilteredValue redisFilteredValue = template.opsForValue().get(key);
                if (Objects.equals(redisGuidKey, redisFilteredValue.getFilterKey())) {
                    template.delete(key);
                }
            }
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }
}
