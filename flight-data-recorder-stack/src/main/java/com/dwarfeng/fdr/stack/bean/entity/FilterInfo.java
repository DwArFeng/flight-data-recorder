package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.GuidKey;

/**
 * 过滤器信息。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class FilterInfo implements Entity<GuidKey> {

    private static final long serialVersionUID = 3393705759584503229L;

    /**
     * 主键。
     */
    private GuidKey key;

    /**
     * 数据点键。
     */
    private GuidKey pointKey;

    private boolean enabled;

    private String remark;

    private String content;

    public FilterInfo() {
    }

    public FilterInfo(GuidKey key, GuidKey pointKey, boolean enabled, String remark, String content) {
        this.key = key;
        this.pointKey = pointKey;
        this.enabled = enabled;
        this.remark = remark;
        this.content = content;
    }

    @Override
    public GuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(GuidKey key) {
        this.key = key;
    }

    public GuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(GuidKey pointKey) {
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

    @Override
    public String toString() {
        return "FilterInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
