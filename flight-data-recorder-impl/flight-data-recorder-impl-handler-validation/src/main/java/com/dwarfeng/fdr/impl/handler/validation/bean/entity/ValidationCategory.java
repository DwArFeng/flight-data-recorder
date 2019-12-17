package com.dwarfeng.fdr.impl.handler.validation.bean.entity;

import com.dwarfeng.fdr.impl.handler.validation.bean.key.ValidationUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationCategory implements Serializable {

    private static final long serialVersionUID = -5872875775155764577L;

    @NotNull
    @Valid
    private ValidationUuidKey key;

    @Valid
    private ValidationUuidKey parentKey;

    @NotNull
    @Length(max = Constraints.LENGTH_CATAGORY_NAME)
    private String name;

    @Length(max = Constraints.LENGTH_CATAGORY_REMARK)
    private String remark;

    public ValidationCategory() {
    }

    public ValidationCategory(@NotNull ValidationUuidKey key, @NotNull @Length(max = 50) String name, @Length(max = 100) String remark) {
        this.key = key;
        this.name = name;
        this.remark = remark;
    }

    public ValidationUuidKey getKey() {
        return key;
    }

    public void setKey(ValidationUuidKey key) {
        this.key = key;
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
        return "ValidationCategory{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
