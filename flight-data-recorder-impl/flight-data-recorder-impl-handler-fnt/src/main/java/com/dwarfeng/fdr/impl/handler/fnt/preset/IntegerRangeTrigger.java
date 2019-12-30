package com.dwarfeng.fdr.impl.handler.fnt.preset;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@Component("integerRangeTrigger")
@Scope("prototype")
public class IntegerRangeTrigger extends AbstractStructuredTrigger {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerRangeTrigger.class);
    @Autowired
    private Validator validator;

    @Override
    public void applyConfig(Object config) throws TriggerException {
        LOGGER.debug("验证配置对象是否合法...");
        if (Objects.isNull(config)) {
            LOGGER.warn("配置对象 config 不能为 null, 将抛出异常...");
            throw new TriggerException("配置对象 config 不能为 null");
        }
        if (!(config instanceof Config)) {
            throw new TriggerException("配置对象应该属于 " + Config.class.getCanonicalName() +
                    ", 而实际上是 " + config.getClass().getCanonicalName());
        }

        Config castedConfig = (Config) config;
        Set<ConstraintViolation<Config>> constraintViolations = validator.validate(castedConfig);
        if (!constraintViolations.isEmpty()) {
            LOGGER.warn("配置对象未能通过验证, 将抛出异常...");
            throw new TriggerException("配置对象未能通过验证...");
        }
        if (castedConfig.getMin() > castedConfig.getMax()) {
            LOGGER.warn("配置对象未能通过验证, 将抛出异常...");
            throw new TriggerException("配置对象未能通过验证...");
        }

        LOGGER.debug("配置对象合法, 应用配置对象...");
        super.applyConfig(config);
    }

    @Override
    public void test(DataInfo dataInfo, Consumer<? super TriggeredValue> consumer) throws TriggerException {
        LOGGER.debug("测试数据点 " + dataInfo.toString() + "...");

        Config castedConfig = (Config) config;

        int intValue = 0;
        try {
            intValue = Integer.parseInt(dataInfo.getValue());
        } catch (NumberFormatException e) {
            LOGGER.warn("测试数据值 " + dataInfo.getValue() + " 转换异常, 数据点 " + pointGuid + " 是否配置了IntegerTrigger过滤器? 将抛出异常...", e);
            throw new TriggerException("测试数据值 " + dataInfo.getValue() + " 转换异常, 数据点 " + pointGuid + " 是否配置了IntegerTrigger过滤器?");
        }

        if ((castedConfig.getCanEqualsMin() && intValue < castedConfig.getMin()) ||
                (!castedConfig.getCanEqualsMin() && intValue <= castedConfig.getMin())) {
            LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 小于(或小于等于等于)最小值, 不进行触发...");
            return;
        }

        if ((castedConfig.getCanEqualsMax() && intValue > castedConfig.getMax()) ||
                (!castedConfig.getCanEqualsMax() && intValue >= castedConfig.getMax())) {
            LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 大于(或大于等于等于)最大值, 不进行触发...");
            return;
        }

        LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 在最大值与最小值之间, 进行触发...");
        TriggeredValue triggeredValue = new TriggeredValue(
                null,
                new GuidKey(pointGuid),
                new GuidKey(triggerGuid),
                dataInfo.getHappenedDate(),
                dataInfo.getValue(),
                String.format("数据值大于(或等于)%d且小于(或等于)%d", castedConfig.getMin(), castedConfig.getMax())
        );
        if (Objects.nonNull(consumer)) {
            consumer.accept(triggeredValue);
        }
    }

    public static final class Config {

        @JSONField(name = "min", ordinal = 1)
        @NotNull
        private Integer min;

        @JSONField(name = "can_equals_min", ordinal = 2)
        @NotNull
        private Boolean canEqualsMin;

        @JSONField(name = "max", ordinal = 3)
        @NotNull
        private Integer max;

        @JSONField(name = "can_equals_max", ordinal = 4)
        @NotNull
        private Boolean canEqualsMax;

        public Config() {
        }

        public Config(@NotNull Integer min, @NotNull Boolean canEqualsMin, @NotNull Integer max, @NotNull Boolean canEqualsMax) {
            this.min = min;
            this.canEqualsMin = canEqualsMin;
            this.max = max;
            this.canEqualsMax = canEqualsMax;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Boolean getCanEqualsMin() {
            return canEqualsMin;
        }

        public void setCanEqualsMin(Boolean canEqualsMin) {
            this.canEqualsMin = canEqualsMin;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Boolean getCanEqualsMax() {
            return canEqualsMax;
        }

        public void setCanEqualsMax(Boolean canEqualsMax) {
            this.canEqualsMax = canEqualsMax;
        }

        @Override
        public String toString() {
            return "IntegerTriggerConfig{" +
                    "min=" + min +
                    ", canEqualsMin=" + canEqualsMin +
                    ", max=" + max +
                    ", canEqualsMax=" + canEqualsMax +
                    '}';
        }
    }

}
