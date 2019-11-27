package com.dwarfeng.fdr.impl.handler.validation.bean.entity;

import com.dwarfeng.fdr.impl.handler.validation.bean.key.ValidationUuidKey;
import com.dwarfeng.fdr.sdk.util.Constants;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationPoint implements Serializable {

    private static final long serialVersionUID = 8875178147675699532L;

    @NotNull
    @Valid
    private ValidationUuidKey key;

    @Valid
    private ValidationUuidKey categoryKey;

    @NotNull
    @Length(max = Constants.CONSTRAINT_LENGTH_POINT_NAME)
    private String name;

    @Length(max = Constants.CONSTRAINT_LENGTH_POINT_REMARK)
    private String remark;

    private boolean persistenceEnabled;

    private boolean realtimeEnabled;

    public ValidationPoint() {
    }

    public ValidationPoint(ValidationUuidKey key, ValidationUuidKey categoryKey, String name, String remark, boolean persistenceEnabled, boolean realtimeEnabled) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.name = name;
        this.remark = remark;
        this.persistenceEnabled = persistenceEnabled;
        this.realtimeEnabled = realtimeEnabled;
    }

    public ValidationUuidKey getKey() {
        return key;
    }

    public void setKey(ValidationUuidKey key) {
        this.key = key;
    }

    public ValidationUuidKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(ValidationUuidKey categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isPersistenceEnabled() {
        return persistenceEnabled;
    }

    public void setPersistenceEnabled(boolean persistenceEnabled) {
        this.persistenceEnabled = persistenceEnabled;
    }

    public boolean isRealtimeEnabled() {
        return realtimeEnabled;
    }

    public void setRealtimeEnabled(boolean realtimeEnabled) {
        this.realtimeEnabled = realtimeEnabled;
    }

    @Override
    public String toString() {
        return "ValidationPoint{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", persistenceEnabled=" + persistenceEnabled +
                ", realtimeEnabled=" + realtimeEnabled +
                '}';
    }
}
