package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 被过滤数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilteredValueMaintainService extends CrudService<LongIdKey, FilteredValue>, PresetLookupService<FilteredValue> {

    String CHILD_FOR_POINT = "child_for_point";
    String CHILD_FOR_FILTER = "child_for_filter";
    String CHILD_FOR_FILTER_SET = "child_for_filter_set";
}
