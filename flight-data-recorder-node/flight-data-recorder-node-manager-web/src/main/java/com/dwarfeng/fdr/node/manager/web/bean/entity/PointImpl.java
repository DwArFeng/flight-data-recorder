package com.dwarfeng.fdr.node.manager.web.bean.entity;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

public class PointImpl implements Point {

    private static final long serialVersionUID = -5760702843039539791L;

    private UuidKey key;

    private String name;

    private String type;

    private boolean persistence;

    private String remark;

    public PointImpl() {
    }

    public PointImpl(UuidKey key, String name, String type, boolean persistence, String remark) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.persistence = persistence;
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isPersistence() {
        return persistence;
    }

    public void setPersistence(boolean persistence) {
        this.persistence = persistence;
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
        return "PointImpl{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", persistence=" + persistence +
                ", remark='" + remark + '\'' +
                '}';
    }
}
