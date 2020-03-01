package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

public class JSFixedFastJsonPersistenceValue implements Bean {

    private static final long serialVersionUID = -1461951520454701826L;

    public static JSFixedFastJsonPersistenceValue of(PersistenceValue persistenceValue) {
        if (Objects.isNull(persistenceValue)) {
            return null;
        }
        return new JSFixedFastJsonPersistenceValue(
                JSFixedFastJsonLongIdKey.of(persistenceValue.getKey()),
                JSFixedFastJsonLongIdKey.of(persistenceValue.getPointKey()),
                persistenceValue.getHappenedDate(),
                persistenceValue.getValue()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 4)
    private String value;

    public JSFixedFastJsonPersistenceValue() {
    }

    public JSFixedFastJsonPersistenceValue(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey pointKey, Date happenedDate, String value) {
        this.key = key;
        this.pointKey = pointKey;
        this.happenedDate = happenedDate;
        this.value = value;
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
        return "JSFixedFastJsonPersistenceValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
