package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.PresetDeleteDao;

/**
 * 分类数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface CategoryDao extends BatchBaseDao<LongIdKey, Category>, PresetDeleteDao<LongIdKey, Category> {

}
