package com.dwarfeng.fdr.stack.dao;

import java.util.List;

import com.dwarfeng.fdr.stack.bean.constraint.Constraint;
import com.dwarfeng.fdr.stack.bean.dto.PagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Entity;
import com.dwarfeng.fdr.stack.bean.key.Key;

/**
 * 基础数据访问层
 * 
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface BaseDao<K extends Key, E extends Entity<K>, C extends Constraint<E>> extends Dao {

	/**
	 * 获取实体。
	 * 
	 * @param key 实体的键。
	 * @return 实体的键对应的实体。
	 */
	public E get(K key);

	/**
	 * 插入实体。
	 * 
	 * @param element 指定的实体。
	 * @return 指定的实体对应的主键。
	 */
	public K insert(E element);

	/**
	 * 更新实体。
	 * 
	 * @param element 更新的实体。
	 * @return 更新实体对应的键。
	 */
	public K update(E element);

	/**
	 * 删除实体。
	 * 
	 * @param key 实体的键。
	 */
	public void delete(K key);

	/**
	 * 查询指定约束和指定的分页信息下的元素组成的集合。
	 * 
	 * @param constraint 指定的约束。
	 * @param pagingInfo 指定的分页信息。
	 */
	public List<E> select(C constraint, PagingInfo pagingInfo);

	/**
	 * 查询指定约束下的元素的数量。
	 * 
	 * @param constraint 指定的约束。
	 * @return 指定约束下的元素的数量。
	 */
	public int selectCount(C constraint);

}