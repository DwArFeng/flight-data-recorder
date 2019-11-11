package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.TriggerSettingConstraint;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 触发器设置数据访问层。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerSettingDao extends BaseDao<UuidKey, TriggerSetting, TriggerSettingConstraint> {

}
