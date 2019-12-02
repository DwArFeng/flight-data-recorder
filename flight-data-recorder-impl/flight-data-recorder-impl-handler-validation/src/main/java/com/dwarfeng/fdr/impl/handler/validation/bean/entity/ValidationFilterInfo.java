package com.dwarfeng.fdr.impl.handler.validation.bean.entity;

import com.dwarfeng.fdr.impl.handler.validation.bean.key.ValidationUuidKey;
import com.dwarfeng.fdr.sdk.util.Constants;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationFilterInfo implements Serializable {

    private static final long serialVersionUID = -4280300503710748718L;

    @NotNull
    @Valid
    private ValidationUuidKey key;

    @Valid
    private ValidationUuidKey pointKey;

    private boolean enabled;

    @Length(max = Constants.CONSTRAINT_LENGTH_FILTER_INFO_REMARK)
    private String remark;

    private String content;

    public ValidationFilterInfo() {
    }

    public ValidationFilterInfo(@NonNull @Valid ValidationUuidKey key, @Valid ValidationUuidKey pointKey, boolean enabled, @Length(max = Constants.CONSTRAINT_LENGTH_FILTER_INFO_REMARK) String remark, String content) {
        this.key = key;
        this.pointKey = pointKey;
        this.enabled = enabled;
        this.remark = remark;
        this.content = content;
    }

    @NonNull
    public ValidationUuidKey getKey() {
        return key;
    }

    public void setKey(@NonNull ValidationUuidKey key) {
        this.key = key;
    }

    public ValidationUuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(ValidationUuidKey pointKey) {
        this.pointKey = pointKey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ValidationFilterInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
