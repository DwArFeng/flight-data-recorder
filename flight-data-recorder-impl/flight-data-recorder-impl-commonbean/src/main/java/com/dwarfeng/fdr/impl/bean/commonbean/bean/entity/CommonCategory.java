package com.dwarfeng.fdr.impl.bean.commonbean.bean.entity;

import com.dwarfeng.fdr.impl.bean.commonbean.bean.key.CommonUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Category;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class CommonCategory implements Category {

    private static final long serialVersionUID = -6264447099891399232L;

    private CommonUuidKey key;

    private String name;

    private String remark;

    public CommonCategory() {
    }

    public CommonCategory(CommonUuidKey key, String name, String remark) {
        this.key = key;
        this.name = name;
        this.remark = remark;
    }

    @Override
    public CommonUuidKey getKey() {
        return key;
    }

    public void setKey(CommonUuidKey key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "CategoryImpl{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
