package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.PointConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;

/**
 * 数据点数据访问层。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointDao extends BaseDao<NameKey, Point, PointConstraint> {

	/**
	 * 将指定的数据点与指定的通道建立联系。
	 * 
	 * @param pointKey   指定的数据点。
	 * @param channelKey 指定的通道。
	 */
	public void addChannel(NameKey pointKey, NameKey channelKey);

	/**
	 * 将指定的数据点与指定的通道解除联系。
	 * 
	 * @param pointKey   指定的数据点。
	 * @param channelKey 指定的通道。
	 */
	public void removeChannel(NameKey pointKey, NameKey channelKey);

	/**
	 * 清除数据点所关联的所有通道。
	 * 
	 * @param pointKey 数据点所关联的所有通道。
	 */
	public void clearChannel(NameKey pointKey);

}
