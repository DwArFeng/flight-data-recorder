package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.PresetDeleteDao;

/**
 * 过滤器信息数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerInfoDao extends BatchBaseDao<LongIdKey, TriggerInfo>, PresetDeleteDao<LongIdKey, TriggerInfo> {

}
