package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisGuidKey;

import java.io.Serializable;
import java.util.Date;

public class RedisTriggeredValue implements Serializable {

    private static final long serialVersionUID = 333024773948079348L;

    @JSONField(name = "key", ordinal = 1)
    private RedisGuidKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private RedisGuidKey pointKey;

    @JSONField(name = "trigger_key", ordinal = 3)
    private RedisGuidKey triggerKey;

    @JSONField(name = "happened_date", ordinal = 4)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 5)
    private String value;

    @JSONField(name = "message", ordinal = 6)
    private String message;

    public RedisTriggeredValue() {
    }

    public RedisTriggeredValue(RedisGuidKey key, RedisGuidKey pointKey, RedisGuidKey triggerKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
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

    public RedisGuidKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(RedisGuidKey triggerKey) {
        this.triggerKey = triggerKey;
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
                ", triggerKey=" + triggerKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
