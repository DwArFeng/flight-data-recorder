package com.dwarfeng.fdr.sdk.validation.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.sdk.validation.bean.key.ValidationGuidKey;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;

public class ValidationCategory implements Serializable {

    private static final long serialVersionUID = -3015892072976478884L;

    @NotNull(groups = {Default.class})
    @Valid
    private ValidationGuidKey key;

    @Valid
    private ValidationGuidKey parentKey;

    @NotNull(groups = {Default.class, Insert.class})
    @Length(max = Constraints.LENGTH_NAME, groups = {Default.class, Insert.class})
    private String name;

    @Length(max = Constraints.LENGTH_REMARK, groups = {Default.class, Insert.class})
    private String remark;

    public ValidationCategory() {
    }

    public ValidationGuidKey getKey() {
        return key;
    }

    public void setKey(ValidationGuidKey key) {
        this.key = key;
    }

    public ValidationGuidKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(ValidationGuidKey parentKey) {
        this.parentKey = parentKey;
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

    @Override
    public String toString() {
        return "ApiCategory{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
