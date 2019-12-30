package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.sdk.validation.bean.entity.ValidationFilteredValue;
import com.dwarfeng.fdr.sdk.validation.bean.key.ValidationGuidKey;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
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
public class FilteredValueMaintainApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilteredValueMaintainApiDelegate.class);
    @Autowired
    public ValidateBean validateBean;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("filteredValueMaintainService")
    private FilteredValueMaintainService service;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    public FilteredValue get(GuidKey guidKey) throws ServiceException {
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
    public GuidKey insert(FilteredValue filteredValue) throws ServiceException {
        ValidationFilteredValue validationFilteredValue = mapper.map(filteredValue, ValidationFilteredValue.class);
        try {
            validateBean.validateInsert(validationFilteredValue);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.insert(filteredValue);
    }

    @TimeAnalyse
    public GuidKey update(FilteredValue filteredValue) throws ServiceException {
        ValidationFilteredValue validationFilteredValue = mapper.map(filteredValue, ValidationFilteredValue.class);
        try {
            validateBean.validateUpdate(validationFilteredValue);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.update(filteredValue);
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
        public void validateGet(@Valid @NotNull ValidationGuidKey validationGuidKey) throws ConstraintViolationException {
        }

        @Validated({Insert.class})
        public void validateInsert(@Valid @NotNull ValidationFilteredValue validationFilteredValue) throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateUpdate(@Valid @NotNull ValidationFilteredValue validationFilteredValue) throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateDelete(@Valid @NotNull ValidationGuidKey validationGuidKey) throws ConstraintViolationException {
        }
    }
}
