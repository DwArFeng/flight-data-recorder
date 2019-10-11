package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.ChannelConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 通道数据访问层。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface ChannelDao extends BaseDao<ChannelKey, Channel, ChannelConstraint> {

	/**
	 * 将指定的通道与指定的数据值建立联系。
	 * 
	 * @param channelKey 指定的通道键。
	 * @param valueKey   指定的数据值键。
	 */
	public void addValue(NameKey channelKey, UuidKey valueKey);

	/**
	 * 将指定的通道与指定的数据值移除联系。
	 * 
	 * @param channelKey 指定的通道键。
	 * @param valueKey   指定的数据值键。
	 */
	public void removeValue(NameKey channelKey, UuidKey valueKey);

	/**
	 * 将指定的通道与数据值清除联系。
	 * 
	 * @param channelKey 指定的通道键。
	 */
	public void clearValue(NameKey channelKey);

	/**
	 * 获取指定通道键对应的数据点。
	 * 
	 * @param channelKey 指定的通道键。
	 * @return 指定的数据点。
	 */
	public Point getPoint(NameKey channelKey);

}
