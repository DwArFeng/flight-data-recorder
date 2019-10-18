package com.dwarfeng.fdr.sdk.utils;

import com.dwarfeng.fdr.stack.exception.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis链接工具类。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RedisUtil {

    /**
     * 在指定的链接下指定的键是否存在。
     *
     * @param redisTemplate 指定的链接。
     * @param key           指定的键。
     * @return 指定的链接下指定的键是否存在。
     * @throws CacheException 缓存异常。
     */
    public static boolean exists(RedisTemplate<Object, Object> redisTemplate, Object key) throws CacheException {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            throw new CacheException("无法判断指定的键是否存在: " + key.toString(), e);
        }
    }

    /**
     * 在指定的链接下获得指定的键对应的值。
     *
     * @param redisTemplate 指定的链接。
     * @param key           指定的键。
     * @return 指定的链接下指定的键对应的值。
     * @throws CacheException 缓存异常。
     */
    public static Object get(RedisTemplate<Object, Object> redisTemplate, Object key) throws CacheException {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new CacheException("无法获得指定键的值: " + key.toString(), e);
        }
    }

    /**
     * 在指定的链接下指定的键设为指定的值。
     *
     * @param redisTemplate 指定的链接。
     * @param key           指定的键。
     * @param value         指定的值。
     * @param timeout       值的超时时间。
     * @param timeUnit      超时时间的单位。
     * @throws CacheException 缓存异常。
     */
    public static void push(RedisTemplate<Object, Object> redisTemplate, Object key, Object value, long timeout,
                            TimeUnit timeUnit) throws CacheException {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, timeout, timeUnit);
        } catch (Exception e) {
            throw new CacheException("无法设置指定键的值: [键]" + key.toString() + " [值]" + value.toString(), e);
        }
    }

    /**
     * 在指定的链接下删除指定的键。
     *
     * @param redisTemplate 指定的链接。
     * @param key           指定的键。
     * @throws CacheException 缓存异常。
     */
    public static void delete(RedisTemplate<Object, Object> redisTemplate, Object key) throws CacheException {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            throw new CacheException("无法删除指定键的值: " + key.toString(), e);
        }
    }

}
