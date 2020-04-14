package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 持久化数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PersistenceValueMaintainService extends BatchCrudService<LongIdKey, PersistenceValue>,
        EntireLookupService<PersistenceValue>, PresetLookupService<PersistenceValue> {

    String BETWEEN = "between";
    String CHILD_FOR_POINT = "child_for_point";
    String CHILD_FOR_POINT_BETWEEN = "child_for_point_between";
}
