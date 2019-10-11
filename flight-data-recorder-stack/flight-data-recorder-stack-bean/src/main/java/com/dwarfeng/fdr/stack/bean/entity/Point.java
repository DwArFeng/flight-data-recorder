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
	 * 数据点是否记录历史数据。
	 * 
	 * @return 是否记录历史数据。
	 */
	public boolean isRecord();

	/**
	 * 获取数据点的备注。
	 * 
	 * @return 数据点的备注。
	 */
	public String getRemark();

}
