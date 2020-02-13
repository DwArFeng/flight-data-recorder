package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * FastJson数据点对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FastJsonPoint implements Bean {

    private static final long serialVersionUID = -6727125091352050788L;

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "catgory_key", ordinal = 2)
    private FastJsonLongIdKey categoryKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "persistence_enabled", ordinal = 5)
    private boolean persistenceEnabled;

    @JSONField(name = "realtime_enabled", ordinal = 6)
    private boolean realtimeEnabled;

    public FastJsonPoint() {
    }

    public FastJsonPoint(FastJsonLongIdKey key, FastJsonLongIdKey categoryKey, String name, String remark, boolean persistenceEnabled, boolean realtimeEnabled) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.name = name;
        this.remark = remark;
        this.persistenceEnabled = persistenceEnabled;
        this.realtimeEnabled = realtimeEnabled;
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(FastJsonLongIdKey categoryKey) {
        this.categoryKey = categoryKey;
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
        return "FastJsonPoint{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", persistenceEnabled=" + persistenceEnabled +
                ", realtimeEnabled=" + realtimeEnabled +
                '}';
    }
}
