package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.impl.handler.MapperMaker;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 最大值映射器制造器。
 *
 * @author DwArFeng
 * @since 1.5.3
 */
@Component
public class MaxMapperMaker implements MapperMaker {

    public static final String SUPPORT_TYPE = "max_mapper";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Mapper makeMapper(Object[] args) throws MapperException {
        try {
            return ctx.getBean(MaxMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class MaxMapper implements Mapper {

        @Override
        public List<TimedValue> map(List<TimedValue> timedValues) throws MapperException {
            try {
                if (timedValues.isEmpty()) {
                    return Collections.emptyList();
                }
                int maxIndex = 0;
                double maxValue = Double.MIN_VALUE;
                for (int i = 0; i < timedValues.size(); i++) {
                    TimedValue timedValue = timedValues.get(i);
                    double v = Double.parseDouble(timedValue.getValue());
                    if (v > maxValue) {
                        maxValue = v;
                        maxIndex = i;
                    }
                }
                return Collections.singletonList(timedValues.get(maxIndex));
            } catch (Exception e) {
                throw new MapperException(e);
            }
        }
    }
}
