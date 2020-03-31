package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.TimedValue;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

public class FastJsonTimedValue implements Bean {

    private static final long serialVersionUID = -4540917031880795257L;

    public static FastJsonTimedValue of(TimedValue timedValue) {
        if (Objects.isNull(timedValue)) {
            return null;
        }
        return new FastJsonTimedValue(
                timedValue.getValue(),
                timedValue.getHappenedDate()
        );
    }

    @JSONField(name = "value", ordinal = 1)
    private String value;

    @JSONField(name = "happened_date", ordinal = 2)
    private Date happenedDate;

    public FastJsonTimedValue() {
    }

    public FastJsonTimedValue(String value, Date happenedDate) {
        this.value = value;
        this.happenedDate = happenedDate;
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
        return "FastJsonTimedValue{" +
                "value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
