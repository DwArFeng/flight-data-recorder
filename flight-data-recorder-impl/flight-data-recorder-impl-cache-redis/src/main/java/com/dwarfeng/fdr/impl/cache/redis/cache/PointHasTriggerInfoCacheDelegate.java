package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.RedisTriggerInfo;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
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
public class PointHasTriggerInfoCacheDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryHasPointCacheDelegate.class);

    @Autowired
    private RedisTemplate<String, RedisTriggerInfo> template;
    @Autowired
    private Mapper mapper;

    @Value("${cache.format.one_to_many.point_has_trigger_info}")
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
    public List<TriggerInfo> get(@NotNull UuidKey key, @Min(0) int beginIndex, @Min(0) int maxSize) throws CacheException {
        try {
            Long totleSize = template.opsForList().size(uuidKey2String(key));
            List<RedisTriggerInfo> redisTriggerInfos = template.opsForList().range(uuidKey2String(key), beginIndex, Math.max(totleSize, beginIndex + maxSize) - 1);
            List<TriggerInfo> triggerInfos = new ArrayList<>();
            for (RedisTriggerInfo redisTriggerInfo : redisTriggerInfos) {
                triggerInfos.add(mapper.map(redisTriggerInfo, TriggerInfo.class));
            }
            return triggerInfos;
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional
    @TimeAnalyse
    public void set(UuidKey key, List<? extends TriggerInfo> value, long timeout) throws CacheException {
        try {
            String keyString = uuidKey2String(key);
            template.delete(keyString);
            template.opsForList().rightPushAll(keyString, toTriggerInfos(value));
            template.expire(keyString, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    @Transactional
    @TimeAnalyse
    public void push(UuidKey key, List<? extends TriggerInfo> value, long timeout) throws CacheException {
        try {
            String keyString = uuidKey2String(key);
            template.opsForList().rightPushAll(keyString, toTriggerInfos(value));
            template.expire(keyString, timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new CacheException("缓存异常", e);
        }
    }

    private List<RedisTriggerInfo> toTriggerInfos(List<? extends TriggerInfo> triggerInfos) {
        List<RedisTriggerInfo> redisTriggerInfos = new ArrayList<>();
        for (TriggerInfo triggerInfo : triggerInfos) {
            redisTriggerInfos.add(mapper.map(triggerInfo, RedisTriggerInfo.class));
        }
        return redisTriggerInfos;
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
