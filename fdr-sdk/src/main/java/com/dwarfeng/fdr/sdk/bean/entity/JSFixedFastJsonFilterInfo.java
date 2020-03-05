package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

public class JSFixedFastJsonFilterInfo implements Bean {

    private static final long serialVersionUID = -3853321213360199542L;

    public static JSFixedFastJsonFilterInfo of(FilterInfo filterInfo) {
        if (Objects.isNull(filterInfo)) {
            return null;
        }
        return new JSFixedFastJsonFilterInfo(
                JSFixedFastJsonLongIdKey.of(filterInfo.getKey()),
                JSFixedFastJsonLongIdKey.of(filterInfo.getPointKey()),
                filterInfo.isEnabled(),
                filterInfo.getRemark(),
                filterInfo.getContent(),
                filterInfo.getType()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey pointKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "content", ordinal = 5)
    private String content;

    @JSONField(name = "type", ordinal = 6)
    private String type;

    public JSFixedFastJsonFilterInfo() {
    }

    public JSFixedFastJsonFilterInfo(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey pointKey, boolean enabled, String remark,
            String content, String type) {
        this.key = key;
        this.pointKey = pointKey;
        this.enabled = enabled;
        this.remark = remark;
        this.content = content;
        this.type = type;
    }

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(JSFixedFastJsonLongIdKey pointKey) {
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
        return "JSFixedFastJsonFilterInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
