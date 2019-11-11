package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;

public class PointImpl implements Point {

    private static final long serialVersionUID = 3341362620055984107L;

    @JSONField(name = "key", ordinal = 1)
    private UuidKeyImpl key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "type", ordinal = 3)
    private String type;

    @JSONField(name = "persistence", ordinal = 4)
    private boolean persistence;

    @JSONField(name = "remark", ordinal = 5)
    private String remark;

    public PointImpl() {
    }

    public PointImpl(UuidKeyImpl key, String name, String type, boolean persistence, String remark) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.persistence = persistence;
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
