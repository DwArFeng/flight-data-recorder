package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 持久化数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PersistenceValueMaintainService extends CrudService<LongIdKey, PersistenceValue>, PresetLookupService<PersistenceValue> {

    String CHILD_FOR_POINT = "child_for_point";
}
