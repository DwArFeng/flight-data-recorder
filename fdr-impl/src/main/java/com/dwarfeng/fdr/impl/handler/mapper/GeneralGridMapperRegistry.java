package com.dwarfeng.fdr.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
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
        stringBuilder.append("元素0: JSON 文本\n");
        stringBuilder.append("" +
                "  {\n" +
                "    \"base_time_stamp\":0,\n" +
                "    \"process_type\":\"current\",\n" +
                "    \"width\":60000\n" +
                "  }\n\n"
        );
        stringBuilder.append("base_time_stamp: 时间戳, 栅格基点。\n");
        stringBuilder.append("process_type: current/maximum_duration, 栅格处理方式。\n");
        {
            stringBuilder.append("  current: 取栅格点当前的值。\n");
            stringBuilder.append("  maximum_duration: 取栅格中持续时间最长的值。\n");
        }
        stringBuilder.append("width: 数值, 栅格宽度。");
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
                MapParam mapParam = JSON.parseObject((String) mapData.getArgs()[0], MapParam.class);
                mapData.setArgs(new Object[]{mapParam.getBaseTimeStamp(), mapParam.getWidth()});
                switch (mapParam.getProcessType()) {
                    case PROCESS_TYPE_CURRENT:
                        return mapCurrent(mapData, mapParam.getBaseTimeStamp(), mapParam.getWidth());
                    case PROCESS_TYPE_MAXIMUM_DURATION:
                        return mapMaximumDuration(mapData, mapParam.getBaseTimeStamp(), mapParam.getWidth());
                    default:
                        throw new MapperException("未知的 process type: " + mapParam.getProcessType());
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

    private static class MapParam {

        @JSONField(name = "base_time_stamp")
        private long baseTimeStamp;

        @JSONField(name = "width")
        private long width;

        @JSONField(name = "process_type")
        private String processType;

        public MapParam() {
        }

        public MapParam(long baseTimeStamp, long width, String processType) {
            this.baseTimeStamp = baseTimeStamp;
            this.width = width;
            this.processType = processType;
        }

        public long getBaseTimeStamp() {
            return baseTimeStamp;
        }

        public void setBaseTimeStamp(long baseTimeStamp) {
            this.baseTimeStamp = baseTimeStamp;
        }

        public long getWidth() {
            return width;
        }

        public void setWidth(long width) {
            this.width = width;
        }

        public String getProcessType() {
            return processType;
        }

        public void setProcessType(String processType) {
            this.processType = processType;
        }

        @Override
        public String toString() {
            return "MapParam{" +
                    "baseTimeStamp=" + baseTimeStamp +
                    ", width=" + width +
                    ", processType='" + processType + '\'' +
                    '}';
        }
    }
}
