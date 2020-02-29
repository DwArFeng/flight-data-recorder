package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 过滤器信息缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilterInfoCache extends BatchBaseCache<LongIdKey, FilterInfo> {
}
