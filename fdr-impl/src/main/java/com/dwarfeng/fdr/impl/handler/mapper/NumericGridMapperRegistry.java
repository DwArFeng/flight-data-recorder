package com.dwarfeng.fdr.impl.handler.mapper;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数字栅格映射器注册。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
@Component
public class NumericGridMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "numeric_grid_mapper";

    public static final String PROCESS_TYPE_MAXIMUM = "maximum";
    public static final String PROCESS_TYPE_MINIMUM = "minimum";
    public static final String PROCESS_TYPE_AVERAGE = "average";
    public static final String PROCESS_TYPE_WEIGHTED_AVERAGE = "weighted_average";

    @Autowired
    private ApplicationContext ctx;

    public NumericGridMapperRegistry() {
        super(MAPPER_TYPE);
    }

    @Override
    public String provideLabel() {
        return "数字栅格映射器";
    }

    @Override
    public String provideDescription() {
        return "将疏密不一致的数据通过指定的处理方式对齐到时间栅格，要求待处理数据均必须能被解析为double。";
    }

    @Override
    public String provideArgsIllustrate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("元素0: long, 当指定时间段内没有数据的时候是否使用前刻数据代替。\n");
        stringBuilder.append("元素1: long, 栅格宽度。\n");
        stringBuilder.append("元素2: String, 浮点数格式, 如 \"0.00\"(保留两位小数)。\n");
        stringBuilder.append("元素3: String enumeration in (maximum, minimum, average, weighted_average), 栅格处理方式。\n");
        {
            stringBuilder.append("  maximum: 取栅格中的最大值。\n");
            stringBuilder.append("  minimum: 取栅格中的最小值。\n");
            stringBuilder.append("  average: 取栅格中的平均值。\n");
            stringBuilder.append("  weighted_average: 取栅格中的加权平均值。\n");
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
                DecimalFormat decimalFormat = new DecimalFormat((String) mapData.getArgs()[2]);
                String processType = (String) mapData.getArgs()[3];

                switch (processType) {
                    case PROCESS_TYPE_MAXIMUM:
                        return mapMaximum(mapData, baseTimeStamp, width, decimalFormat);
                    case PROCESS_TYPE_MINIMUM:
                        return mapMinimum(mapData, baseTimeStamp, width, decimalFormat);
                    case PROCESS_TYPE_AVERAGE:
                        return mapAverage(mapData, baseTimeStamp, width, decimalFormat);
                    case PROCESS_TYPE_WEIGHTED_AVERAGE:
                        return mapWeightedAverage(
                                mapData, baseTimeStamp, width, decimalFormat);
                    default:
                        throw new MapperException("未知的 process type: " + processType);
                }
            } catch (MapperException e) {
                throw e;
            } catch (Exception e) {
                throw new MapperException(e);
            }
        }

        private List<TimedValue> mapMaximum(MapData mapData, long baseTimeStamp, long width, DecimalFormat decimalFormat) {
            List<TimedValue> timedValueList = new ArrayList<>();
            Grid[] grids = GridUtil.rasterizedData(mapData, baseTimeStamp, width);
            for (Grid grid : grids) {
                double value = -Double.MAX_VALUE;
                for (GridData gridData : grid.getGridDatum()) {
                    double gridDataValue = Double.parseDouble(gridData.getValue());
                    if (gridDataValue > value) {
                        value = gridDataValue;
                    }
                }
                timedValueList.add(new TimedValue(decimalFormat.format(value), new Date(grid.getBaseTimeStamp())));
            }
            return timedValueList;
        }

        private List<TimedValue> mapMinimum(MapData mapData, long baseTimeStamp, long width, DecimalFormat decimalFormat) {
            List<TimedValue> timedValueList = new ArrayList<>();
            Grid[] grids = GridUtil.rasterizedData(mapData, baseTimeStamp, width);
            for (Grid grid : grids) {
                double value = Double.MAX_VALUE;
                for (GridData gridData : grid.getGridDatum()) {
                    double gridDataValue = Double.parseDouble(gridData.getValue());
                    if (gridDataValue < value) {
                        value = gridDataValue;
                    }
                }
                timedValueList.add(new TimedValue(decimalFormat.format(value), new Date(grid.getBaseTimeStamp())));
            }
            return timedValueList;
        }

        private List<TimedValue> mapAverage(MapData mapData, long baseTimeStamp, long width, DecimalFormat decimalFormat) {
            List<TimedValue> timedValueList = new ArrayList<>();
            Grid[] grids = GridUtil.rasterizedData(mapData, baseTimeStamp, width);
            for (Grid grid : grids) {
                double total = 0;
                int count = 0;
                for (GridData gridData : grid.getGridDatum()) {
                    total += Double.parseDouble(gridData.getValue());
                    count += 1;
                }
                timedValueList.add(new TimedValue(
                        decimalFormat.format(total / count), new Date(grid.getBaseTimeStamp())
                ));
            }
            return timedValueList;
        }

        private List<TimedValue> mapWeightedAverage(
                MapData mapData, long baseTimeStamp, long width, DecimalFormat decimalFormat) {
            List<TimedValue> timedValueList = new ArrayList<>();
            Grid[] grids = GridUtil.rasterizedData(mapData, baseTimeStamp, width);
            for (Grid grid : grids) {
                double square = 0;
                long duration = 0;
                for (GridData gridData : grid.getGridDatum()) {
                    long gridDataDuration = Math.max(1, gridData.getDuration());
                    square += Double.parseDouble(gridData.getValue()) * gridDataDuration;
                    duration += gridDataDuration;
                }
                timedValueList.add(new TimedValue(
                        decimalFormat.format(square / duration), new Date(grid.getBaseTimeStamp())
                ));
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

        @JSONField(name = "decimal_format")
        private String decimalFormat;

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

        public String getDecimalFormat() {
            return decimalFormat;
        }

        public void setDecimalFormat(String decimalFormat) {
            this.decimalFormat = decimalFormat;
        }

        @Override
        public String toString() {
            return "MapParam{" +
                    "baseTimeStamp=" + baseTimeStamp +
                    ", width=" + width +
                    ", processType='" + processType + '\'' +
                    ", decimalFormat='" + decimalFormat + '\'' +
                    '}';
        }
    }
}
