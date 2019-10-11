package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 触发器设置。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerSetting extends Entity<UuidKey> {

	/**
	 * 该触发器配置是否可用。
	 * 
	 * @return 该触发器配置是否可用。
	 */
	public boolean isEnabled();

	/**
	 * 获取触发器的名称。
	 * 
	 * @return 触发器的名称。
	 */
	public String getTriggerName();

	/**
	 * 获取触发器的备注。
	 * 
	 * @return 触发器的备注。
	 */
	public String getRemark();

}
