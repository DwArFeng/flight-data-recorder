package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Date;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RealtimeValue implements Entity<UuidKey> {

    private UuidKey key;

    private Date happenedDate;

    private String value;

    public RealtimeValue() {
    }

    public RealtimeValue(UuidKey key, Date happenedDate, String value) {
        this.key = key;
        this.happenedDate = happenedDate;
        this.value = value;
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

    @Override
    public String toString() {
        return "RealtimeValue{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
