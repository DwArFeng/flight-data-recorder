package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisGuidKey;

import java.io.Serializable;
import java.util.Date;

public class RedisPersistenceValue implements Serializable {

    private static final long serialVersionUID = 3792689493415676501L;

    @JSONField(name = "key", ordinal = 1)
    private RedisGuidKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private RedisGuidKey pointKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 4)
    private String value;

    public RedisPersistenceValue() {
    }

    public RedisPersistenceValue(RedisGuidKey key, RedisGuidKey pointKey, Date happenedDate, String value) {
        this.key = key;
        this.pointKey = pointKey;
        this.happenedDate = happenedDate;
        this.value = value;
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
        return "RedisPersistenceValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
