package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyseAdvisor;
import com.dwarfeng.fdr.sdk.utils.RedisUtil;
import com.dwarfeng.fdr.stack.cache.BaseCache;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * BaseCache的抽象实现。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public abstract class AbstractBaseCache<K, V> implements BaseCache<K, V> {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean exists(K key) throws CacheException {
        return RedisUtil.exists(redisTemplate, key2Object(key));
    }

    @Override
    public V get(K key) throws CacheException {
        return object2Value(RedisUtil.get(redisTemplate, key2Object(key)));
    }

    @Override
    public V get(K key, V defaultValue) throws CacheException {
        return Optional.ofNullable(get(key)).map(this::object2Value).orElse(defaultValue);
    }

    @Override
    public void push(K key, V value, long timeout, TimeUnit timeUnit) throws CacheException {
        RedisUtil.push(redisTemplate, key2Object(key), value2Object(value), timeout, timeUnit);
    }

    @Override
    public void delete(K key) throws CacheException {
        RedisUtil.delete(redisTemplate, key2Object(key));
    }

    /**
     * 将指定的键转换为对象。
     *
     * @param key 指定的键。
     * @return 指定的键转换成的对象。
     */
    protected abstract Object key2Object(K key);

    /**
     * 将指定的值转换为对象。
     *
     * @param value 指定的值。
     * @return 指定的值转换成的对象。
     */
    protected abstract Object value2Object(V value);

    /**
     * 将指定的对象转换为值。
     *
     * @param object 指定的对象。
     * @return 指定的对象转换成的值。
     */
    protected abstract V object2Value(Object object);

}
