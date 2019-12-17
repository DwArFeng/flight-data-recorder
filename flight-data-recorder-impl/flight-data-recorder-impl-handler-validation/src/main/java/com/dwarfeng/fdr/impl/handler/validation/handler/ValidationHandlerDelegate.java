package com.dwarfeng.fdr.impl.handler.validation.handler;

import com.dwarfeng.fdr.impl.handler.validation.bean.dto.ValidationLookupPagingInfo;
import com.dwarfeng.fdr.impl.handler.validation.bean.entity.*;
import com.dwarfeng.fdr.impl.handler.validation.bean.key.ValidationUuidKey;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ValidationException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

@Component
@Validated
public class ValidationHandlerDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationHandlerDelegate.class);

    @Autowired
    private Validator validator;
    @Autowired
    private Mapper mapper;

    public void uuidKeyValidation(UuidKey uuidKey) throws ValidationException {
        if (Objects.isNull(uuidKey)) {
            return;
        }

        LOGGER.debug("验证 " + uuidKey.toString() + " 的合法性...");
        ValidationUuidKey validationUuidKey = mapper.map(uuidKey, ValidationUuidKey.class);
        Set<ConstraintViolation<ValidationUuidKey>> constraintViolations = validator.validate(validationUuidKey);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(uuidKey + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + uuidKey.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void categoryValidation(Category category) throws ValidationException {
        if (Objects.isNull(category)) {
            return;
        }

        LOGGER.debug("验证 " + category.toString() + " 的合法性...");
        ValidationCategory validationCategory = mapper.map(category, ValidationCategory.class);
        Set<ConstraintViolation<ValidationCategory>> constraintViolations = validator.validate(validationCategory);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(category + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + category.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void lookupPagingInfoValidation(LookupPagingInfo lookupPagingInfo) throws ValidationException {
        if (Objects.isNull(lookupPagingInfo)) {
            return;
        }

        LOGGER.debug("验证 " + lookupPagingInfo.toString() + " 的合法性...");
        ValidationLookupPagingInfo validationLookupPagingInfo = mapper.map(lookupPagingInfo, ValidationLookupPagingInfo.class);
        Set<ConstraintViolation<ValidationLookupPagingInfo>> constraintViolations = validator.validate(validationLookupPagingInfo);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(lookupPagingInfo + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + lookupPagingInfo.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void pointValidation(Point point) throws ValidationException {
        if (Objects.isNull(point)) {
            return;
        }

        LOGGER.debug("验证 " + point.toString() + " 的合法性...");
        ValidationPoint validationPoint = mapper.map(point, ValidationPoint.class);
        Set<ConstraintViolation<ValidationPoint>> constraintViolations = validator.validate(validationPoint);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(point + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + point.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void filterInfoValidation(FilterInfo filterInfo) throws ValidationException {
        if (Objects.isNull(filterInfo)) {
            return;
        }

        LOGGER.debug("验证 " + filterInfo.toString() + " 的合法性...");
        ValidationFilterInfo validationFilterInfo = mapper.map(filterInfo, ValidationFilterInfo.class);
        Set<ConstraintViolation<ValidationFilterInfo>> constraintViolations = validator.validate(validationFilterInfo);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(filterInfo + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + filterInfo.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void triggerInfoValidation(TriggerInfo triggerInfo) throws ValidationException {
        if (Objects.isNull(triggerInfo)) {
            return;
        }

        LOGGER.debug("验证 " + triggerInfo.toString() + " 的合法性...");
        ValidationTriggerInfo validationTriggerInfo = mapper.map(triggerInfo, ValidationTriggerInfo.class);
        Set<ConstraintViolation<ValidationTriggerInfo>> constraintViolations = validator.validate(validationTriggerInfo);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(triggerInfo + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + triggerInfo.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void filteredValueValidation(FilteredValue filteredValue) throws ValidationException {
        if (Objects.isNull(filteredValue)) {
            return;
        }

        LOGGER.debug("验证 " + filteredValue.toString() + " 的合法性...");
        ValidationFilterInfo validationTriggerInfo = mapper.map(filteredValue, ValidationFilterInfo.class);
        Set<ConstraintViolation<ValidationFilterInfo>> constraintViolations = validator.validate(validationTriggerInfo);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(filteredValue + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + filteredValue.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void persistenceValueValidation(PersistenceValue persistenceValue) throws ValidationException {
        if (Objects.isNull(persistenceValue)) {
            return;
        }

        LOGGER.debug("验证 " + persistenceValue.toString() + " 的合法性...");
        ValidationPersistenceValue validationTriggerInfo = mapper.map(persistenceValue, ValidationPersistenceValue.class);
        Set<ConstraintViolation<ValidationPersistenceValue>> constraintViolations = validator.validate(validationTriggerInfo);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(persistenceValue + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + persistenceValue.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void realtimeValueValidation(RealtimeValue realtimeValue) throws ValidationException {
        if (Objects.isNull(realtimeValue)) {
            return;
        }

        LOGGER.debug("验证 " + realtimeValue.toString() + " 的合法性...");
        ValidationRealtimeValue validationTriggerInfo = mapper.map(realtimeValue, ValidationRealtimeValue.class);
        Set<ConstraintViolation<ValidationRealtimeValue>> constraintViolations = validator.validate(validationTriggerInfo);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(realtimeValue + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + realtimeValue.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void triggeredValueValidation(TriggeredValue triggeredValue) throws ValidationException {
        if (Objects.isNull(triggeredValue)) {
            return;
        }

        LOGGER.debug("验证 " + triggeredValue.toString() + " 的合法性...");
        ValidationTriggeredValue validationTriggerInfo = mapper.map(triggeredValue, ValidationTriggeredValue.class);
        Set<ConstraintViolation<ValidationTriggeredValue>> constraintViolations = validator.validate(validationTriggerInfo);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(triggeredValue + " 不合法，抛出异常...");
            throw new ValidationException("参数非法: " + triggeredValue.toString(), new ConstraintViolationException(constraintViolations));
        }
    }
}
