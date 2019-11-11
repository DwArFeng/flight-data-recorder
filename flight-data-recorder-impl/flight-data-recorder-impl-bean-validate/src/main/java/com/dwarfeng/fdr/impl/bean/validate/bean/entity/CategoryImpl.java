package com.dwarfeng.fdr.impl.bean.validate.bean.entity;

import com.dwarfeng.fdr.stack.bean.entity.Category;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class CategoryImpl implements Category {

    private static final long serialVersionUID = -6264447099891399232L;

    private UuidKey key;

    private String name;

    private String remark;

    public CategoryImpl() {
    }

    public CategoryImpl(UuidKey key, String name, String remark) {
        this.key = key;
        this.name = name;
        this.remark = remark;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    public void setKey(UuidKey key) {
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
