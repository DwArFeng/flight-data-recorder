package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;

/**
 * 被过滤数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilteredValueDao extends BaseDao<GuidKey, FilteredValue> {

    void deleteAllByPoint(GuidKey pointKey) throws DaoException;

    void deleteAllByFilterInfo(GuidKey filterInfoKey) throws DaoException;
}
