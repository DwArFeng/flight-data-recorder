package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 分类。
 */
public class Category implements Entity<UuidKey> {

    private static final long serialVersionUID = 270451223976111366L;

    /**
     * 主键。
     */
    private UuidKey key;
    /**
     * 父项。
     */
    private UuidKey parentKey;
    /**
     * 名称。
     */
    private String name;
    /**
     * 备注。
     */
    private String remark;

    public Category() {
    }

    public Category(UuidKey key, UuidKey parentKey, String name, String remark) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(UuidKey key) {
        this.key = key;
    }

    public UuidKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(UuidKey parentKey) {
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
        return "Category{" +
                "key=" + key +
                ", parentKey=" + parentKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
