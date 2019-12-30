package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;

/**
 * 持久化数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PersistenceValueDao extends BaseDao<GuidKey, PersistenceValue> {

    void deleteAll(GuidKey pointKey) throws DaoException;

}
