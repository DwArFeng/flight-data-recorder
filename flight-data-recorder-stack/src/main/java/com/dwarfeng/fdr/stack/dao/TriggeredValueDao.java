package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.DaoException;

/**
 * 实时数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggeredValueDao extends BaseDao<UuidKey, TriggeredValue> {

    void deleteAllByPoint(UuidKey pointKey) throws DaoException;

    void deleteAllByTriggerInfo(UuidKey triggerInfoKey) throws DaoException;
}
