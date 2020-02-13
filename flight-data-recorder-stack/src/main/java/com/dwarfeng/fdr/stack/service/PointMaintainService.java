package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.PresetLookupService;

/**
 * 数据点维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointMaintainService extends CrudService<LongIdKey, Point>, PresetLookupService<Point> {

    String CHILD_FOR_CATEGORY = "child_for_category";
    String CHILD_FOR_CATEGORY_SET = "child_for_category_set";
}
