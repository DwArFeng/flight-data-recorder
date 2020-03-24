package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;

/**
 * 被映射的数据值。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public class MappedValue implements Dto {

    private static final long serialVersionUID = -2721833507011331854L;

    private Date happenedDate;
    private String value;

    public MappedValue() {
    }

    public MappedValue(Date happenedDate, String value) {
        this.happenedDate = happenedDate;
        this.value = value;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MappedValue{" +
                "happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
