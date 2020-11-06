package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 映射 QOS 服务。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public interface MapQosService extends Service {

    /**
     * 获取指定类型的映射器。
     *
     * @param mapperType 指定的类型。
     * @return 指定类型的映射器，或者是 null。
     * @throws ServiceException 服务异常。
     */
    Mapper getMapper(String mapperType) throws ServiceException;

    /**
     * 清除本地缓存。
     *
     * @throws ServiceException 服务异常。
     */
    void clearLocalCache() throws ServiceException;
}
