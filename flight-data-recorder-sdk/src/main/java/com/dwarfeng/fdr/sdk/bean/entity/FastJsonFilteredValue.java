package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;

public class FastJsonFilteredValue implements Bean {

    private static final long serialVersionUID = -8969452860654354134L;

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "filter_key", ordinal = 3)
    private FastJsonLongIdKey filterKey;

    @JSONField(name = "happened_date", ordinal = 4)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 5)
    private String value;

    @JSONField(name = "message", ordinal = 6)
    private String message;

    public FastJsonFilteredValue() {
    }

    public FastJsonFilteredValue(FastJsonLongIdKey key, FastJsonLongIdKey pointKey, FastJsonLongIdKey filterKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.filterKey = filterKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public FastJsonLongIdKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(FastJsonLongIdKey filterKey) {
        this.filterKey = filterKey;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FastJsonFilteredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
