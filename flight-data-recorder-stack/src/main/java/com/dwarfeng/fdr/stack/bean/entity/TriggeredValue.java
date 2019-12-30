package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.GuidKey;

import java.util.Date;

/**
 * 被触发的值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class TriggeredValue implements Entity<GuidKey> {

    private static final long serialVersionUID = 1771987209858988112L;

    /**
     * 主键。
     */
    private GuidKey key;

    /**
     * 数据点外键。
     */
    private GuidKey pointKey;

    /**
     * 触发器外键。
     */
    private GuidKey triggerKey;

    private Date happenedDate;

    private String value;

    private String message;

    public TriggeredValue() {
    }

    public TriggeredValue(GuidKey key, GuidKey pointKey, GuidKey triggerKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.triggerKey = triggerKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    @Override
    public GuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(GuidKey key) {
        this.key = key;
    }

    public GuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(GuidKey pointKey) {
        this.pointKey = pointKey;
    }

    public GuidKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(GuidKey triggerKey) {
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
