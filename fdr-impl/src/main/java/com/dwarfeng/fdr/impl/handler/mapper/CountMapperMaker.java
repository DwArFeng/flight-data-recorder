package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.fdr.impl.handler.MapperMaker;
import com.dwarfeng.fdr.stack.bean.dto.MappedValue;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 计数映射器。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
@Component
public class CountMapperMaker implements MapperMaker {

    public static final String SUPPORT_TYPE = "count";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }


    @Override
    public Mapper makeMapper(Object[] args) throws MapperException {
        try {
            return ctx.getBean(CountMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Component
    @Scope("prototype")
    public static class CountMapper implements Mapper {

        @Override
        public boolean supportPersistenceValue() {
            return true;
        }

        @Override
        public boolean supportFilteredValue() {
            return true;
        }

        @Override
        public boolean supportTriggeredValue() {
            return true;
        }

        @Override
        public List<MappedValue> mapPersistenceValue(List<PersistenceValue> persistenceValues) {
            return Collections.singletonList(new MappedValue(new Date(), Integer.toString(persistenceValues.size())));
        }

        @Override
        public List<MappedValue> mapFilteredValue(List<FilteredValue> filteredValues) {
            return Collections.singletonList(new MappedValue(new Date(), Integer.toString(filteredValues.size())));
        }

        @Override
        public List<MappedValue> mapTriggeredValue(List<TriggeredValue> triggeredValues) {
            return Collections.singletonList(new MappedValue(new Date(), Integer.toString(triggeredValues.size())));
        }
    }
}
