package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 服务质量服务。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface RecordQosService extends Service {

    /**
     * 清除本地缓存。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 开启记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void startRecord() throws ServiceException;

    /**
     * 关闭记录服务。
     *
     * @throws ServiceException 服务异常。
     */
    void stopRecord() throws ServiceException;
}
