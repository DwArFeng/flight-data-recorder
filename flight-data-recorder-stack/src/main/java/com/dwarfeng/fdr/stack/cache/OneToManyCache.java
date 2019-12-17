package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.exception.CacheException;

import java.util.List;

/**
 * 一对多缓存。
 */
public interface OneToManyCache<K, V> extends Cache {

    /**
     * 获取缓存中指定的键是否存在。
     *
     * @param key 指定的键是否存在。
     * @return 指定的键是否存在。
     * @throws CacheException 缓存异常。
     */
    boolean exists(K key) throws CacheException;

    /**
     * 获取指定的键对应的元素的数量。
     *
     * @param key 指定的键。
     * @return 指定的键对应的元素的数量。
     * @throws CacheException 缓存异常。
     */
    long size(K key) throws CacheException;

    /**
     * 获取缓存中指定的键对应的值。
     *
     * @param key        指定的键。
     * @param beginIndex 指定的起始元素。
     * @param maxSize    指定的最大数量。
     * @return 对应的值组成的列表。
     * @throws CacheException 缓存异常。
     */
    List<V> get(K key, int beginIndex, int maxSize) throws CacheException;

    /**
     * 向缓存中指定的键设置值。
     *
     * @param key
     * @param value
     * @param timeout 超时时间（毫秒）
     * @throws CacheException
     */
    void set(K key, List<? extends V> value, long timeout) throws CacheException;

    /**
     * 向缓存指定的键追加值。
     *
     * @param key
     * @param value
     * @param timeout 超时时间（毫秒）
     * @throws CacheException
     */
    void push(K key, List<? extends V> value, long timeout) throws CacheException;


    /**
     * 从缓存中删除指定的键。
     *
     * @param key 指定的键。
     * @throws CacheException 缓存异常。
     */
    void delete(K key) throws CacheException;
}
