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
public class FastJsonFilterInfo implements Bean {

    private static final long serialVersionUID = 635115142498652853L;

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "content", ordinal = 5)
    private String content;

    @JSONField(name = "type", ordinal = 6)
    private String type;

    public FastJsonFilterInfo() {
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
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
        return "FastJsonFilterInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
