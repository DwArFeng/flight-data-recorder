package com.dwarfeng.fdr.stack.bean.key;

/**
 * 通道键。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface ChannelKey extends Key {

	/**
	 * 获取数据点的名称。
	 * 
	 * @return 数据点的名称。
	 */
	public String getPointName();

	/**
	 * 获取通道的名称。
	 * 
	 * @return 通道的名称。
	 */
	public String getChannelName();

	@Override
	public boolean equals(Object obj);

	@Override
	public int hashCode();

}
