package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.impl.cache.redis.bean.entity.TriggerSettingImpl;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.cache.TriggerSettingCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class TriggerSettingCacheImpl implements TriggerSettingCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerSettingCacheImpl.class);

    @Autowired
    private RedisTemplate<NameKeyImpl, TriggerSettingImpl> redisTemplate;
    @Autowired
    private Mapper mapper;

    @Override
    public boolean exists(NameKey key) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        try {
            return redisTemplate.hasKey(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法判断指定的键是否存在: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public TriggerSetting get(NameKey key) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        try {
            return redisTemplate.opsForValue().get(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public TriggerSetting get(NameKey key, TriggerSetting defaultValue) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        try {
            return Optional.ofNullable((TriggerSetting) redisTemplate.opsForValue().get(keyImpl)).orElse(defaultValue);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + keyImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public void push(NameKey key, TriggerSetting triggerSetting, long timeout, TimeUnit timeUnit) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        TriggerSettingImpl triggerSettingImpl = mapper.map(triggerSetting, TriggerSettingImpl.class);
        try {
            redisTemplate.opsForValue().set(keyImpl, triggerSettingImpl);
            redisTemplate.expire(keyImpl, timeout, timeUnit);
        } catch (Exception e) {
            throw new CacheException("无法设置指定键的值: [键]" + keyImpl.toString() +
                    " [值]" + triggerSettingImpl.toString(), e);
        }
    }

    @Override
    @TimeAnalyseAdvisor.TimeAnalyse
    public void delete(NameKey key) throws CacheException {
        NameKeyImpl keyImpl = mapper.map(key, NameKeyImpl.class);
        try {
            redisTemplate.delete(keyImpl);
        } catch (Exception e) {
            throw new CacheException("无法删除指定键的值: " + keyImpl.toString(), e);
        }
    }
}
