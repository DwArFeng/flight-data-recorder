package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 被触发数据缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggeredValueCache extends BaseCache<UuidKey, TriggeredValue> {
}
