package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.handler.Mapper.MapData;

import java.util.*;

/**
 * 栅格工具类。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
final class GridUtil {

    /**
     * 栅格化数据。
     *
     * @param mapData       指定的映射数据。
     * @param baseTimeStamp 栅格基点。
     * @param width         栅格宽度。
     * @return 栅格，当数据无法建立时返回空数组。
     */
    public static Grid[] rasterizedData(MapData mapData, long baseTimeStamp, long width) {
        // 取出必要数据。
        List<TimedValue> timedValues = mapData.getTimedValues();
        TimedValue previous = mapData.getPrevious();
        long startTimeStamp = mapData.getStartDate().getTime();
        long endTimeStamp = mapData.getEndDate().getTime();

        // 判断特殊情况。
        if (!valid(previous, startTimeStamp, endTimeStamp, baseTimeStamp, width)) {
            return new Grid[0];
        }

        // 寻找起始栅格基点。
        baseTimeStamp = seekBegin(startTimeStamp, baseTimeStamp, width);
        // 处理 timedValues，添加必要数据，保证其数量大于 2。
        timedValues = new ArrayList<>(timedValues);
        if (Objects.nonNull(previous)) {
            timedValues.add(0, previous);
        }
        timedValues.add(new TimedValue(null, new Date(Long.MAX_VALUE)));
        // 如果处理后的 timedValues 数量不大于2，则直接返回空值。
        if (timedValues.size() < 2) {
            return new Grid[0];
        }

        // 遍历并栅格化。
        List<Grid> gridList = new ArrayList<>();
        int valueIndex = 0;
        // 虽然在结构上是双重循环，但是内层的循环是线性的，因此整个方法的时间复杂度是线性的。
        do {
            String value;
            long valueStartTimeStamp;
            long valueEndTimeStamp;
            List<GridData> gridDataList = new ArrayList<>();
            do {
                // 两个一组错位取出 timedValues，取 happenedDate 分别作为起始时间和结束时间。
                // 由于之前的方法已经妥善的处理数据，在循环中不会出现数组越界的问题。
                value = timedValues.get(valueIndex).getValue();
                valueStartTimeStamp = timedValues.get(valueIndex).getHappenedDate().getTime();
                valueEndTimeStamp = timedValues.get(valueIndex + 1).getHappenedDate().getTime();
                // 如果 valueEndTimeStamp 小于 baseTimeStamp，则代表整个区间落在栅格前方，直接忽略。
                if (valueEndTimeStamp < baseTimeStamp) {
                    valueIndex += 1;
                    continue;
                }
                // 处理数据，使开始时间和结束时间落于栅格之内。
                long calculatedValueStartTimeStamp = Math.max(valueStartTimeStamp, baseTimeStamp);
                long calculatedValueEndTimeStamp = Math.min(valueEndTimeStamp, baseTimeStamp + width);
                gridDataList.add(new GridData(
                        calculatedValueStartTimeStamp,
                        calculatedValueEndTimeStamp - calculatedValueStartTimeStamp,
                        value
                ));
                // 如果数据组结束时间跨栅格，需要判断两次，前栅格一次，后栅格一次。
                // 数组跨栅格 valueIndex 不增加，本循环退出，进入下一个栅格循环。
                // 进入下一个栅格循环后紧接着再次进步本循环，实现跨栅格两次至多次调用。
                if (valueEndTimeStamp < baseTimeStamp + width) {
                    valueIndex += 1;
                }
            }
            //如果结束时间在栅格之内，则一直取下两个 timedValues。
            while (valueEndTimeStamp < baseTimeStamp + width);
            gridList.add(new Grid(baseTimeStamp, width, gridDataList.toArray(new GridData[0])));
            baseTimeStamp += width;
        } while (baseTimeStamp < endTimeStamp);
        return gridList.toArray(new Grid[0]);
    }

    @SuppressWarnings("RedundantIfStatement") // 为了可读性保留代码格式，不做简化。
    private static boolean valid(TimedValue previous, long startTimeStamp, long endTimeStamp, long baseTimeStamp, long width) {
        // 如果 width 不是正数，不合法。
        if (width <= 0) {
            return false;
        }
        // 如果结束时间减去开始时间小于栅格宽度，不合法。
        if ((endTimeStamp - startTimeStamp) < width) {
            return false;
        }
        // 如果 startTimeStamp 不落在栅格点上且 previous 是 null，不合法。
        if ((Math.abs(startTimeStamp - baseTimeStamp) % width) != 0 && Objects.isNull(previous)) {
            return false;
        }
        // 其余情形合法。
        return true;
    }

    private static long seekBegin(long startTimeStamp, long baseTimeStamp, long width) {
        // 如果 baseTimeStamp 落在 startTimeStamp 一个宽度之内，则直接返回本身。
        if (baseTimeStamp > startTimeStamp && (baseTimeStamp - startTimeStamp) < width) return baseTimeStamp;
        // 计算偏移的 delta 偏差。
        long timeDelta = startTimeStamp - baseTimeStamp;
        long gridDelta = timeDelta / width;
        // 如果 timeDelta 不能被 width 除尽，则 startTimeStamp 不在栅格上，需要修正。
        // 修正分正负两种情况，由于向下取整，所以负数不需要修正。
        if ((timeDelta % width) != 0 && gridDelta > 0) {
            gridDelta += 1;
        }
        // 返回栅格。
        return baseTimeStamp + width * gridDelta;
    }

    /**
     * 栅格。
     *
     * @author DwArFeng
     * @since 1.9.0
     */
    static class Grid {

        /**
         * 栅格基点时间戳。
         */
        private long baseTimeStamp;
        /**
         * 栅格宽度。
         */
        private long width;
        /**
         * 栅格内的数据组。
         */
        private GridData[] gridDatum;

        public Grid() {
        }

        public Grid(long baseTimeStamp, long width, GridData[] gridDatum) {
            this.baseTimeStamp = baseTimeStamp;
            this.width = width;
            this.gridDatum = gridDatum;
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

        public GridData[] getGridDatum() {
            return gridDatum;
        }

        public void setGridDatum(GridData[] gridDatum) {
            this.gridDatum = gridDatum;
        }

        @Override
        public String toString() {
            return "Grid{" +
                    "baseTimeStamp=" + baseTimeStamp +
                    ", width=" + width +
                    ", gridDatum=" + Arrays.toString(gridDatum) +
                    '}';
        }
    }

    /**
     * 栅格数据。
     *
     * @author DwArFeng
     * @since 1.9.0
     */
    static class GridData {

        /**
         * 数据发生时的时间戳。
         */
        private long happenedTimeStamp;
        /**
         * 数据的持续时间。
         */
        private long duration;
        /**
         * 数据值。
         */
        private String value;

        public GridData() {
        }

        public GridData(long happenedTimeStamp, long duration, String value) {
            this.happenedTimeStamp = happenedTimeStamp;
            this.duration = duration;
            this.value = value;
        }

        public long getHappenedTimeStamp() {
            return happenedTimeStamp;
        }

        public void setHappenedTimeStamp(long happenedTimeStamp) {
            this.happenedTimeStamp = happenedTimeStamp;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "GridData{" +
                    "happenedTimeStamp=" + happenedTimeStamp +
                    ", duration=" + duration +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    private GridUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
