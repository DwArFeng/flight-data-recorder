package com.dwarfeng.fdr.sdk.validation;

import com.dwarfeng.fdr.sdk.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 参数需要为指定的类或其子类的验证器注解。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Documented
@Constraint(
        validatedBy = {DenseUUID.SpecificClassConstraintValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DenseUUID {

    String message() default "{com.dwarfeng.fdr.sdk.validation.DenseUUID.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class SpecificClassConstraintValidator implements ConstraintValidator<DenseUUID, Object> {

        private static final Logger LOGGER = LoggerFactory.getLogger(SpecificClassConstraintValidator.class);

        @Override
        public void initialize(DenseUUID constraintAnnotation) {
            LOGGER.debug("初始化...");
        }

        // 执行校验操作
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            LOGGER.debug("开始验证...");
            try {
                if (!(value instanceof String)) {
                    LOGGER.debug("对象 " + value.toString() + " 不属于 String, 验证不通过...");
                    return false;
                }
                if (((String) value).length() != Constants.DENSE_UUID_LENGTH) {
                    LOGGER.debug("文本 " + value + " 的长度不是紧缩UUID标准长度, 验证不通过...");
                    return false;
                }
                if (!((String) value).matches(Constants.DENSE_UUID_REGEX)) {
                    LOGGER.debug("文本 " + value + " 不满足紧缩UUID的格式, 验证不通过...");
                    return false;
                } else {
                    LOGGER.debug("文本 " + value + " 验证通过...");
                    return true;
                }
            } catch (Exception e) {
                LOGGER.warn("验证过程中发生了意料之外的异常", e);
                return false;
            }
        }
    }
}
