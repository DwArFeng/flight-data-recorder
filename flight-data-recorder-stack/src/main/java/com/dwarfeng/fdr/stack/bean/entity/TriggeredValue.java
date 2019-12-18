package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Date;

/**
 * 被触发的值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class TriggeredValue implements Entity<UuidKey> {

    private static final long serialVersionUID = 1771987209858988112L;

    /**
     * 主键。
     */
    private UuidKey key;

    /**
     * 数据点外键。
     */
    private UuidKey pointKey;

    /**
     * 触发器外键。
     */
    private UuidKey triggerKey;

    private Date happenedDate;

    private String value;

    private String message;

    public TriggeredValue() {
    }

    public TriggeredValue(UuidKey key, UuidKey pointKey, UuidKey triggerKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(UuidKey key) {
        this.key = key;
    }

    public UuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(UuidKey pointKey) {
        this.pointKey = pointKey;
    }

    public UuidKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(UuidKey triggerKey) {
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
        return "TriggeredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", triggerKey=" + triggerKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
