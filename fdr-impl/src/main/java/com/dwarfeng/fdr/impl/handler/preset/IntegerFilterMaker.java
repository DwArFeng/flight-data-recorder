package com.dwarfeng.fdr.impl.handler.preset;

import com.dwarfeng.fdr.impl.handler.FilterMaker;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
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
 * Integer过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0.a
 */
@Component
public class IntegerFilterMaker implements FilterMaker {

    public static final String SUPPORT_TYPE = "integer_filter";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        try {
            IntegerFilter filter = ctx.getBean(IntegerFilter.class);
            filter.setPointKey(filterInfo.getPointKey());
            filter.setFilterInfoKey(filterInfo.getKey());
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
        return "整型数过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是整型数，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return "";
    }

    @Component
    @Scope("prototype")
    public static class IntegerFilter implements Filter, Bean {

        private static final long serialVersionUID = -3232275174352383029L;
        private static final Logger LOGGER = LoggerFactory.getLogger(IntegerFilter.class);

        private LongIdKey pointKey;
        private LongIdKey filterInfoKey;

        public IntegerFilter() {
        }

        @Override
        public FilteredValue test(DataInfo dataInfo) throws FilterException {
            try {
                String value = dataInfo.getValue();
                try {
                    Integer.parseInt(value);
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

        @Override
        public String toString() {
            return "IntegerFilter{" +
                    "pointKey=" + pointKey +
                    ", filterInfoKey=" + filterInfoKey +
                    '}';
        }
    }
}
