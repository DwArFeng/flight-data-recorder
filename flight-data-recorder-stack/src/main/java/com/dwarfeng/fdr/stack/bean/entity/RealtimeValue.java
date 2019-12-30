package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.GuidKey;

import java.util.Date;

/**
 * 实时数据。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RealtimeValue implements Entity<GuidKey> {

    private static final long serialVersionUID = -5851108509570366034L;

    /**
     * 主键。
     */
    private GuidKey key;

    private Date happenedDate;

    private String value;

    public RealtimeValue() {
    }

    public RealtimeValue(GuidKey key, Date happenedDate, String value) {
        this.key = key;
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
