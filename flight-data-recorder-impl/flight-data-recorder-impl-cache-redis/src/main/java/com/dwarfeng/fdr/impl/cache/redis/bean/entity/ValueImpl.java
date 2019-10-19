package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Date;

public class ValueImpl implements Value {

    private static final long serialVersionUID = 8094022497241806770L;

    @JSONField(name = "key", ordinal = 1)
    private UuidKey key;

    @JSONField(name = "happened_date", ordinal = 2)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 3)
    private String value;

    public ValueImpl() {
    }

    public ValueImpl(UuidKey key, Date happenedDate, String value) {
        this.key = key;
        this.happenedDate = happenedDate;
        this.value = value;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    public void setKey(UuidKey key) {
        this.key = key;
    }

    @Override
    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueImpl{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
