package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.GuidKey;

/**
 * 分类。
 */
public class Category implements Entity<GuidKey> {

    private static final long serialVersionUID = 270451223976111366L;

    /**
     * 主键。
     */
    private GuidKey key;
    /**
     * 父项。
     */
    private GuidKey parentKey;
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

    public Category(GuidKey key, GuidKey parentKey, String name, String remark) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
    }

    @Override
    public GuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(GuidKey key) {
        this.key = key;
    }

    public GuidKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(GuidKey parentKey) {
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
