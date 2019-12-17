package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 分类持有子项的缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointHasTriggerInfoCache extends OneToManyCache<UuidKey, TriggerInfo> {
}
