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

    private static final long serialVersionUID = -4408273740766562224L;

    private UuidKey key;

    private Date happenedDate;

    private String value;

    private String message;

    public TriggeredValue() {
    }

    public TriggeredValue(UuidKey key, Date happenedDate, String value, String message) {
        this.key = key;
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
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
