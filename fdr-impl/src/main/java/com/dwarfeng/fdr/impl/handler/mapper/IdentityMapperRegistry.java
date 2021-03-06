package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 原值映射器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class IdentityMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "identity_mapper";

    @Autowired
    private ApplicationContext ctx;

    public IdentityMapperRegistry() {
        super(MAPPER_TYPE);
    }

    @Override
    public String provideLabel() {
        return "原值映射器";
    }

    @Override
    public String provideDescription() {
        return "将输入的值原封不动的输出。";
    }

    @Override
    public String provideArgsIllustrate() {
        return "不需要任何参数";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(IdentityMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "IdentityMapperRegistry{" +
                "ctx=" + ctx +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class IdentityMapper implements Mapper {

        @Override
        public List<TimedValue> map(MapData mapData) {
            return mapData.getTimedValues();
        }
    }
}
