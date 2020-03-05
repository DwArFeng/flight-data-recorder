package com.dwarfeng.fdr.impl.handler.preset;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.handler.FilterMaker;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterSupport;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.service.FilterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 正则表达式过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0.a
 */
@Component
public class RegexFilterMaker implements FilterMaker {

    public static final String SUPPORT_TYPE = "regex_filter";
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterMaker.class);

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private FilterSupportMaintainService service;

    @PostConstruct
    public void init() {
        try {
            String label = "正则表达式过滤器";
            String description = "如果数据值匹配指定的正则表达式，则通过过滤。";
            String exampleContent = JSON.toJSONString(new Config("^\\d+$"), true);
            service.insertIfNotExists(
                    new FilterSupport(
                            new StringIdKey(SUPPORT_TYPE),
                            label,
                            description,
                            exampleContent
                    )
            );
        } catch (Exception e) {
            LOGGER.warn("未能向 FilterSupportMaintainService 中确认或添加过滤器信息", e);
        }
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        try {
            RegexFilter filter = ctx.getBean(RegexFilter.class);
            filter.setPointKey(filterInfo.getPointKey());
            filter.setFilterInfoKey(filterInfo.getKey());
            filter.setConfig(JSON.parseObject(filterInfo.getContent(), Config.class));
            return filter;
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Component
    @Scope("prototype")
    public static class RegexFilter implements Filter, Bean {

        private static final long serialVersionUID = 970186550656361331L;
        private static final Logger LOGGER = LoggerFactory.getLogger(RegexFilter.class);

        private LongIdKey pointKey;
        private LongIdKey filterInfoKey;
        private Config config;

        public RegexFilter() {
        }

        @Override
        public void test(DataInfo dataInfo, Consumer<? super FilteredValue> consumer) throws FilterException {
            try {
                String value = dataInfo.getValue();
                if (!value.matches(config.getPattern())) {
                    LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 不能匹配正则表达式 " + config.getPattern()
                            + ", 不能通过过滤...");
                    FilteredValue filteredValue = new FilteredValue(
                            null,
                            pointKey,
                            filterInfoKey,
                            dataInfo.getHappenedDate(),
                            dataInfo.getValue(),
                            "数据值不是数字"
                    );
                    if (Objects.nonNull(consumer)) {
                        consumer.accept(filteredValue);
                    }
                    return;
                }
                LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 通过过滤器...");
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
            return "RegexFilter{" +
                    "pointKey=" + pointKey +
                    ", filterInfoKey=" + filterInfoKey +
                    ", config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -4947434410530771676L;

        @JSONField(name = "pattern")
        private String pattern;

        public Config() {
        }

        public Config(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "pattern='" + pattern + '\'' +
                    '}';
        }
    }
}
