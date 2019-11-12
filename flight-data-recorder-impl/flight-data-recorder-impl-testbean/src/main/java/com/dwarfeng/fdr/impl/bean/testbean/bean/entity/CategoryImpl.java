package com.dwarfeng.fdr.impl.bean.testbean.bean.entity;

import com.dwarfeng.fdr.impl.bean.testbean.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Category;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class CategoryImpl implements Category {

    private static final long serialVersionUID = -6264447099891399232L;

    private UuidKeyImpl key;

    private String name;

    private String remark;

    public CategoryImpl() {
    }

    public CategoryImpl(UuidKeyImpl key, String name, String remark) {
        this.key = key;
        this.name = name;
        this.remark = remark;
    }

    @Override
    public UuidKeyImpl getKey() {
        return key;
    }

    public void setKey(UuidKeyImpl key) {
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
