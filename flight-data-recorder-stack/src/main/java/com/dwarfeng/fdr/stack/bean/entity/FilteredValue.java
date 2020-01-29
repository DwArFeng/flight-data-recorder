package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;

/**
 * 被过滤的值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FilteredValue implements Entity<LongIdKey> {

    private static final long serialVersionUID = -3479250934578863439L;

    /**
     * 主键。
     */
    private LongIdKey key;

    /**
     * 数据点外键。
     */
    private LongIdKey pointKey;

    /**
     * 过滤器外键。
     */
    private LongIdKey filterKey;

    private Date happenedDate;

    private String value;

    private String message;

    public FilteredValue() {
    }

    public FilteredValue(LongIdKey key, LongIdKey pointKey, LongIdKey filterKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.filterKey = filterKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
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

    public LongIdKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(LongIdKey filterKey) {
        this.filterKey = filterKey;
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
        return "FilteredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
