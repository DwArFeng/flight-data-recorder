package com.dwarfeng.fdr.stack.bean.key;

/**
 * 使用名称作为主键。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface NameKey extends Key {

	/**
	 * 获取名称主键的名称。
	 * 
	 * @return 名称主键的名称。
	 */
	public String getName();

	@Override
	public boolean equals(Object obj);

	@Override
	public int hashCode();

}
