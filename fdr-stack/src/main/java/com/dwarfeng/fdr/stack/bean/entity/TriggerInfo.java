package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 触发器信息。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class TriggerInfo implements Entity<LongIdKey> {

    private static final long serialVersionUID = -1663272353581715666L;

    private LongIdKey key;
    private LongIdKey pointKey;
    private boolean enabled;
    private String remark;
    private String content;
    private String type;

    public TriggerInfo() {
    }

    public TriggerInfo(LongIdKey key, LongIdKey pointKey, boolean enabled, String remark, String content, String type) {
        this.key = key;
        this.pointKey = pointKey;
        this.enabled = enabled;
        this.remark = remark;
        this.content = content;
        this.type = type;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TriggerInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
