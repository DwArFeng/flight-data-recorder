package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 被触发数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggeredValueMaintainService extends CrudService<LongIdKey, TriggeredValue>, PresetLookupService<TriggeredValue> {

    String CHILD_FOR_POINT = "child_for_point";
    String CHILD_FOR_TRIGGER = "child_for_trigger";
    String CHILD_FOR_TRIGGER_SET = "child_for_trigger_set";
}
