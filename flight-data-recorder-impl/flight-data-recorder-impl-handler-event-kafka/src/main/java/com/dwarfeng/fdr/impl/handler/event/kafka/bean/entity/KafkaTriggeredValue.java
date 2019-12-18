package com.dwarfeng.fdr.impl.handler.event.kafka.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.handler.event.kafka.bean.key.KafkaUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Entity;

import java.util.Date;

public class KafkaTriggeredValue implements Entity<KafkaUuidKey> {

    private static final long serialVersionUID = 1179465468881533540L;

    @JSONField(name = "key", ordinal = 1)
    private KafkaUuidKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private KafkaUuidKey pointKey;

    @JSONField(name = "trigger_key", ordinal = 3)
    private KafkaUuidKey triggerKey;

    @JSONField(name = "happened_date", ordinal = 4)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 5)
    private String value;

    @JSONField(name = "message", ordinal = 6)
    private String message;

    public KafkaTriggeredValue() {
    }

    public KafkaTriggeredValue(KafkaUuidKey key, KafkaUuidKey pointKey, KafkaUuidKey triggerKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    @Override
    public KafkaUuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(KafkaUuidKey key) {
        this.key = key;
    }

    public KafkaUuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(KafkaUuidKey pointKey) {
        this.pointKey = pointKey;
    }

    public KafkaUuidKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(KafkaUuidKey triggerKey) {
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
        return "KafkaTriggeredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", triggerKey=" + triggerKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
