package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.handler.ConsumeHandler;
import com.dwarfeng.fdr.stack.handler.RecordHandler;
import com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Source;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordQosServiceImpl implements RecordQosService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordQosServiceImpl.class);

    @Autowired
    private RecordLocalCacheHandler recordLocalCacheHandler;
    @Autowired
    private List<Source> sources = new ArrayList<>();
    @Autowired
    private RecordHandler recordHandler;
    @Autowired
    @Qualifier("filteredEventConsumeHandler")
    private ConsumeHandler<FilteredValue> filteredEventConsumeHandler;
    @Autowired
    @Qualifier("filteredValueConsumeHandler")
    private ConsumeHandler<FilteredValue> filteredValueConsumeHandler;
    @Autowired
    @Qualifier("triggeredEventConsumeHandler")
    private ConsumeHandler<TriggeredValue> triggeredEventConsumeHandler;
    @Autowired
    @Qualifier("triggeredValueConsumeHandler")
    private ConsumeHandler<TriggeredValue> triggeredValueConsumeHandler;
    @Autowired
    @Qualifier("realtimeEventConsumeHandler")
    private ConsumeHandler<RealtimeValue> realtimeEventConsumeHandler;
    @Autowired
    @Qualifier("realtimeValueConsumeHandler")
    private ConsumeHandler<RealtimeValue> realtimeValueConsumeHandler;
    @Autowired
    @Qualifier("persistenceEventConsumeHandler")
    private ConsumeHandler<PersistenceValue> persistenceEventConsumeHandler;
    @Autowired
    @Qualifier("persistenceValueConsumeHandler")
    private ConsumeHandler<PersistenceValue> persistenceValueConsumeHandler;

    @Autowired
    private ServiceExceptionMapper sem;

    @PreDestroy
    public void dispose() throws ServiceException {
        stopRecord();
    }

    @Override
    @BehaviorAnalyse
    public void clearLocalCache() throws ServiceException {
        try {
            recordLocalCacheHandler.clear();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("清除本地缓存时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    @BehaviorAnalyse
    public void startRecord() throws ServiceException {
        try {
            LOGGER.info("开启记录服务...");
            filteredEventConsumeHandler.start();
            filteredValueConsumeHandler.start();
            triggeredEventConsumeHandler.start();
            triggeredValueConsumeHandler.start();
            realtimeEventConsumeHandler.start();
            realtimeValueConsumeHandler.start();
            persistenceEventConsumeHandler.start();
            persistenceValueConsumeHandler.start();

            recordHandler.enable();

            for (Source source : sources) {
                source.online();
            }
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("开启记录服务时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }

    @Override
    @BehaviorAnalyse
    public void stopRecord() throws ServiceException {
        try {
            LOGGER.info("关闭记录服务...");
            for (Source source : sources) {
                source.offline();
            }

            recordHandler.enable();

            filteredEventConsumeHandler.stop();
            filteredValueConsumeHandler.stop();
            triggeredEventConsumeHandler.stop();
            triggeredValueConsumeHandler.stop();
            realtimeEventConsumeHandler.stop();
            realtimeValueConsumeHandler.stop();
            persistenceEventConsumeHandler.stop();
            persistenceValueConsumeHandler.stop();
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("关闭记录服务时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
