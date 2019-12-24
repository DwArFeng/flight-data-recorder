package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.CacheException;

/**
 * 被过滤数据缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilteredValueCache extends BaseCache<UuidKey, FilteredValue> {

    void deleteAllByPoint(UuidKey pointKey) throws CacheException;

    void deleteAllByFilterInfo(UuidKey filterInfoKey) throws CacheException;
}
