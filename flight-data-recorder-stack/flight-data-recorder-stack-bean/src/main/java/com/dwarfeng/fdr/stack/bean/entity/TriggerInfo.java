package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 触发器信息。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class TriggerInfo implements Entity<UuidKey> {

    private static final long serialVersionUID = 976639449175248078L;

    /**
     * 主键。
     */
    private UuidKey key;

    /**
     * 是否启用。
     */
    private boolean enabled;

    /**
     * 备注。
     */
    private String remark;

    /**
     * 内容。
     */
    private String content;

    public TriggerInfo() {
    }

    public TriggerInfo(UuidKey key, boolean enabled, String remark, String content) {
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
        return "TriggerInfo{" +
                "key=" + key +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
