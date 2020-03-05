package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 过滤器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.1.0.a
 */
public interface FilterSupportDao extends BaseDao<StringIdKey, FilterSupport>, EntireLookupDao<FilterSupport>,
        PresetLookupDao<FilterSupport> {
}
