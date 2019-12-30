package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisGuidKey;

import java.io.Serializable;
import java.util.Date;

public class RedisRealtimeValue implements Serializable {

    private static final long serialVersionUID = 2093936111729327278L;

    @JSONField(name = "key", ordinal = 1)
    private RedisGuidKey key;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 4)
    private String value;

    public RedisRealtimeValue() {
    }

    public RedisRealtimeValue(RedisGuidKey key, Date happenedDate, String value) {
        this.key = key;
        this.happenedDate = happenedDate;
        this.value = value;
    }

    public RedisGuidKey getKey() {
        return key;
    }

    public void setKey(RedisGuidKey key) {
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
        return "RedisRealtimeValue{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
