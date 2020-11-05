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

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 计数映射器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class CountMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "count_mapper";

    @Autowired
    private ApplicationContext ctx;

    public CountMapperRegistry() {
        super(MAPPER_TYPE);
    }

    @Override
    public String provideLabel() {
        return "计数映射器";
    }

    @Override
    public String provideDescription() {
        return "将输入的数据统计个数并输出，时间为统计行为发生的时间。";
    }

    @Override
    public String provideArgsIllustrate() {
        return "不需要任何参数";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(CountMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "CountMapperRegistry{" +
                "ctx=" + ctx +
                ", mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class CountMapper implements Mapper {

        @Override
        public List<TimedValue> map(MapData mapData) {
            return Collections.singletonList(
                    new TimedValue(Integer.toString(mapData.getTimedValues().size()), new Date()));
        }
    }
}
