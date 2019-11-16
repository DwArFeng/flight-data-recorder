package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.constraint.Constraint;
import com.dwarfeng.fdr.stack.bean.entity.Entity;
import com.dwarfeng.fdr.stack.bean.key.Key;
import com.dwarfeng.fdr.stack.exception.ServiceException;

import java.util.Collection;
import java.util.List;

/**
 * 实体增删改查服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface EntityCrudService<K extends Key, E extends Entity<K>, C extends Constraint<E>> extends Service {

    /**
     * 获取实体。
     *
     * @param key 实体的键。
     * @return 实体的键对应的实体。
     */
    E get(K key) throws ServiceException;

    /**
     * 批量获取实体。
     *
     * @param keys 指定的键组成的列表。
     * @return 指定的键对应的实体组成的列表。
     * @throws ServiceException 服务异常。
     */
    List<? extends E> batchGet(List<? extends K> keys) throws ServiceException;


    /**
     * 插入实体。
     *
     * @param element 指定的实体。
     * @return 指定的实体对应的主键。
     */
    K insert(E element) throws ServiceException;

    /**
     * 批量插入实体。
     *
     * @param c 指定的实体组成的集合。
     * @throws ServiceException 服务异常。
     */
    void batchInsert(Collection<? extends E> c) throws ServiceException;

    /**
     * 更新实体。
     *
     * @param element 更新的实体。
     * @return 更新实体对应的键。
     */
    K update(E element) throws ServiceException;


    /**
     * 批量更新实体。
     *
     * @param c 指定的实体组成的集合。
     * @throws ServiceException 服务异常。
     */
    void batchUpdate(Collection<? extends E> c) throws ServiceException;

    /**
     * 删除实体。
     *
     * @param key 实体的键。
     */
    void delete(K key) throws ServiceException;

    /**
     * 批量删除实体。
     *
     * @param c 指定的实体的键组成的集合。
     * @throws ServiceException 服务异常。
     */
    void batchDelete(Collection<? extends K> c) throws ServiceException;

}
