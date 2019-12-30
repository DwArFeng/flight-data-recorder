package com.dwarfeng.fdr.sdk.validation.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.sdk.validation.bean.key.ValidationGuidKey;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;

public class ValidationPoint implements Serializable {

    private static final long serialVersionUID = -1154446606532065579L;

    @NotNull(groups = {Default.class})
    @Valid
    private ValidationGuidKey key;

    @Valid
    private ValidationGuidKey categoryKey;

    @NotNull(groups = {Default.class, Insert.class})
    @Length(max = Constraints.LENGTH_NAME, groups = {Default.class, Insert.class})
    private String name;

    @Length(max = Constraints.LENGTH_REMARK, groups = {Default.class, Insert.class})
    private String remark;

    private boolean persistenceEnabled;

    private boolean realtimeEnabled;

    public ValidationPoint() {
    }

    public ValidationGuidKey getKey() {
        return key;
    }

    public void setKey(ValidationGuidKey key) {
        this.key = key;
    }

    public ValidationGuidKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(ValidationGuidKey categoryKey) {
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
        return "ApiPoint{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", persistenceEnabled=" + persistenceEnabled +
                ", realtimeEnabled=" + realtimeEnabled +
                '}';
    }
}
