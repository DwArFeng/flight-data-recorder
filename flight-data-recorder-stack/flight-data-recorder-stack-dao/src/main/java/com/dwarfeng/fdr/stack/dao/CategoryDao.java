package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.CategoryConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 分类数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface CategoryDao extends BaseDao<UuidKey, Category, CategoryConstraint> {


}
