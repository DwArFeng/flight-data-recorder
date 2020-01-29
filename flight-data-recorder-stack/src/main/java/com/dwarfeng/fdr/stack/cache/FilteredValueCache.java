package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 被过滤数据缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilteredValueCache extends BatchBaseCache<LongIdKey, FilteredValue> {
}
