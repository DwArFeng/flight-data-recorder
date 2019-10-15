package com.dwarfeng.fdr.stack.service;

import java.util.Date;

import com.dwarfeng.fdr.stack.bean.key.NameKey;

/**
 * 数据记录服务。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface RecordService extends Service {

	/**
	 * 向指定的数据点追加数据值。
	 * 
	 * <p>
	 * 数据发生的时间为系统的当前时间。
	 * 
	 * @param pointKey 指定的数据点。
	 * @param value    追加的数据值。
	 */
	public void appendValue(NameKey pointKey, String value);

	/**
	 * 向指定的数据点追加数据值。
	 * 
	 * @param pointKey     指定的数据点。
	 * @param value        追加的数据值。
	 * @param happenedDate 数据发生的时间。
	 */
	public void appendValue(NameKey pointKey, String value, Date happenedDate);

}
