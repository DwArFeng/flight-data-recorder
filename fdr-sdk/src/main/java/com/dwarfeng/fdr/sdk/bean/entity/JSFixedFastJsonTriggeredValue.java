package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Date;
import java.util.Objects;

public class JSFixedFastJsonTriggeredValue implements Bean {

    private static final long serialVersionUID = 5700403007038286403L;

    public static JSFixedFastJsonTriggeredValue of(TriggeredValue triggeredValue) {
        if (Objects.isNull(triggeredValue)) {
            return null;
        }
        return new JSFixedFastJsonTriggeredValue(
                JSFixedFastJsonLongIdKey.of(triggeredValue.getKey()),
                JSFixedFastJsonLongIdKey.of(triggeredValue.getPointKey()),
                JSFixedFastJsonLongIdKey.of(triggeredValue.getTriggerKey()),
                triggeredValue.getHappenedDate(),
                triggeredValue.getValue(),
                triggeredValue.getMessage()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "trigger_key", ordinal = 3)
    private JSFixedFastJsonLongIdKey triggerKey;

    @JSONField(name = "happened_date", ordinal = 4)
    private Date happenedDate;

    @JSONField(name = "value", ordinal = 5)
    private String value;

    @JSONField(name = "message", ordinal = 6)
    private String message;

    public JSFixedFastJsonTriggeredValue() {
    }

    public JSFixedFastJsonTriggeredValue(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey pointKey, JSFixedFastJsonLongIdKey triggerKey,
            Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
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

    public JSFixedFastJsonLongIdKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(JSFixedFastJsonLongIdKey triggerKey) {
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
        return "JSFixedFastJsonTriggeredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", triggerKey=" + triggerKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
