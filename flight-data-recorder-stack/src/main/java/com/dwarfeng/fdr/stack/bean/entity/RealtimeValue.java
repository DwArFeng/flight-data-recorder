package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Date;

/**
 * 实时数据。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RealtimeValue implements Entity<UuidKey> {

    private static final long serialVersionUID = -5851108509570366034L;

    /**
     * 主键。
     */
    private UuidKey key;

    /**
     * 数据点外键。
     */
    private UuidKey pointKey;

    private Date happenedDate;

    private String value;

    public RealtimeValue() {
    }

    public RealtimeValue(UuidKey key, UuidKey pointKey, Date happenedDate, String value) {
        this.key = key;
        this.pointKey = pointKey;
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

    public UuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(UuidKey pointKey) {
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

    @Override
    public String toString() {
        return "RealtimeValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
