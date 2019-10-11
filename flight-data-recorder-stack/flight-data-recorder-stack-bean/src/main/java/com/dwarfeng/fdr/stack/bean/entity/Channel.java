package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.ChannelKey;

public interface Channel extends Entity<ChannelKey> {

	/**
	 * 通道是否是默认通道。
	 * 
	 * @return 通道是否是默认通道。
	 */
	public boolean isDefaultChannel();

	/**
	 * 通道是否启用。
	 * 
	 * @return 通道是否启用。
	 */
	public boolean isEnabled();

	/**
	 * 获取通道的备注。
	 * 
	 * @return 通道的备注。
	 */
	public String getRemark();

}
