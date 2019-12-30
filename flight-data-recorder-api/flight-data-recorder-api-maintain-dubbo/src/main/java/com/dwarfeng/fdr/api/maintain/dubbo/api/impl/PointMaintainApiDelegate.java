package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.sdk.validation.bean.dto.ValidationLookupPagingInfo;
import com.dwarfeng.fdr.sdk.validation.bean.entity.ValidationPoint;
import com.dwarfeng.fdr.sdk.validation.bean.key.ValidationGuidKey;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.dto.PagedData;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
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
public class PointMaintainApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PointMaintainApiDelegate.class);
    @Autowired
    public ValidateBean validateBean;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("pointMaintainService")
    private PointMaintainService service;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    public PagedData<Point> getPoints(GuidKey pointGuidKey, LookupPagingInfo lookupPagingInfo) throws ServiceException {
        ValidationGuidKey validationGuidKey = mapper.map(pointGuidKey, ValidationGuidKey.class);
        ValidationLookupPagingInfo validationLookupPagingInfo = mapper.map(lookupPagingInfo, ValidationLookupPagingInfo.class);
        try {
            validateBean.validateGetChilds(validationGuidKey, validationLookupPagingInfo);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.getPoints(pointGuidKey, lookupPagingInfo);
    }

    @TimeAnalyse
    public Point get(GuidKey guidKey) throws ServiceException {
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
    public GuidKey insert(Point point) throws ServiceException {
        ValidationPoint validationPoint = mapper.map(point, ValidationPoint.class);
        try {
            validateBean.validateInsert(validationPoint);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.insert(point);
    }

    @TimeAnalyse
    public GuidKey update(Point point) throws ServiceException {
        ValidationPoint validationPoint = mapper.map(point, ValidationPoint.class);
        try {
            validateBean.validateUpdate(validationPoint);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.update(point);
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
        public void validateGetChilds(@Valid @NotNull ValidationGuidKey validationGuidKey, @Valid @NotNull ValidationLookupPagingInfo validationLookupPagingInfo)
                throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateGet(@Valid @NotNull ValidationGuidKey validationGuidKey) throws ConstraintViolationException {
        }

        @Validated({Insert.class})
        public void validateInsert(@Valid @NotNull ValidationPoint validationPoint) throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateUpdate(@Valid @NotNull ValidationPoint validationPoint) throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateDelete(@Valid @NotNull ValidationGuidKey validationGuidKey) throws ConstraintViolationException {
        }
    }
}
