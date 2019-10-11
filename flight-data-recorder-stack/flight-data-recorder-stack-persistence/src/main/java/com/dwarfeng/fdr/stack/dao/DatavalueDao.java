package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.DatavalueConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Datapoint;
import com.dwarfeng.fdr.stack.bean.entity.Datavalue;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 数据值数据访问层。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface DatavalueDao extends BaseDao<UuidKey, Datavalue, DatavalueConstraint> {

	/**
	 * 将指定的数据值与数据点建立关系。
	 * 
	 * @param datavalueKey 指定的数据值对应的键。
	 * @param datapointKey 指定的数据点对应的键。
	 */
	public void setDatapoint(UuidKey datavalueKey, NameKey datapointKey);

	/**
	 * 将指定的数据值与相关的数据点移除关系。
	 * 
	 * @param datavalueKey 指定的数据值对应的键。
	 */
	public void clearDatapoint(UuidKey datavalueKey);

	/**
	 * 获取指定数据值相关的数据点。
	 * 
	 * @param datavalueKey 指定的数据值对应的键。
	 * @return 指定的数据值相关的数据点。
	 */
	public Datapoint getDatapoint(UuidKey datavalueKey);

}
