package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisFilterInfo;
import com.dwarfeng.fdr.impl.cache.redis.formatter.Formatter;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Validated
public class PointHasFilterInfoCacheDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryHasPointCacheDelegate.class);

    @Autowired
    private RedisTemplate<String, RedisFilterInfo> template;
    @Autowired
    private Mapper mapper;
    @Autowired
    @Qualifier("uuidKeyFormatter")
    private Formatter<UuidKey> formatter;

    @Value("${cache.prefix.one_to_many.point_has_filter_info}")
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
    public long size(@NotNull UuidKey key) throws CacheException {
        try {
            return template.opsForList().size(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public List<FilterInfo> get(@NotNull UuidKey key, @Min(0) int beginIndex, @Min(0) int maxSize) throws CacheException {
        try {
            Long totleSize = template.opsForList().size(formatter.format(keyPrefix, key));
            List<RedisFilterInfo> redisFilterInfos = template.opsForList().range(formatter.format(keyPrefix, key), beginIndex, Math.max(totleSize, beginIndex + maxSize) - 1);
            List<FilterInfo> filterInfos = new ArrayList<>();
            for (RedisFilterInfo redisFilterInfo : redisFilterInfos) {
                filterInfos.add(mapper.map(redisFilterInfo, FilterInfo.class));
            }
            return filterInfos;
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void set(@NotNull UuidKey key, List<? extends FilterInfo> value, long timeout) throws CacheException {
        try {
            String keyString = formatter.format(keyPrefix, key);
            template.delete(keyString);
            mayRightPushAll(keyString, value);
            template.expire(keyString, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void push(@NotNull UuidKey key, List<? extends FilterInfo> value, long timeout) throws CacheException {
        try {
            String keyString = formatter.format(keyPrefix, key);
            mayRightPushAll(keyString, value);
            template.expire(keyString, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    private void mayRightPushAll(String keyString, List<? extends FilterInfo> value) {
        if (value.isEmpty()) {
            return;
        }
        template.opsForList().rightPushAll(keyString, toFilterInfos(value));
    }

    private List<RedisFilterInfo> toFilterInfos(List<? extends FilterInfo> filterInfos) {
        List<RedisFilterInfo> redisFilterInfos = new ArrayList<>();
        for (FilterInfo filterInfo : filterInfos) {
            redisFilterInfos.add(mapper.map(filterInfo, RedisFilterInfo.class));
        }
        return redisFilterInfos;
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
