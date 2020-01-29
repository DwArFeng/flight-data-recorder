package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 持久化数据值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class PersistenceValue implements Entity<LongIdKey> {

    private static final long serialVersionUID = -2096752760200032911L;

    /**
     * 主键。
     */
    private LongIdKey key;

    /**
     * 数据点外键。
     */
    private LongIdKey pointKey;

    private Date happenedDate;

    private String value;

    public PersistenceValue() {
    }

    public PersistenceValue(LongIdKey key, LongIdKey pointKey, Date happenedDate, String value) {
        this.key = key;
        this.pointKey = pointKey;
        this.happenedDate = happenedDate;
        this.value = value;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
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
