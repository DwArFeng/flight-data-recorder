package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.PresetDeleteDao;

/**
 * 实时数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggeredValueDao extends BatchBaseDao<LongIdKey, TriggeredValue>, PresetDeleteDao<LongIdKey, TriggeredValue> {
}
