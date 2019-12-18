package com.dwarfeng.fdr.impl.handler.validation.bean.entity;

import com.dwarfeng.fdr.impl.handler.validation.bean.key.ValidationUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 被触发的值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationTriggeredValue implements Serializable {

    private static final long serialVersionUID = -6009186344627624502L;

    @NotNull
    @Valid
    private ValidationUuidKey key;

    @NotNull
    @Valid
    private ValidationUuidKey pointKey;

    @NotNull
    private Date happenedDate;

    @Length(max = Constraints.LENGTH_TRIGGERED_VALUE_VALUE)
    private String value;

    @Length(max = Constraints.LENGTH_TRIGGERED_VALUE_MESSAGE)
    private String message;

    public ValidationTriggeredValue() {
    }

    public ValidationTriggeredValue(ValidationUuidKey key, ValidationUuidKey pointKey, Date happenedDate, String value, String message) {
        this.key = key;
        this.pointKey = pointKey;
        this.happenedDate = happenedDate;
        this.value = value;
        this.message = message;
    }

    public ValidationUuidKey getKey() {
        return key;
    }

    public void setKey(ValidationUuidKey key) {
        this.key = key;
    }

    public ValidationUuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(ValidationUuidKey pointKey) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TriggeredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}