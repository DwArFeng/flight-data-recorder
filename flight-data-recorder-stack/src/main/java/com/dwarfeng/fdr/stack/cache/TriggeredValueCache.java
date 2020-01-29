package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 被触发数据缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggeredValueCache extends BatchBaseCache<LongIdKey, TriggeredValue> {
}
