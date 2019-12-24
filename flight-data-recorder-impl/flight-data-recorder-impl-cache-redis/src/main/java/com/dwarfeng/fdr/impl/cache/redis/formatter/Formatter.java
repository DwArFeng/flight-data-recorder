package com.dwarfeng.fdr.impl.cache.redis.formatter;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Formatter<T> {

    String format(String prefix, T target);
}
