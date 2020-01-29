package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 持久化数据缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PersistenceValueCache extends BatchBaseCache<LongIdKey, PersistenceValue> {
}
