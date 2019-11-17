package com.dwarfeng.fdr.impl.handler.validation.handler;

import com.dwarfeng.fdr.impl.handler.validation.bean.dto.ValidationLookupPagingInfo;
import com.dwarfeng.fdr.impl.handler.validation.bean.entity.ValidationCategory;
import com.dwarfeng.fdr.impl.handler.validation.bean.key.ValidationUuidKey;
import com.dwarfeng.fdr.stack.bean.dto.LookupPagingInfo;
import com.dwarfeng.fdr.stack.bean.entity.Category;
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
import javax.validation.constraints.NotNull;
import java.util.Set;

@Component
@Validated
public class ValidationHandlerDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationHandlerDelegate.class);

    @Autowired
    private Validator validator;
    @Autowired
    private Mapper mapper;

    public void uuidKeyValidation(@NotNull UuidKey uuidKey) throws ValidationException {
        LOGGER.debug("验证 " + uuidKey.toString() + " 的合法性...");
        ValidationUuidKey validationUuidKey = mapper.map(uuidKey, ValidationUuidKey.class);
        Set<ConstraintViolation<ValidationUuidKey>> constraintViolations = validator.validate(validationUuidKey);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(uuidKey + " 不合法，抛出异常...");
            throw new ValidationException("入口参数非法: " + uuidKey.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void categoryValidation(@NotNull Category category) throws ValidationException {
        LOGGER.debug("验证 " + category.toString() + " 的合法性...");
        ValidationCategory validationCategory = mapper.map(category, ValidationCategory.class);
        Set<ConstraintViolation<ValidationCategory>> constraintViolations = validator.validate(validationCategory);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(category + " 不合法，抛出异常...");
            throw new ValidationException("入口参数非法: " + category.toString(), new ConstraintViolationException(constraintViolations));
        }
    }

    public void lookupPagingInfoValidation(@NotNull LookupPagingInfo lookupPagingInfo) throws ValidationException {
        LOGGER.debug("验证 " + lookupPagingInfo.toString() + " 的合法性...");
        ValidationLookupPagingInfo validationLookupPagingInfo = mapper.map(lookupPagingInfo, ValidationLookupPagingInfo.class);
        Set<ConstraintViolation<ValidationLookupPagingInfo>> constraintViolations = validator.validate(validationLookupPagingInfo);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn(lookupPagingInfo + " 不合法，抛出异常...");
            throw new ValidationException("入口参数非法: " + lookupPagingInfo.toString(), new ConstraintViolationException(constraintViolations));
        }
    }
}
