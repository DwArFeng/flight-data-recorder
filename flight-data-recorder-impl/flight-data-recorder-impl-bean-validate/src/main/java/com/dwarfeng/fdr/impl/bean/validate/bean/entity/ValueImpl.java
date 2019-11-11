package com.dwarfeng.fdr.impl.bean.validate.bean.entity;

import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Date;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValueImpl implements Value {

    private static final long serialVersionUID = -595194912918789614L;

    private UuidKey key;

    private Date happenedDate;

    private String value;

    public ValueImpl() {
    }

    public ValueImpl(UuidKey key, Date happenedDate, String value) {
        this.key = key;
        this.happenedDate = happenedDate;
        this.value = value;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    public void setKey(UuidKey key) {
        this.key = key;
    }

    @Override
    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueImpl{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
