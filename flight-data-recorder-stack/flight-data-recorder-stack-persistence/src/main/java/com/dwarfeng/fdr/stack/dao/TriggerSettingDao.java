package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.TriggerSettingConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Datapoint;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 触发器设置数据访问层。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerSettingDao extends BaseDao<UuidKey, TriggerSetting, TriggerSettingConstraint> {

	/**
	 * 将指定的触发器设置与数据点建立关系。
	 * 
	 * @param triggerSettingKey 指定的触发器设置对应的键。
	 * @param datapointKey      指定的数据点对应的键。
	 */
	public void setDatapoint(UuidKey triggerSettingKey, NameKey datapointKey);

	/**
	 * 将指定的触发器设置与相关的数据点移除关系。
	 * 
	 * @param triggerSettingKey 指定的触发器设置对应的键。
	 */
	public void clearDatapoint(UuidKey triggerSettingKey);

	/**
	 * 获取指定的触发器设置相关的数据点。
	 * 
	 * @param triggerSettingKey 指定的触发器设置对应的键。
	 * @return 指定的触发器设置相关的数据点。
	 */
	public Datapoint getDatapoint(UuidKey triggerSettingKey);

}
