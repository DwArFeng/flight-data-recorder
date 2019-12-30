package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.GuidKey;

import java.util.Date;

/**
 * 持久化数据值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class PersistenceValue implements Entity<GuidKey> {

    private static final long serialVersionUID = -2096752760200032911L;

    /**
     * 主键。
     */
    private GuidKey key;

    /**
     * 数据点外键。
     */
    private GuidKey pointKey;

    private Date happenedDate;

    private String value;

    public PersistenceValue() {
    }

    public PersistenceValue(GuidKey key, GuidKey pointKey, Date happenedDate, String value) {
        this.key = key;
        this.pointKey = pointKey;
        this.happenedDate = happenedDate;
        this.value = value;
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
        return "PersistenceValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
