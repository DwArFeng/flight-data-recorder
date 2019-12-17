package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 实时数据缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface RealtimeValueCache extends BaseCache<UuidKey, RealtimeValue> {
}
