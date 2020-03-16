package com.dwarfeng.fdr.impl.handler.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.impl.handler.FilterMaker;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 具有范围的 Double过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class RangedDoubleFilterMaker implements FilterMaker {

    public static final String SUPPORT_TYPE = "ranged_double_filter";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        try {
            RangedDoubleFilter filter = ctx.getBean(RangedDoubleFilter.class);
            filter.setPointKey(filterInfo.getPointKey());
            filter.setFilterInfoKey(filterInfo.getKey());
            filter.setConfig(JSON.parseObject(filterInfo.getContent(), Config.class));
            return filter;
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "具有范围的双精度浮点过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是双精度浮点数且数值在配置的范围之内，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return JSON.toJSONString(new Config(
                0.5,
                true,
                -1.25,
                false
        ), true);
    }

    @Component
    @Scope("prototype")
    public static class RangedDoubleFilter implements Filter, Bean {

        private static final long serialVersionUID = -7217865313990907457L;
        private static final Logger LOGGER = LoggerFactory.getLogger(RangedDoubleFilter.class);

        private LongIdKey pointKey;
        private LongIdKey filterInfoKey;
        private Config config;

        public RangedDoubleFilter() {
        }

        @Override
        public FilteredValue test(DataInfo dataInfo) throws FilterException {
            try {
                String value = dataInfo.getValue();
                double doubleValue;
                try {
                    doubleValue = Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 不是数字或超过双精度浮点数范围, 不能通过过滤...", e);
                    return new FilteredValue(
                            null,
                            pointKey,
                            filterInfoKey,
                            dataInfo.getHappenedDate(),
                            dataInfo.getValue(),
                            "数据值不是数字或超过双精度浮点数范围"
                    );
                }

                if ((config.getCanEqualsMin() && doubleValue < config.getMin()) ||
                        (!config.getCanEqualsMin() && doubleValue <= config.getMin())) {
                    LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 小于(或小于等于等于)最小值, 不能通过过滤...");
                    return new FilteredValue(
                            null,
                            pointKey,
                            filterInfoKey,
                            dataInfo.getHappenedDate(),
                            dataInfo.getValue(),
                            "数据值小于(或小于等于等于)最小值"
                    );
                }

                if ((config.getCanEqualsMax() && doubleValue > config.getMax()) ||
                        (!config.getCanEqualsMax() && doubleValue >= config.getMax())) {
                    LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 大于(或大于等于等于)最大值, 不能通过过滤...");
                    return new FilteredValue(
                            null,
                            pointKey,
                            filterInfoKey,
                            dataInfo.getHappenedDate(),
                            dataInfo.getValue(),
                            "数据值大于(或大于等于等于)最大值"
                    );
                }

                LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 通过过滤器...");
                return null;
            } catch (Exception e) {
                throw new FilterException(e);
            }
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
            this.pointKey = pointKey;
        }

        public LongIdKey getFilterInfoKey() {
            return filterInfoKey;
        }

        public void setFilterInfoKey(LongIdKey filterInfoKey) {
            this.filterInfoKey = filterInfoKey;
        }

        public Config getConfig() {
            return config;
        }

        public void setConfig(Config config) {
            this.config = config;
        }

        @Override
        public String toString() {
            return "RangedDoubleFilter{" +
                    "pointKey=" + pointKey +
                    ", filterInfoKey=" + filterInfoKey +
                    ", config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 7448644831542247410L;

        @JSONField(name = "min")
        private Double min;

        @JSONField(name = "can_equals_min")
        private Boolean canEqualsMin;

        @JSONField(name = "max")
        private Double max;

        @JSONField(name = "can_equals_max")
        private Boolean canEqualsMax;

        public Config() {
        }

        public Config(Double min, Boolean canEqualsMin, Double max, Boolean canEqualsMax) {
            this.min = min;
            this.canEqualsMin = canEqualsMin;
            this.max = max;
            this.canEqualsMax = canEqualsMax;
        }

        public Double getMin() {
            return min;
        }

        public void setMin(Double min) {
            this.min = min;
        }

        public Boolean getCanEqualsMin() {
            return canEqualsMin;
        }

        public void setCanEqualsMin(Boolean canEqualsMin) {
            this.canEqualsMin = canEqualsMin;
        }

        public Double getMax() {
            return max;
        }

        public void setMax(Double max) {
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
            return "Config{" +
                    "min=" + min +
                    ", canEqualsMin=" + canEqualsMin +
                    ", max=" + max +
                    ", canEqualsMax=" + canEqualsMax +
                    '}';
        }
    }
}
