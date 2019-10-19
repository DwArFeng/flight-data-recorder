package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.constraint.Constraint;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Entity;
import com.dwarfeng.fdr.stack.bean.key.Key;
import com.dwarfeng.fdr.stack.exception.DaoException;

import java.util.Collection;
import java.util.List;

/**
 * 基础数据访问层
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface BaseDao<K extends Key, E extends Entity<K>, C extends Constraint<E>> extends Dao {

    /**
     * 获取指定的实体是否存在。
     *
     * @param key 指定的实体的键。
     * @return 指定的实体是否存在。
     * @throws DaoException 数据访问层异常。
     */
    public boolean exists(K key) throws DaoException;

    /**
     * 获取指定的所有键是否都存在。
     *
     * @param c 指定的键组成的集合。
     * @return 指定的键是否都存在。
     * @throws DaoException 数据访问层异常。
     */
    public boolean existsAll(Collection<K> c) throws DaoException;

    /**
     * 获取指定的所有键是否都不存在。
     *
     * @param c 指定的键组成的集合。
     * @return 指定的键是否都不存在。
     * @throws DaoException 数据访问层异常。
     */
    public boolean existsNon(Collection<K> c) throws DaoException;

    /**
     * 获取实体。
     *
     * @param key 实体的键。
     * @return 实体的键对应的实体。
     */
    public E get(K key) throws DaoException;

    /**
     * 插入实体。
     *
     * @param element 指定的实体。
     * @return 指定的实体对应的主键。
     */
    public K insert(E element) throws DaoException;

    /**
     * 批量插入实体。
     *
     * @param c 指定的实体组成的集合。
     * @throws DaoException 数据访问层异常。
     */
    public void batchInsert(Collection<E> c) throws DaoException;

    /**
     * 更新实体。
     *
     * @param element 更新的实体。
     * @return 更新实体对应的键。
     */
    public K update(E element) throws DaoException;


    /**
     * 批量更新实体。
     *
     * @param c 指定的实体组成的集合。
     * @throws DaoException 数据访问层异常。
     */
    public void batchUpdate(Collection<E> c) throws DaoException;

    /**
     * 删除实体。
     *
     * @param key 实体的键。
     */
    public void delete(K key) throws DaoException;

    /**
     * 批量删除实体。
     *
     * @param c 指定的实体的键组成的集合。
     * @throws DaoException 数据访问层异常。
     */
    public void batchDelete(Collection<K> c) throws DaoException;

    /**
     * 查询指定约束和指定的分页信息下的元素组成的集合。
     *
     * @param constraint       指定的约束。
     * @param lookupPagingInfo 指定的分页信息。
     */
    public List<E> select(C constraint, LookupPagingInfo lookupPagingInfo) throws DaoException;

    /**
     * 查询指定约束下的元素的数量。
     *
     * @param constraint 指定的约束。
     * @return 指定约束下的元素的数量。
     */
    public int selectCount(C constraint) throws DaoException;

}