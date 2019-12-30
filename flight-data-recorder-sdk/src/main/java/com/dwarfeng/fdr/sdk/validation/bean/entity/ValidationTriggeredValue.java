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

public class ValidationTriggeredValue implements Serializable {

    private static final long serialVersionUID = -4752059815608159053L;

    @NotNull(groups = {Default.class})
    @Valid
    private ValidationGuidKey key;

    @Valid
    private ValidationGuidKey pointKey;

    @Valid
    private ValidationGuidKey triggerKey;

    @NotNull(groups = {Default.class, Insert.class})
    private Date happenedDate;

    @NotNull(groups = {Default.class, Insert.class})
    @Length(max = Constraints.LENGTH_VALUE, groups = {Default.class, Insert.class})
    private String value;

    @Length(max = Constraints.LENGTH_MESSAGE, groups = {Default.class, Insert.class})
    private String message;

    public ValidationTriggeredValue() {
    }

    public ValidationGuidKey getKey() {
        return key;
    }

    public void setKey(ValidationGuidKey key) {
        this.key = key;
    }

    public ValidationGuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(ValidationGuidKey pointKey) {
        this.pointKey = pointKey;
    }

    public ValidationGuidKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(ValidationGuidKey triggerKey) {
        this.triggerKey = triggerKey;
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
        return "ApiTriggeredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", triggerKey=" + triggerKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
