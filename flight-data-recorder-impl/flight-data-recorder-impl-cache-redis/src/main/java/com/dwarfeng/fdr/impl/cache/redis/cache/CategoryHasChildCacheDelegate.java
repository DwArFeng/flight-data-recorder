package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisCategory;
import com.dwarfeng.fdr.impl.cache.redis.formatter.Formatter;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
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
public class CategoryHasChildCacheDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryHasChildCacheDelegate.class);

    @Autowired
    private RedisTemplate<String, RedisCategory> template;
    @Autowired
    private Mapper mapper;
    @Autowired
    @Qualifier("guidKeyFormatter")
    private Formatter<GuidKey> formatter;

    @Value("${cache.prefix.one_to_many.category_has_child}")
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
    public long size(@NotNull GuidKey key) throws CacheException {
        try {
            return template.opsForList().size(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    @TimeAnalyse
    public List<Category> get(@NotNull GuidKey key, @Min(0) int beginIndex, @Min(0) int maxSize) throws CacheException {
        try {
            Long totleSize = template.opsForList().size(formatter.format(keyPrefix, key));
            List<RedisCategory> redisCategories = template.opsForList().range(formatter.format(keyPrefix, key), beginIndex, Math.max(totleSize, beginIndex + maxSize) - 1);
            List<Category> categories = new ArrayList<>();
            for (RedisCategory redisCategory : redisCategories) {
                categories.add(mapper.map(redisCategory, Category.class));
            }
            return categories;
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    @TimeAnalyse
    public void set(@NotNull GuidKey key, List<? extends Category> value, long timeout) throws CacheException {
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
    public void push(@NotNull GuidKey key, List<? extends Category> value, long timeout) throws CacheException {
        try {
            String keyString = formatter.format(keyPrefix, key);
            mayRightPushAll(keyString, value);
            template.expire(keyString, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    private void mayRightPushAll(String keyString, List<? extends Category> value) {
        if (value.isEmpty()) {
            return;
        }
        template.opsForList().rightPushAll(keyString, toRedisCategories(value));
    }

    private List<RedisCategory> toRedisCategories(List<? extends Category> categories) {
        List<RedisCategory> redisCategories = new ArrayList<>();
        for (Category category : categories) {
            redisCategories.add(mapper.map(category, RedisCategory.class));
        }
        return redisCategories;
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
