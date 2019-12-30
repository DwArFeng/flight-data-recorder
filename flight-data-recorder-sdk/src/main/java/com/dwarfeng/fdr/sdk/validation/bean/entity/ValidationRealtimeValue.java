package com.dwarfeng.fdr.sdk.validation.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.sdk.validation.bean.key.ValidationGuidKey;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

public class ValidationRealtimeValue implements Serializable {

    private static final long serialVersionUID = -7659338124478555535L;

    @NotNull(groups = {Default.class, Insert.class})
    @Valid
    private ValidationGuidKey key;

    @NotNull(groups = {Default.class, Insert.class})
    private Date happenedDate;

    @NotNull(groups = {Default.class, Insert.class})
    @Length(max = Constraints.LENGTH_VALUE, groups = {Default.class, Insert.class})
    private String value;

    public ValidationRealtimeValue() {
    }

    public ValidationGuidKey getKey() {
        return key;
    }

    public void setKey(ValidationGuidKey key) {
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
        return "ApiRealtimeValue{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
