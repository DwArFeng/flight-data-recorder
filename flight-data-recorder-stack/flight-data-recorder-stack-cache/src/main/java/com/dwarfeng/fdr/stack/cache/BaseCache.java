package com.dwarfeng.fdr.stack.cache;

import java.util.concurrent.TimeUnit;

/**
 * 基础缓存接口。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface BaseCache<K, V> extends Cache {

    /**
     * 获取缓存中指定的键是否存在。
     *
     * @param key 指定的键是否存在。
     * @return 指定的键是否存在。
     */
    public boolean exists(K key);

    /**
     * 获取缓存中指定键对应的值。
     *
     * @param key 指定的键。
     * @return 指定的键对应的值。
     */
    public V get(K key);

    /**
     * 获取缓存中指定键对应的值。
     *
     * @param key          指定的键。
     * @param defaultValue 当键不存在的时候的默认值。
     * @return 指定的键对应的值或默认值。
     */
    public V get(K key, V defaultValue);

    /**
     * 向缓存中推送指定的键与值。
     * <p>
     * 如果指定的键不存在，则创建。
     *
     * @param key      指定的键。
     * @param value    指定的键对应的值。
     * @param timeout  超时时间。
     * @param timeUnit 时间单位。
     */
    public void push(K key, V value, long timeout, TimeUnit timeUnit);

    /**
     * 从缓存中删除指定的键。
     *
     * @param key 指定的键。
     */
    public void delete(K key);

}
