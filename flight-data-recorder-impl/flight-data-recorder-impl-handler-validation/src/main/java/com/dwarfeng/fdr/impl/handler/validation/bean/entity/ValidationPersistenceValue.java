package com.dwarfeng.fdr.impl.handler.validation.bean.entity;

import com.dwarfeng.fdr.impl.handler.validation.bean.key.ValidationUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 持久化数据值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationPersistenceValue implements Serializable {

    private static final long serialVersionUID = 6647909312146667955L;

    @NotNull
    @Valid
    private ValidationUuidKey key;

    @NotNull
    @Valid
    private ValidationUuidKey pointKey;

    @NotNull
    private Date happenedDate;

    @Length(max = Constraints.LENGTH_PERSISTENCE_VALUE_VALUE)
    private String value;

    public ValidationPersistenceValue() {
    }

    public ValidationPersistenceValue(ValidationUuidKey key, ValidationUuidKey pointKey, Date happenedDate, String value) {
        this.key = key;
        this.pointKey = pointKey;
        this.happenedDate = happenedDate;
        this.value = value;
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

    @Override
    public String toString() {
        return "ValidationPersistenceValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
