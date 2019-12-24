package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisCategory;
import com.dwarfeng.fdr.impl.cache.redis.formatter.Formatter;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.Category;
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
public class CategoryCacheDelegate {

    @Autowired
    private RedisTemplate<String, RedisCategory> template;
    @Autowired
    private Mapper mapper;
    @Autowired
    @Qualifier("uuidKeyFormatter")
    private Formatter<UuidKey> formatter;

    @Value("${cache.prefix.entity.category}")
    private String keyPrefix;

    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    @TimeAnalyse
    public boolean exists(@NotNull UuidKey key) throws CacheException {
        try {
            return template.hasKey(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "daoTransactionManager", readOnly = true)
    @TimeAnalyse
    public Category get(@NotNull UuidKey key) throws CacheException {
        try {
            RedisCategory redisCategory = template.opsForValue().get(formatter.format(keyPrefix, key));
            return mapper.map(redisCategory, Category.class);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public void push(@NotNull UuidKey key, @NotNull Category category, @Min(0) long timeout) throws CacheException {
        try {
            RedisCategory redisCategory = mapper.map(category, RedisCategory.class);
            template.opsForValue().set(formatter.format(keyPrefix, key), redisCategory, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public void delete(@NotNull UuidKey key) throws CacheException {
        try {
            template.delete(formatter.format(keyPrefix, key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }
}
