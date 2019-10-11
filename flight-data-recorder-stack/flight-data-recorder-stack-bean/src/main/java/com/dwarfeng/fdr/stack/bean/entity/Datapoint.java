package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.NameKey;

/**
 * 数据点。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Datapoint extends Entity<NameKey> {

	/**
	 * 获取数据点的类型。
	 * 
	 * @return 数据点的类型。
	 */
	public String getType();

	/**
	 * 数据点是否被启用。
	 * 
	 * @return 数据点是否被启用。
	 */
	public boolean isEnabled();

	/**
	 * 获取数据点的备注。
	 * 
	 * @return 数据点的备注。
	 */
	public String getRemark();

}
