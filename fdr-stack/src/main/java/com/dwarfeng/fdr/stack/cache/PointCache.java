package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.cache.BatchBaseCache;

/**
 * 数据点缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointCache extends BatchBaseCache<LongIdKey, Point> {
}