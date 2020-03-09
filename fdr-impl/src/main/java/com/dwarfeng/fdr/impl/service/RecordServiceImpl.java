package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.service.RecordService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Autowired
    private ServiceExceptionMapper sem;

    @Override
    @BehaviorAnalyse
    public void record(@NotNull DataInfo dataInfo) throws ServiceException {
        try {
            //TODO
            LOGGER.error("记录数据: " + dataInfo);
        } catch (Exception e) {
            throw ServiceExceptionHelper.logAndThrow("记录数据信息时发生异常",
                    LogLevel.WARN, sem, e
            );
        }
    }
}
