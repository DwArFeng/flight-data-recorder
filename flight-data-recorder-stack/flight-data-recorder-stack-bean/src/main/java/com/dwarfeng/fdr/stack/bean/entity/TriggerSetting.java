package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.NameKey;

/**
 * 触发器设置。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerSetting extends Entity<NameKey> {

	/**
	 * 获取触发器的数据。
	 * 
	 * @return 触发器的数据。
	 */
	public String getTriggerData();

	/**
	 * 获取触发器的备注。
	 * 
	 * @return 触发器的备注。
	 */
	public String getRemark();

}
