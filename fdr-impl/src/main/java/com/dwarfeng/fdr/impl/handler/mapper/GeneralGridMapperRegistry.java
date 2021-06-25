package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.impl.handler.mapper.GridUtil.Grid;
import com.dwarfeng.fdr.impl.handler.mapper.GridUtil.GridData;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 通用栅格映射器注册。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
@Component
public class GeneralGridMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "general_grid_mapper";

    public static final String PROCESS_TYPE_CURRENT = "current";
    public static final String PROCESS_TYPE_MAXIMUM_DURATION = "maximum_duration";

    @Autowired
    private ApplicationContext ctx;

    public GeneralGridMapperRegistry() {
        super(MAPPER_TYPE);
    }

    @Override
    public String provideLabel() {
        return "通用栅格映射器";
    }

    @Override
    public String provideDescription() {
        return "将疏密不一致的数据通过指定的处理方式对齐到时间栅格。";
    }

    @Override
    public String provideArgsIllustrate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("元素0: long, 当指定时间段内没有数据的时候是否使用前刻数据代替。\n");
        stringBuilder.append("元素1: long, 栅格宽度。\n");
        stringBuilder.append("元素2: String enumeration in (current, maximum_duration), 栅格处理方式。\n");
        {
            stringBuilder.append("  current: 取栅格点当前的值。\n");
            stringBuilder.append("  maximum_duration: 取栅格中持续时间最长的值。");
        }
        return stringBuilder.toString();
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(GeneralGridMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GeneralGridMapperRegistry{" +
                "ctx=" + ctx +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GeneralGridMapper implements Mapper {

        @Override
        public List<TimedValue> map(MapData mapData) throws MapperException {
            try {
                long baseTimeStamp = (long) mapData.getArgs()[0];
                long width = (long) mapData.getArgs()[1];
                String processType = (String) mapData.getArgs()[2];

                switch (processType) {
                    case PROCESS_TYPE_CURRENT:
                        return mapCurrent(mapData, baseTimeStamp, width);
                    case PROCESS_TYPE_MAXIMUM_DURATION:
                        return mapMaximumDuration(mapData, baseTimeStamp, width);
                    default:
                        throw new MapperException("未知的 process type: " + processType);
                }
            } catch (MapperException e) {
                throw e;
            } catch (Exception e) {
                throw new MapperException(e);
            }
        }

        private List<TimedValue> mapCurrent(MapData mapData, long baseTimeStamp, long width) {
            List<TimedValue> timedValueList = new ArrayList<>();
            Grid[] grids = GridUtil.rasterizedData(mapData, baseTimeStamp, width);
            for (Grid grid : grids) {
                timedValueList.add(
                        new TimedValue(grid.getGridDatum()[0].getValue(), new Date(grid.getBaseTimeStamp()))
                );
            }
            return timedValueList;
        }

        private List<TimedValue> mapMaximumDuration(MapData mapData, long baseTimeStamp, long width) {
            List<TimedValue> timedValueList = new ArrayList<>();
            Grid[] grids = GridUtil.rasterizedData(mapData, baseTimeStamp, width);
            for (Grid grid : grids) {
                String value = null;
                long duration = 0;
                for (GridData gridData : grid.getGridDatum()) {
                    if (gridData.getDuration() > duration) {
                        value = gridData.getValue();
                        duration = gridData.getDuration();
                    }
                }
                timedValueList.add(new TimedValue(value, new Date(grid.getBaseTimeStamp())));
            }
            return timedValueList;
        }
    }
}
