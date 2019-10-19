package com.dwarfeng.fdr.stack.cache;

import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 数据点缓存。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface ValueEntityCache extends BaseCache<UuidKey, Value> {
}
