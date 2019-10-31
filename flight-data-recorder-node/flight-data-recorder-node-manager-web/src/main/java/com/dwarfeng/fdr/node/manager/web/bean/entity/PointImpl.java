package com.dwarfeng.fdr.node.manager.web.bean.entity;

import com.dwarfeng.fdr.node.manager.web.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;

public class PointImpl implements Point {

    private static final long serialVersionUID = 5256767191752007679L;

    private NameKeyImpl key;

    private String type;

    private boolean persistence;

    private String remark;

    public PointImpl() {
    }

    public PointImpl(NameKeyImpl key, String type, boolean persistence, String remark) {
        this.key = key;
        this.type = type;
        this.persistence = persistence;
        this.remark = remark;
    }

    @Override
    public NameKeyImpl getKey() {
        return key;
    }

    public void setKey(NameKeyImpl key) {
        this.key = key;
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
                ", type='" + type + '\'' +
                ", persistence=" + persistence +
                ", remark='" + remark + '\'' +
                '}';
    }
}
