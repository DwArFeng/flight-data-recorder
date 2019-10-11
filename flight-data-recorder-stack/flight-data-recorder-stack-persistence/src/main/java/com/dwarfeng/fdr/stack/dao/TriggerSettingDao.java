package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.TriggerSettingConstraint;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 触发器设置数据访问层。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerSettingDao extends BaseDao<NameKey, TriggerSetting, TriggerSettingConstraint> {

	/**
	 * 将指定的触发器设置与数据点添加关系。
	 * 
	 * @param triggerSettingKey 指定的触发器设置对应的键。
	 * @param pointKey          指定的数据点对应的键。
	 */
	public void addChannel(UuidKey triggerSettingKey, NameKey pointKey);

	/**
	 * 将指定的触发器设置与数据点清除关系。
	 * 
	 * @param triggerSettingKey 指定的触发器设置对应的键。
	 * @param pointKey          指定的数据点对应的键。
	 */
	public void removeChannel(UuidKey triggerSettingKey, NameKey pointKey);

	/**
	 * 将指定的触发器设置与数据点添加关系。
	 * 
	 * @param triggerSettingKey 指定的触发器设置对应的键。
	 */
	public void clearChannel(UuidKey triggerSettingKey);

}
