package com.dwarfeng.fdr.impl.cache.redis.cache;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.utils.RedisUtil;
import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * BaseCache的抽象实现。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Validated
public abstract class AbstractBaseCacheDelegate<K, V> {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @TimeAnalyse
    public boolean exists(@NotNull K key) throws CacheException {
        return RedisUtil.exists(redisTemplate, key2Object(key));
    }

    @TimeAnalyse
    public boolean existsAll(@NotNull Collection<K> c) throws CacheException {
        for (K key : c) {
            if (!RedisUtil.exists(redisTemplate, key2Object(key))) return false;
        }
        return true;
    }

    @TimeAnalyse
    public boolean existsNon(@NotNull Collection<K> c) throws CacheException {
        for (K key : c) {
            if (RedisUtil.exists(redisTemplate, key2Object(key))) return false;
        }
        return true;
    }

    @TimeAnalyse
    public V get(@NotNull K key) throws CacheException {
        return object2Value(RedisUtil.get(redisTemplate, key2Object(key)));
    }

    @TimeAnalyse
    public void push(@NotNull K key, @NotNull V value, long timeout, @NotNull TimeUnit timeUnit) throws CacheException {
        RedisUtil.push(redisTemplate, key2Object(key), value2Object(value), timeout, timeUnit);
    }

    @TimeAnalyse
    public void batchPush(@NotNull Map<K, V> map, long timeout, @NotNull TimeUnit timeUnit) throws CacheException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            RedisUtil.push(redisTemplate, key2Object(entry.getKey()), value2Object(entry.getValue()), timeout, timeUnit);
        }
    }

    @TimeAnalyse
    public void delete(@NotNull K key) throws CacheException {
        RedisUtil.delete(redisTemplate, key2Object(key));
    }

    @TimeAnalyse
    public void batchDelete(@NotNull Collection<K> c) throws CacheException {
        for (K key : c) {
            RedisUtil.delete(redisTemplate, key2Object(key));
        }
    }

    /**
     * 将指定的键转换为对象。
     *
     * @param key 指定的键。
     * @return 指定的键转换成的对象。
     */
    protected abstract Object key2Object(@NotNull K key);

    /**
     * 将指定的值转换为对象。
     *
     * @param value 指定的值。
     * @return 指定的值转换成的对象。
     */
    protected abstract Object value2Object(@NotNull V value);

    /**
     * 将指定的对象转换为值。
     *
     * @param object 指定的对象。
     * @return 指定的对象转换成的值。
     */
    protected abstract V object2Value(@NotNull Object object);

}
