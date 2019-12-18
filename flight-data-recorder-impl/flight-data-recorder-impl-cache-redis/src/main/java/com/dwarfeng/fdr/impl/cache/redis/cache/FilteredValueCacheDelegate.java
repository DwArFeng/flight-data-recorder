package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisFilteredValue;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
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
public class FilteredValueCacheDelegate {

    @Autowired
    private RedisTemplate<String, RedisFilteredValue> template;
    @Autowired
    private Mapper mapper;

    @Value("${cache.format.entity.filtered_value}")
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
    public FilteredValue get(@NotNull UuidKey key) throws CacheException {
        try {
            RedisFilteredValue redisFilteredValue = template.opsForValue().get(uuidKey2String(key));
            return mapper.map(redisFilteredValue, FilteredValue.class);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional(transactionManager = "daoTransactionManager")
    @TimeAnalyse
    public void push(@NotNull UuidKey key, @NotNull FilteredValue filteredValue, @Min(0) long timeout) throws CacheException {
        try {
            RedisFilteredValue redisFilteredValue = mapper.map(filteredValue, RedisFilteredValue.class);
            template.opsForValue().set(uuidKey2String(key), redisFilteredValue, timeout, TimeUnit.MILLISECONDS);
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
