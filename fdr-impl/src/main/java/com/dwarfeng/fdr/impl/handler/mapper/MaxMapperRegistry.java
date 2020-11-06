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
import java.util.List;

/**
 * 最大值映射器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class MaxMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "max_mapper";

    @Autowired
    private ApplicationContext ctx;

    public MaxMapperRegistry() {
        super(MAPPER_TYPE);
    }

    @Override
    public String provideLabel() {
        return "最大值映射器";
    }

    @Override
    public String provideDescription() {
        return "统计输入数据中的最大数据并输出，要求待处理数据均必须能被解析为double。";
    }

    @Override
    public String provideArgsIllustrate() {
        return "元素0: true/false, 当指定时间段内没有数据的时候是否使用前刻数据代替。";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(MaxMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "MaxMapperRegistry{" +
                "ctx=" + ctx +
                ", mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class MaxMapper implements Mapper {

        @SuppressWarnings("DuplicatedCode")
        @Override
        public List<TimedValue> map(MapData mapData) throws MapperException {
            try {
                // 提取必要参数。
                List<TimedValue> timedValues = mapData.getTimedValues();
                TimedValue previous = mapData.getPrevious();
                boolean considerPrevious = (boolean) mapData.getArgs()[0];

                // 当时间段内数据不存在时，根据参数确定返回结果。
                if (timedValues.isEmpty()) {
                    if (considerPrevious) {
                        return Collections.singletonList(previous);
                    } else {
                        return Collections.emptyList();
                    }
                }

                // 最大值判断。
                int maxIndex = 0;
                double maxValue = -Double.MAX_VALUE;
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
