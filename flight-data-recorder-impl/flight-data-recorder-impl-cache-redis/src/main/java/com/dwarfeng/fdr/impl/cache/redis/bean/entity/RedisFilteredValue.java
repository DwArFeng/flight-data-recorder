package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisGuidKey;

import java.io.Serializable;
import java.util.Date;

public class RedisFilteredValue implements Serializable {

    private static final long serialVersionUID = -8969452860654354134L;

    @JSONField(name = "key", ordinal = 1)
    private RedisGuidKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private RedisGuidKey pointKey;

    @JSONField(name = "filter_key", ordinal = 3)
    private RedisGuidKey filterKey;

    @JSONField(name = "happened_date", ordinal = 4)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 5)
    private String value;

    @JSONField(name = "message", ordinal = 6)
    private String message;

    public RedisFilteredValue() {
    }

    public RedisFilteredValue(RedisGuidKey key, RedisGuidKey pointKey, RedisGuidKey filterKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.filterKey = filterKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    public RedisGuidKey getKey() {
        return key;
    }

    public void setKey(RedisGuidKey key) {
        this.key = key;
    }

    public RedisGuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(RedisGuidKey pointKey) {
        this.pointKey = pointKey;
    }

    public RedisGuidKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(RedisGuidKey filterKey) {
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
        return "RedisFilteredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
