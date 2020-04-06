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
 * 最小值映射器。
 *
 * @author DwArFeng
 * @since 1.5.3
 */
@Component
public class MinMapperMaker implements MapperMaker {

    public static final String SUPPORT_TYPE = "min_mapper";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Mapper makeMapper(Object[] args) throws MapperException {
        try {
            return ctx.getBean(MinMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class MinMapper implements Mapper {

        @Override
        public List<TimedValue> map(List<TimedValue> timedValues) throws MapperException {
            try {
                if (timedValues.isEmpty()) {
                    return Collections.emptyList();
                }
                int minIndex = 0;
                double minValue = Double.MAX_VALUE;
                for (int i = 0; i < timedValues.size(); i++) {
                    TimedValue timedValue = timedValues.get(i);
                    double v = Double.parseDouble(timedValue.getValue());
                    if (v < minValue) {
                        minValue = v;
                        minIndex = i;
                    }
                }
                return Collections.singletonList(timedValues.get(minIndex));
            } catch (Exception e) {
                throw new MapperException(e);
            }
        }
    }
}
