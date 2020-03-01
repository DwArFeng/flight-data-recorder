package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

public class JSFixedFastJsonFilteredValue implements Bean {

    private static final long serialVersionUID = -4088800961672758231L;

    public static JSFixedFastJsonFilteredValue of(FilteredValue filteredValue) {
        if (Objects.isNull(filteredValue)) {
            return null;
        }
        return new JSFixedFastJsonFilteredValue(
                JSFixedFastJsonLongIdKey.of(filteredValue.getKey()),
                JSFixedFastJsonLongIdKey.of(filteredValue.getPointKey()),
                JSFixedFastJsonLongIdKey.of(filteredValue.getFilterKey()),
                filteredValue.getHappenedDate(),
                filteredValue.getValue(),
                filteredValue.getMessage()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "filter_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey filterKey;

    @JSONField(name = "happened_date", ordinal = 4)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 5)
    private String value;

    @JSONField(name = "message", ordinal = 6)
    private String message;

    public JSFixedFastJsonFilteredValue() {
    }

    public JSFixedFastJsonFilteredValue(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey pointKey, JSFixedFastJsonLongIdKey filterKey,
            Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.filterKey = filterKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(JSFixedFastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public JSFixedFastJsonLongIdKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(JSFixedFastJsonLongIdKey filterKey) {
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
        return "JSFixedFastJsonFilteredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
