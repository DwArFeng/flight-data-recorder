package com.dwarfeng.fdr.api.maintain.dubbo.api;

import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.Service;

/**
 * 实体增删改查服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface EntityCrudApi<K, E> extends Service {

    /**
     * 获取实体。
     *
     * @param key 实体的键。
     * @return 实体的键对应的实体。
     */
    E get(K key) throws ServiceException;

    /**
     * 插入实体。
     *
     * @param element 指定的实体。
     * @return 指定的实体对应的主键。
     */
    K insert(E element) throws ServiceException;

    /**
     * 更新实体。
     *
     * @param element 更新的实体。
     * @return 更新实体对应的键。
     */
    K update(E element) throws ServiceException;

    /**
     * 删除实体。
     *
     * @param key 实体的键。
     */
    void delete(K key) throws ServiceException;

}
