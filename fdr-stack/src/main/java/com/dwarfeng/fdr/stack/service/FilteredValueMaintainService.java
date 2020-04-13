package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import com.dwarfeng.subgrade.stack.service.EntireLookupService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 被过滤数据维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilteredValueMaintainService extends BatchCrudService<LongIdKey, FilteredValue>,
        EntireLookupService<FilteredValue>, PresetLookupService<FilteredValue> {

    String CHILD_FOR_POINT = "child_for_point";
    String CHILD_FOR_FILTER = "child_for_filter";
    String CHILD_FOR_FILTER_SET = "child_for_filter_set";
    String CHILD_FOR_POINT_BETWEEN = "child_for_point_between";
    String CHILD_FOR_FILTER_BETWEEN = "child_for_filter_between";
}
