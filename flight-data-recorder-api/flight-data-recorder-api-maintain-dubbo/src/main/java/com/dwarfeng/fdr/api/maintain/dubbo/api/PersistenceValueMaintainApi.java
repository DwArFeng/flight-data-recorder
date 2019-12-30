package com.dwarfeng.fdr.api.maintain.dubbo.api;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;

/**
 * 持久化数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PersistenceValueMaintainApi extends EntityCrudApi<GuidKey, PersistenceValue> {

}
