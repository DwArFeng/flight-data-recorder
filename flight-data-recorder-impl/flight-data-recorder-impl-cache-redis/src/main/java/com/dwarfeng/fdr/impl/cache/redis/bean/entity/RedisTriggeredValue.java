package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Entity;

import java.util.Date;

public class RedisTriggeredValue implements Entity<RedisUuidKey> {

    private static final long serialVersionUID = 333024773948079348L;

    @JSONField(name = "key", ordinal = 1)
    private RedisUuidKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private RedisUuidKey pointKey;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 4)
    private String value;

    @JSONField(name = "message", ordinal = 5)
    private String message;

    public RedisTriggeredValue() {
    }

    public RedisTriggeredValue(RedisUuidKey key, RedisUuidKey pointKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    @Override
    public RedisUuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(RedisUuidKey key) {
        this.key = key;
    }

    public RedisUuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(RedisUuidKey pointKey) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RedisTriggeredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
