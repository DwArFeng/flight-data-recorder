package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisGuidKey;

import java.io.Serializable;

/**
 * Redis数据点对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RedisTriggerInfo implements Serializable {

    private static final long serialVersionUID = -7563795664602239319L;

    @JSONField(name = "key", ordinal = 1)
    private RedisGuidKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private RedisGuidKey pointKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "content", ordinal = 5)
    private String content;

    public RedisTriggerInfo() {
    }

    public RedisGuidKey getKey() {
        return key;
    }

    public void setKey(RedisGuidKey key) {
        this.key = key;
    }

    public RedisGuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(RedisGuidKey pointKey) {
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
        return "RedisTriggerInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
