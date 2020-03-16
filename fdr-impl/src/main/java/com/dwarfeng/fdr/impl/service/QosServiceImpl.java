package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.handler.*;
import com.dwarfeng.fdr.stack.service.QosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class QosServiceImpl implements QosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QosServiceImpl.class);

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

    @Autowired(required = false)
    private List<RecordControlHandler> recordControlHandlers;

    @Autowired
    private ServiceExceptionMapper sem;

    @PostConstruct
    public void init() {
        if (Objects.isNull(recordControlHandlers)) {
            recordControlHandlers = Collections.emptyList();
        }
    }

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

    @Override
    public void recordOnline() throws ServiceException {
        List<Exception> exceptions = new ArrayList<>();
        try {
            for (RecordControlHandler recordControlHandler : recordControlHandlers) {
                try {
                    recordControlHandler.online();
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }
            if (!exceptions.isEmpty()) {
                LOGGER.warn("至少一个 recordControlHandler 上线时发生异常, 将打印所有日常详情...");
                for (int i = 0; i < exceptions.size(); i++) {
                    String format = String.format("(%d/%d) 详细信息如下:", i + 1, exceptions.size());
                    LOGGER.warn(format, exceptions.get(i));
                }
                throw exceptions.get(0);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("记录数据功能上线时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    public void recordOffline() throws ServiceException {
        List<Exception> exceptions = new ArrayList<>();
        try {
            for (RecordControlHandler recordControlHandler : recordControlHandlers) {
                try {
                    recordControlHandler.offline();
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }
            if (!exceptions.isEmpty()) {
                LOGGER.warn("至少一个 recordControlHandler 下线时发生异常, 将打印所有日常详情...");
                for (int i = 0; i < exceptions.size(); i++) {
                    String format = String.format("(%d/%d) 详细信息如下:", i + 1, exceptions.size());
                    LOGGER.warn(format, exceptions.get(i));
                }
                throw exceptions.get(0);
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("记录数据功能下线时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
