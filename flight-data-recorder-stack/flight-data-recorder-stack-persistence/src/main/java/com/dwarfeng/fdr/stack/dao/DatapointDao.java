package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.DatapointConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Datapoint;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 数据点数据访问层。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface DatapointDao extends BaseDao<NameKey, Datapoint, DatapointConstraint> {

	/**
	 * 将指定的数据点与数据值建立联系。
	 * 
	 * @param datapointKey 指定的数据点对应的键。
	 * @param dataValueKey 指定的数据值对应的键。
	 */
	public void setDatavalue(NameKey datapointKey, UuidKey dataValueKey);

	/**
	 * 将指定的数据点与触发器设置建立联系。
	 * 
	 * @param datapointKey      指定的数据点对应的键。
	 * @param triggerSettingKey 指定的触发器设置对应的键。
	 */
	public void setTriggerSetting(NameKey datapointKey, UuidKey triggerSettingKey);

	/**
	 * 将指定数据点相关的所有数据值清除关系。
	 * 
	 * @param datapointKey 指定的数据点对应的键。
	 */
	public void clearDatavalue(NameKey datapointKey);

	/**
	 * 将指定的数据点相关的所有触发器设置清除关系。
	 * 
	 * @param datapointKey 指定的数据点对应的键。
	 */
	public void clearTriggerSetting(NameKey datapointKey);

}
