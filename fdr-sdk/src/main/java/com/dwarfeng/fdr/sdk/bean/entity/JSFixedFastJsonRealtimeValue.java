package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

public class JSFixedFastJsonRealtimeValue implements Bean {

    private static final long serialVersionUID = 4587662593922700637L;

    public static JSFixedFastJsonRealtimeValue of(RealtimeValue realtimeValue) {
        if (Objects.isNull(realtimeValue)) {
            return null;
        }
        return new JSFixedFastJsonRealtimeValue(
                JSFixedFastJsonLongIdKey.of(realtimeValue.getKey()),
                realtimeValue.getHappenedDate(),
                realtimeValue.getValue()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 4)
    private String value;

    public JSFixedFastJsonRealtimeValue() {
    }

    public JSFixedFastJsonRealtimeValue(JSFixedFastJsonLongIdKey key, Date happenedDate, String value) {
        this.key = key;
        this.happenedDate = happenedDate;
        this.value = value;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
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
        return "JSFixedFastJsonRealtimeValue{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
