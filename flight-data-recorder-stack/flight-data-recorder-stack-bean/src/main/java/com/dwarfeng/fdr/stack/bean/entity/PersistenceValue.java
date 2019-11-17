package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Date;

/**
 * 持久化数据值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class PersistenceValue implements Entity<UuidKey> {

    private static final long serialVersionUID = 4820909896408017954L;

    private UuidKey key;

    private Date happenedDate;

    private String value;

    public PersistenceValue() {
    }

    public PersistenceValue(UuidKey key, Date happenedDate, String value) {
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
        return "PersistenceValue{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
