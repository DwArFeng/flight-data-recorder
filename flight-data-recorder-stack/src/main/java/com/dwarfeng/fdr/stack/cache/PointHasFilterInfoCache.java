package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;

/**
 * 分类持有子项的缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointHasFilterInfoCache extends OneToManyCache<GuidKey, FilterInfo> {
}
