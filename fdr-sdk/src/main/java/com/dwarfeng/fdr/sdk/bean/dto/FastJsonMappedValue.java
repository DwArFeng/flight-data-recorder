package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.MappedValue;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

public class FastJsonMappedValue implements Bean {

    private static final long serialVersionUID = -4540917031880795257L;

    public static FastJsonMappedValue of(MappedValue mappedValue) {
        if (Objects.isNull(mappedValue)) {
            return null;
        }
        return new FastJsonMappedValue(
                mappedValue.getHappenedDate(),
                mappedValue.getValue()
        );
    }

    @JSONField(name = "happened_date", ordinal = 1)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    public FastJsonMappedValue() {
    }

    public FastJsonMappedValue(Date happenedDate, String value) {
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
        return "FastJsonMappedValue{" +
                "happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
