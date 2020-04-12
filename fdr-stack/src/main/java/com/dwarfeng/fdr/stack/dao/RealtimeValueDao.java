package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;

/**
 * 实时数据数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface RealtimeValueDao extends BatchBaseDao<LongIdKey, RealtimeValue>, EntireLookupDao<RealtimeValue> {
}
