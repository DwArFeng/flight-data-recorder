package com.dwarfeng.fdr.sdk.validation.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.sdk.validation.bean.key.ValidationGuidKey;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.Serializable;

public class ValidationTriggerInfo implements Serializable {

    private static final long serialVersionUID = -196469351407772177L;

    @NotNull(groups = {Default.class})
    @Valid
    private ValidationGuidKey key;

    @Valid
    private ValidationGuidKey parentKey;

    private boolean enabled;

    @NotNull(groups = {Default.class, Insert.class})
    private String content;

    @Length(max = Constraints.LENGTH_REMARK, groups = {Default.class, Insert.class})
    private String remark;

    public ValidationTriggerInfo() {
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ApiTriggerInfo{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", enabled=" + enabled +
                ", content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
