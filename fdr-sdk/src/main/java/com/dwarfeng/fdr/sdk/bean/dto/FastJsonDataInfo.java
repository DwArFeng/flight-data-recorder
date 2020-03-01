package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 适用于FastJson的DataInfo。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonDataInfo implements Dto {

    private static final long serialVersionUID = -195639022270422852L;

    /**
     * 通过指定的 DataInfo 生成对应的 FastJsonDataInfo。
     *
     * @param dataInfo 指定的 DataInfo.
     * @return 通过指定的 DataInfo 生成对应的 FastJsonDataInfo。
     */
    public static FastJsonDataInfo of(DataInfo dataInfo) {
        if (Objects.isNull(dataInfo)) {
            return null;
        }
        return new FastJsonDataInfo(
                dataInfo.getPointLongId(),
                dataInfo.getValue(),
                dataInfo.getHappenedDate()
        );
    }

    @JSONField(name = "point_long_id", ordinal = 1)
    private long pointLongId;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public FastJsonDataInfo() {
    }

    public FastJsonDataInfo(long pointLongId, String value, Date happenedDate) {
        this.pointLongId = pointLongId;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public long getPointLongId() {
        return pointLongId;
    }

    public void setPointLongId(long pointLongId) {
        this.pointLongId = pointLongId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "FastJsonDataInfo{" +
                "pointLongId=" + pointLongId +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
