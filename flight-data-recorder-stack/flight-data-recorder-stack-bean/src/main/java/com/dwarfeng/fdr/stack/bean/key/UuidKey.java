package com.dwarfeng.fdr.stack.bean.key;

/**
 * UUID主键。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface UuidKey extends Key {

	/**
	 * 获取主键的UUID。
	 * 
	 * @return 主键的UUID。
	 */
	public String getUuid();

}
