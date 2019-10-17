package com.dwarfeng.fdr.stack.cache;

/**
 * 基础缓存接口。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface BaseCache<K, V> extends Cache {

    /**
     * 获取缓存中指定键对应的值。
     *
     * @param key 指定的键。
     * @return 指定的键对应的值。
     */
    public V get(K key);

    /**
     * 向缓存中推送指定的键与值。
     * <p>
     * 如果指定的键不存在，则创建。
     *
     * @param key    指定的键。
     * @param value  指定的键对应的值。
     * @param expire 超时时间。
     */
    public void push(K key, V value, long expire);

    /**
     * 从缓存中删除指定的键。
     *
     * @param key 指定的键。
     */
    public void delete(K key);

}
