package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 持久化数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PersistenceValueDao extends BatchBaseDao<LongIdKey, PersistenceValue>,
        EntireLookupDao<PersistenceValue>, PresetLookupDao<PersistenceValue> {
}
