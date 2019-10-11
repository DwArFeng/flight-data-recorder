package com.dwarfeng.fdr.stack.bean.entity;

import java.util.Date;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 历史值。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Datavalue extends Entity<UuidKey> {

	/**
	 * 获取历史值的发生日期。
	 * 
	 * @return 历史值的发生日期。
	 */
	public Date getHappenedDate();

	/**
	 * 获取历史值的值。
	 * 
	 * @return 历史值的值。
	 */
	public String getValue();

	/**
	 * 获取历史的触发器信息。
	 * 
	 * @return 历史的触发器信息。
	 */
	public String getTriggerMessage();

}
