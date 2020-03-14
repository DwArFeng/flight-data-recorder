package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 服务质量服务。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface QosService extends Service {

    /**
     * 清除本地缓存。
     */
    void clearLocalCache() throws ServiceException;

    /**
     * 设置实时数据事件消费参数。
     */
    void setRealtimeEventConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException;

    /**
     * 设置实时数据值消费参数。
     */
    void setRealtimeValueConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException;

    /**
     * 设置持久数据事件消费参数。
     */
    void setPersistenceEventConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException;

    /**
     * 设置持久数据值消费参数。
     */
    void setPersistenceValueConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException;

    /**
     * 设置过滤数据事件消费参数。
     */
    void setFilteredEventConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException;

    /**
     * 设置过滤数据值消费参数。
     */
    void setFilteredValueConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException;

    /**
     * 设置触发数据事件消费参数。
     */
    void setTriggeredEventConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException;

    /**
     * 设置触发数据值消费参数。
     */
    void setTriggeredValueConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException;
}
