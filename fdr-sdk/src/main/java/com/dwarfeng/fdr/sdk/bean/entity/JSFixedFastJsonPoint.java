package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

public class JSFixedFastJsonPoint implements Bean {

    private static final long serialVersionUID = -8148864106012951708L;

    public static JSFixedFastJsonPoint of(Point point) {
        if (Objects.isNull(point)) {
            return null;
        }
        return new JSFixedFastJsonPoint(
                JSFixedFastJsonLongIdKey.of(point.getKey()),
                point.getName(),
                point.getRemark(),
                point.isPersistenceEnabled(),
                point.isRealtimeEnabled()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "name", ordinal = 2)
    private String name;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    @JSONField(name = "persistence_enabled", ordinal = 4)
    private boolean persistenceEnabled;

    @JSONField(name = "realtime_enabled", ordinal = 5)
    private boolean realtimeEnabled;

    public JSFixedFastJsonPoint() {
    }

    public JSFixedFastJsonPoint(
            JSFixedFastJsonLongIdKey key, String name, String remark, boolean persistenceEnabled, boolean realtimeEnabled) {
        this.key = key;
        this.name = name;
        this.remark = remark;
        this.persistenceEnabled = persistenceEnabled;
        this.realtimeEnabled = realtimeEnabled;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
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

    public boolean isPersistenceEnabled() {
        return persistenceEnabled;
    }

    public void setPersistenceEnabled(boolean persistenceEnabled) {
        this.persistenceEnabled = persistenceEnabled;
    }

    public boolean isRealtimeEnabled() {
        return realtimeEnabled;
    }

    public void setRealtimeEnabled(boolean realtimeEnabled) {
        this.realtimeEnabled = realtimeEnabled;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonPoint{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", persistenceEnabled=" + persistenceEnabled +
                ", realtimeEnabled=" + realtimeEnabled +
                '}';
    }
}
