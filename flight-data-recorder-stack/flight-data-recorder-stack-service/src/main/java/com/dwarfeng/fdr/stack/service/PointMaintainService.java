package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;

/**
 * 维护服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface PointMaintainService extends Service {

    /**
     * 获取指定的键是否存在。
     *
     * @param key 指定的键。
     * @return 指定的键是否存在。
     * @throws ServiceException 服务异常。
     */
    boolean exists(UuidKey key) throws ServiceException;

    /**
     * 获得指定的键对应的数据点。
     *
     * @param key 指定的键。
     * @return 指定的键对应的数据点。
     * @throws ServiceException 服务异常。
     */
    Point get(UuidKey key) throws ServiceException;

    /**
     * 添加数据点。
     *
     * @param point 指定的数据点。
     * @throws ServiceException 服务异常。
     */
    void add(Point point) throws ServiceException;

    /**
     * 移除数据点。
     *
     * @param key 指定的数据点对应的键。
     * @throws ServiceException 服务异常。
     */
    void remove(UuidKey key) throws ServiceException;

}
