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
 * 具有范围的 Integer过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class RangedIntegerFilterMaker implements FilterMaker {

    public static final String SUPPORT_TYPE = "ranged_integer_filter";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        try {
            RangedIntegerFilter filter = ctx.getBean(RangedIntegerFilter.class);
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
        return "具有范围的整型过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是整型数且数值在配置的范围之内，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return JSON.toJSONString(new Config(
                1,
                true,
                -2,
                false
        ), true);
    }

    @Component
    @Scope("prototype")
    public static class RangedIntegerFilter implements Filter, Bean {

        private static final long serialVersionUID = -3232275174352383029L;
        private static final Logger LOGGER = LoggerFactory.getLogger(RangedIntegerFilter.class);

        private LongIdKey pointKey;
        private LongIdKey filterInfoKey;
        private Config config;

        public RangedIntegerFilter() {
        }

        @Override
        public FilteredValue test(DataInfo dataInfo) throws FilterException {
            try {
                String value = dataInfo.getValue();
                int intValue;
                try {
                    intValue = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 不是数字或超过整型数范围, 不能通过过滤...", e);
                    return new FilteredValue(
                            null,
                            pointKey,
                            filterInfoKey,
                            dataInfo.getHappenedDate(),
                            dataInfo.getValue(),
                            "数据值不是数字或超过整型数范围"
                    );
                }

                if ((config.getCanEqualsMin() && intValue < config.getMin()) ||
                        (!config.getCanEqualsMin() && intValue <= config.getMin())) {
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

                if ((config.getCanEqualsMax() && intValue > config.getMax()) ||
                        (!config.getCanEqualsMax() && intValue >= config.getMax())) {
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
            return "RangedIntegerFilter{" +
                    "pointKey=" + pointKey +
                    ", filterInfoKey=" + filterInfoKey +
                    ", config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 3412462303040292737L;

        @JSONField(name = "min")
        private Integer min;

        @JSONField(name = "can_equals_min")
        private Boolean canEqualsMin;

        @JSONField(name = "max")
        private Integer max;

        @JSONField(name = "can_equals_max")
        private Boolean canEqualsMax;

        public Config() {
        }

        public Config(Integer min, Boolean canEqualsMin, Integer max, Boolean canEqualsMax) {
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
            return "Config{" +
                    "min=" + min +
                    ", canEqualsMin=" + canEqualsMin +
                    ", max=" + max +
                    ", canEqualsMax=" + canEqualsMax +
                    '}';
        }
    }
}
