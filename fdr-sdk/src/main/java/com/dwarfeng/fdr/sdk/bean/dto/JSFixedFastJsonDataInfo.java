package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * 修正了JS精度问题的适用于FastJson的DataInfo。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class JSFixedFastJsonDataInfo implements Dto {

    private static final long serialVersionUID = -2703218942703808169L;

    /**
     * 通过指定的 DataInfo 生成对应的 JSFixedFastJsonDataInfo。
     *
     * @param dataInfo 指定的 DataInfo.
     * @return 通过指定的 DataInfo 生成对应的 JSFixedFastJsonDataInfo。
     */
    public static JSFixedFastJsonDataInfo of(DataInfo dataInfo) {
        if (Objects.isNull(dataInfo)) {
            return null;
        }
        return new JSFixedFastJsonDataInfo(
                dataInfo.getPointLongId(),
                dataInfo.getValue(),
                dataInfo.getHappenedDate()
        );
    }

    @JSONField(name = "point_long_id", ordinal = 1, serializeUsing = ToStringSerializer.class)
    private long pointLongId;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public JSFixedFastJsonDataInfo() {
    }

    public JSFixedFastJsonDataInfo(long pointLongId, String value, Date happenedDate) {
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
        return "JSFixedFastJsonDataInfo{" +
                "pointLongId=" + pointLongId +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
