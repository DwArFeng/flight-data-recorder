package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.GuidKey;

import java.util.Date;

/**
 * 被过滤的值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FilteredValue implements Entity<GuidKey> {

    private static final long serialVersionUID = -3479250934578863439L;

    /**
     * 主键。
     */
    private GuidKey key;

    /**
     * 数据点外键。
     */
    private GuidKey pointKey;

    /**
     * 过滤器外键。
     */
    private GuidKey filterKey;

    private Date happenedDate;

    private String value;

    private String message;

    public FilteredValue() {
    }

    public FilteredValue(GuidKey key, GuidKey pointKey, GuidKey filterKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.filterKey = filterKey;
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

    public GuidKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(GuidKey filterKey) {
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
