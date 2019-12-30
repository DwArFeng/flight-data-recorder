package com.dwarfeng.fdr.api.record.dubbo.api.impl;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.sdk.validation.bean.dto.ValidationDataInfo;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.dto.RecordResult;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.RecordService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Component
public class RecordApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordApiDelegate.class);
    @Autowired
    public ValidateBean validateBean;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RecordService service;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    public RecordResult record(DataInfo dataInfo) throws ServiceException {
        ValidationDataInfo validationDataInfo = mapper.map(dataInfo, ValidationDataInfo.class);
        try {
            validateBean.validateRecord(validationDataInfo);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.record(dataInfo);
    }

    @Component
    @Validated
    public static class ValidateBean {

        @Validated({Default.class})
        public void validateRecord(@Valid @NotNull ValidationDataInfo validationDataInfo) throws ConstraintViolationException {
        }
    }
}
