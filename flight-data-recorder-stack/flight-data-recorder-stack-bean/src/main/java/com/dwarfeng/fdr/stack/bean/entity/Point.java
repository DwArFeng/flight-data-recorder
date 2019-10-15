package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.NameKey;

public interface Point extends Entity<NameKey> {

	/**
	 * 获取数据点的类型。
	 * 
	 * @return 数据点的类型。
	 */
	public String getType();

	/**
	 * 数据点是否持久化。
	 * 
	 * @return 数据点是否持久化记录数据。
	 */
	public boolean isPersistence();

	/**
	 * 获取数据点的备注。
	 * 
	 * @return 数据点的备注。
	 */
	public String getRemark();

}
