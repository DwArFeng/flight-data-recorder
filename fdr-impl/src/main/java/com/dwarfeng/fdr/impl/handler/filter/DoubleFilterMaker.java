package com.dwarfeng.fdr.impl.handler.filter;

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
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Double过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
@Component
public class DoubleFilterMaker implements FilterMaker {

    public static final String SUPPORT_TYPE = "double_filter";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        try {
            DoubleFilter filter = ctx.getBean(DoubleFilter.class);
            filter.setPointKey(filterInfo.getPointKey());
            filter.setFilterInfoKey(filterInfo.getKey());
            return filter;
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class DoubleFilter implements Filter, Bean {

        private static final long serialVersionUID = -3161256701795506170L;
        private static final Logger LOGGER = LoggerFactory.getLogger(DoubleFilter.class);

        private LongIdKey pointKey;
        private LongIdKey filterInfoKey;

        public DoubleFilter() {
        }

        @Override
        public FilteredValue test(DataInfo dataInfo) throws FilterException {
            try {
                String value = dataInfo.getValue();
                try {
                    Double.parseDouble(value);
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
            return "DoubleFilter{" +
                    "pointKey=" + pointKey +
                    ", filterInfoKey=" + filterInfoKey +
                    '}';
        }
    }
}
