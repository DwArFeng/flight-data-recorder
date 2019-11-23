package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@Validated
public class CategoryCacheDelegate {

    @Autowired
    private RedisTemplate<String, Category> template;
    @Autowired
    private Mapper mapper;

    @Value("${cache.format.entity.category}")
    private String keyFormat;

    @TimeAnalyse
    public boolean exists(@NotNull UuidKey key) throws CacheException {
        try {
            Category category = template.opsForValue().get(uuidKey2String(key));
            return Objects.nonNull(category);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @TimeAnalyse
    public Category get(@NotNull UuidKey key) throws CacheException {
        try {
            return template.opsForValue().get(uuidKey2String(key));
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @TimeAnalyse
    public void push(@NotNull UuidKey key, @NotNull Category value, @Min(0) long timeout, @NotNull TimeUnit timeUnit) throws CacheException {
        try {
            template.opsForValue().set(uuidKey2String(key), value, timeout, timeUnit);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

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
