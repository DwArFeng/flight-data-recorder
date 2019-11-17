package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FilterInfo implements Entity<UuidKey> {

    /**
     * 主键。
     */
    private UuidKey key;

    private boolean enabled;

    private String remark;

    private String content;

    public FilterInfo() {
    }

    public FilterInfo(UuidKey key, boolean enabled, String remark, String content) {
        this.key = key;
        this.enabled = enabled;
        this.remark = remark;
        this.content = content;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(UuidKey key) {
        this.key = key;
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

    @Override
    public String toString() {
        return "FilterInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
