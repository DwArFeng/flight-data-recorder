package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.ValueConstraint;
import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.NameKey;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 数据值数据访问层。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface ValueDao extends BaseDao<UuidKey, Value, ValueConstraint> {

	/**
	 * 将指定的数据值与指定的通道建立联系。
	 * 
	 * @param valueKey   指定的数据值键。
	 * @param channelKey 指定的通道键。
	 */
	public void setChannel(UuidKey valueKey, NameKey channelKey);

	/**
	 * 将指定的数据值通道清除联系。
	 * 
	 * @param valueKey 指定的数据值键。
	 */
	public void clearChannel(UuidKey valueKey);

	/**
	 * 获得指定的数据值对应的通道。
	 * 
	 * @param valueKey 指定的数据值键。
	 * @return 指定的数据值对应的通道。
	 */
	public Channel getChannel(UuidKey valueKey);

	/**
	 * 获取指定数据值的前一个数据值。
	 * 
	 * @param valueKey 指定数据值键。
	 * @return 指定数据值的前一个数据值。
	 */
	public Value getPreviousValue(UuidKey valueKey);

	/**
	 * 获取指定数据值的下一个数据值。
	 * 
	 * @param valueKey 指定数据值键。
	 * @return 指定数据值的下一个数据值。
	 */
	public Value getNextValue(UuidKey valueKey);

}
