package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.CrudService;
import com.dwarfeng.subgrade.stack.service.PresetDeleteService;

/**
 * 分类维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface CategoryMaintainService extends CrudService<LongIdKey, Category>, PresetDeleteService<Category> {

    String CHILD_FOR_PARENT = "child_for_parent";
}
