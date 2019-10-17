package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class PointImpl implements Point {

    private static final long serialVersionUID = 5256767191752007679L;

    @JSONField(name = "key", ordinal = 1)
    private NameKey key;

    @JSONField(name = "type", ordinal = 2)
    private String type;

    @JSONField(name = "persistence", ordinal = 3)
    private boolean persistence;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public PointImpl() {
    }

    public PointImpl(NameKey key, String type, boolean persistence, String remark) {
        this.key = key;
        this.type = type;
        this.persistence = persistence;
        this.remark = remark;
    }

    @Override
    public NameKey getKey() {
        return key;
    }

    public void setKey(NameKey key) {
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
