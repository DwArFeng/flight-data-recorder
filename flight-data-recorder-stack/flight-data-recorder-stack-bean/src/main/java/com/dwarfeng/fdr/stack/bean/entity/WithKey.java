package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.Key;

/**
 * 拥有实体主键的对象。
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface WithKey<K extends Key> {

	/**
	 * 获取实体的主键。
	 * 
	 * @return 实体的主键。
	 */
	public K getKey();

}
