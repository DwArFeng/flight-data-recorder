package com.dwarfeng.fdr.impl.handler.event.kafka.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.handler.event.kafka.bean.key.KafkaGuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Entity;

import java.util.Date;

public class KafkaTriggeredValue implements Entity<KafkaGuidKey> {

    private static final long serialVersionUID = 1179465468881533540L;

    @JSONField(name = "key", ordinal = 1)
    private KafkaGuidKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private KafkaGuidKey pointKey;

    @JSONField(name = "trigger_key", ordinal = 3)
    private KafkaGuidKey triggerKey;

    @JSONField(name = "happened_date", ordinal = 4)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 5)
    private String value;

    @JSONField(name = "message", ordinal = 6)
    private String message;

    public KafkaTriggeredValue() {
    }

    public KafkaTriggeredValue(KafkaGuidKey key, KafkaGuidKey pointKey, KafkaGuidKey triggerKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    @Override
    public KafkaGuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(KafkaGuidKey key) {
        this.key = key;
    }

    public KafkaGuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(KafkaGuidKey pointKey) {
        this.pointKey = pointKey;
    }

    public KafkaGuidKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(KafkaGuidKey triggerKey) {
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
