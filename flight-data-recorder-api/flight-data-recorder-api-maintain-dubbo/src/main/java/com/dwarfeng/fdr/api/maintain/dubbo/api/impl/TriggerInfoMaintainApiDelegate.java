package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.sdk.validation.bean.dto.ValidationLookupPagingInfo;
import com.dwarfeng.fdr.sdk.validation.bean.entity.ValidationTriggerInfo;
import com.dwarfeng.fdr.sdk.validation.bean.key.ValidationGuidKey;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@SuppressWarnings("unused")
@Component
public class TriggerInfoMaintainApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerInfoMaintainApiDelegate.class);
    @Autowired
    public ValidateBean validateBean;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("triggerInfoMaintainService")
    private TriggerInfoMaintainService service;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    public PagedData<TriggerInfo> getTriggerInfos(GuidKey pointGuidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        ValidationGuidKey validationGuidKey = mapper.map(pointGuidKey, ValidationGuidKey.class);
        ValidationLookupPagingInfo validationLookupPagingInfo = mapper.map(lookupPagingInfo, ValidationLookupPagingInfo.class);
        try {
            validateBean.validateGetTriggerInfos(validationGuidKey, validationLookupPagingInfo);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.getTriggerInfos(pointGuidKey, lookupPagingInfo);
    }

    @TimeAnalyse
    public TriggerInfo get(GuidKey guidKey) throws ServiceException {
        ValidationGuidKey validationGuidKey = mapper.map(guidKey, ValidationGuidKey.class);
        try {
            validateBean.validateGet(validationGuidKey);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.get(guidKey);
    }

    @TimeAnalyse
    public GuidKey insert(TriggerInfo triggerInfo) throws ServiceException {
        ValidationTriggerInfo validationTriggerInfo = mapper.map(triggerInfo, ValidationTriggerInfo.class);
        try {
            validateBean.validateInsert(validationTriggerInfo);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.insert(triggerInfo);
    }

    @TimeAnalyse
    public GuidKey update(TriggerInfo triggerInfo) throws ServiceException {
        ValidationTriggerInfo validationTriggerInfo = mapper.map(triggerInfo, ValidationTriggerInfo.class);
        try {
            validateBean.validateUpdate(validationTriggerInfo);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.update(triggerInfo);
    }

    @TimeAnalyse
    public void delete(GuidKey guidKey) throws ServiceException {
        ValidationGuidKey validationGuidKey = mapper.map(guidKey, ValidationGuidKey.class);
        try {
            validateBean.validateDelete(validationGuidKey);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        service.delete(guidKey);
    }

    @Component
    @Validated
    public static class ValidateBean {
        @Validated({Default.class})
        public void validateGetTriggerInfos(@Valid @NotNull ValidationGuidKey validationGuidKey, @Valid @NotNull ValidationLookupPagingInfo validationLookupPagingInfo)
                throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateGet(@Valid @NotNull ValidationGuidKey validationGuidKey) throws ConstraintViolationException {
        }

        @Validated({Insert.class})
        public void validateInsert(@Valid @NotNull ValidationTriggerInfo validationTriggerInfo) throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateUpdate(@Valid @NotNull ValidationTriggerInfo validationTriggerInfo) throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateDelete(@Valid @NotNull ValidationGuidKey validationGuidKey) throws ConstraintViolationException {
        }
    }
}
