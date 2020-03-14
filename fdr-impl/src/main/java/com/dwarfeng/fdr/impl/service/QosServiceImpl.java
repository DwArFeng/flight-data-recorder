package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.fdr.stack.service.QosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QosServiceImpl implements QosService {

    @Autowired
    private LocalCacheHandler localCacheHandler;
    @Autowired
    private RealtimeValueConsumeHandler realtimeValueConsumeHandler;
    @Autowired
    private RealtimeEventConsumeHandler realtimeEventConsumeHandler;
    @Autowired
    private PersistenceValueConsumeHandler persistenceValueConsumeHandler;
    @Autowired
    private PersistenceEventConsumeHandler persistenceEventConsumeHandler;
    @Autowired
    private FilteredValueConsumeHandler filteredValueConsumeHandler;
    @Autowired
    private FilteredEventConsumeHandler filteredEventConsumeHandler;
    @Autowired
    private TriggeredValueConsumeHandler triggeredValueConsumeHandler;
    @Autowired
    private TriggeredEventConsumeHandler triggeredEventConsumeHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    public void clearLocalCache() throws ServiceException {
        try {
            localCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void setRealtimeEventConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException {
        try {
            realtimeEventConsumeHandler.setConsumeParameter(consumerThread, bufferSize, batchSize, maxIdleTime);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void setRealtimeValueConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException {
        try {
            realtimeValueConsumeHandler.setConsumeParameter(consumerThread, bufferSize, batchSize, maxIdleTime);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void setPersistenceEventConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException {
        try {
            persistenceEventConsumeHandler.setConsumeParameter(consumerThread, bufferSize, batchSize, maxIdleTime);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void setPersistenceValueConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException {
        try {
            persistenceValueConsumeHandler.setConsumeParameter(consumerThread, bufferSize, batchSize, maxIdleTime);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void setFilteredEventConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException {
        try {
            filteredEventConsumeHandler.setConsumeParameter(consumerThread, bufferSize, batchSize, maxIdleTime);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void setFilteredValueConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException {
        try {
            filteredValueConsumeHandler.setConsumeParameter(consumerThread, bufferSize, batchSize, maxIdleTime);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void setTriggeredEventConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException {
        try {
            triggeredEventConsumeHandler.setConsumeParameter(consumerThread, bufferSize, batchSize, maxIdleTime);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void setTriggeredValueConsumeParameter(int consumerThread, int bufferSize, int batchSize, long maxIdleTime) throws ServiceException {
        try {
            triggeredValueConsumeHandler.setConsumeParameter(consumerThread, bufferSize, batchSize, maxIdleTime);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("执行qos方法时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
