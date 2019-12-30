package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.GuidKey;

/**
 * 数据点。
 */
public class Point implements Entity<GuidKey> {

    private static final long serialVersionUID = 9116129592160719291L;

    /**
     * 主键。
     */
    private GuidKey key;
    /**
     * 所属分类主键。
     */
    private GuidKey categoryKey;
    /**
     * 数据点的名称。
     */
    private String name;
    /**
     * 备注。
     */
    private String remark;
    /**
     * 是否启用持久化。
     */
    private boolean persistenceEnabled;
    /**
     * 是否启用实时化。
     */
    private boolean realtimeEnabled;

    public Point() {
    }

    public Point(GuidKey key, GuidKey categoryKey, String name, String remark, boolean persistenceEnabled, boolean realtimeEnabled) {
        this.key = key;
        this.categoryKey = categoryKey;
        this.name = name;
        this.remark = remark;
        this.persistenceEnabled = persistenceEnabled;
        this.realtimeEnabled = realtimeEnabled;
    }

    @Override
    public GuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(GuidKey key) {
        this.key = key;
    }

    public GuidKey getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(GuidKey categoryKey) {
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
        return "Point{" +
                "key=" + key +
                ", categoryKey=" + categoryKey +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", persistenceEnabled=" + persistenceEnabled +
                ", realtimeEnabled=" + realtimeEnabled +
                '}';
    }
}
