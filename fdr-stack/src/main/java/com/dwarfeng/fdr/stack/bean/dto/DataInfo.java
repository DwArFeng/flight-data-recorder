package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * 数据值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class DataInfo implements Dto {

    private static final long serialVersionUID = 6360037498642277881L;

    /**
     * 数据点的UUID。
     */
    private long pointLongId;

    /**
     * 数据的文本格式值。
     */
    private String value;

    /**
     * 数据值的发生日期。
     */
    private Date happenedDate;

    public DataInfo() {
    }

    public DataInfo(long pointLongId, String value, Date happenedDate) {
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
        return "DataInfo{" +
                "pointLongId=" + pointLongId +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
