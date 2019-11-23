package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisCategory;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${cache.format.one_to_many.category_has_child}")
    private String keyFormat;

    @Transactional(readOnly = true)
    @TimeAnalyse
    public boolean exists(@NotNull UuidKey key) throws CacheException {
        try {
            return template.hasKey(uuidKey2String(key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(readOnly = true)
    @TimeAnalyse
    public long size(@NotNull UuidKey key) throws CacheException {
        try {
            return template.opsForList().size(uuidKey2String(key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(readOnly = true)
    @TimeAnalyse
    public List<Category> get(@NotNull UuidKey key, @Min(0) int beginIndex, @Min(0) int maxSize) throws CacheException {
        try {
            Long totleSize = template.opsForList().size(uuidKey2String(key));
            List<RedisCategory> redisCategories = template.opsForList().range(uuidKey2String(key), beginIndex, Math.max(totleSize, beginIndex + maxSize) - 1);
            List<Category> categories = new ArrayList<>();
            for (RedisCategory redisCategory : redisCategories) {
                categories.add(mapper.map(redisCategory, Category.class));
            }
            return categories;
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional
    @TimeAnalyse
    public void set(UuidKey key, List<? extends Category> value, long timeout) throws CacheException {
        try {
            String keyString = uuidKey2String(key);
            template.delete(keyString);
            template.opsForList().rightPushAll(keyString, toRedisCategories(value));
            template.expire(keyString, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional
    @TimeAnalyse
    public void push(UuidKey key, List<? extends Category> value, long timeout) throws CacheException {
        try {
            String keyString = uuidKey2String(key);
            template.opsForList().rightPushAll(keyString, toRedisCategories(value));
            template.expire(keyString, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    private List<RedisCategory> toRedisCategories(List<? extends Category> categories) {
        List<RedisCategory> redisCategories = new ArrayList<>();
        for (Category category : categories) {
            redisCategories.add(mapper.map(category, RedisCategory.class));
        }
        return redisCategories;
    }

    @Transactional
    @TimeAnalyse
    public void delete(UuidKey key) throws CacheException {
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
