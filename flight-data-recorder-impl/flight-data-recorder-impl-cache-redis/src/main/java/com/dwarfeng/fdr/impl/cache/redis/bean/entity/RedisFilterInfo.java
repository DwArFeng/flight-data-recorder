package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Entity;

/**
 * Redis数据点对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RedisFilterInfo implements Entity<RedisUuidKey> {

    @JSONField(name = "key", ordinal = 1)
    private RedisUuidKey key;

    @JSONField(name = "point_key", ordinal = 2)
    private RedisUuidKey pointKey;

    @JSONField(name = "enabled", ordinal = 3)
    private boolean enabled;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    @JSONField(name = "content", ordinal = 5)
    private String content;

    public RedisFilterInfo() {
    }

    @Override
    public RedisUuidKey getKey() {
        return key;
    }

    @Override
    public void setKey(RedisUuidKey key) {
        this.key = key;
    }

    public RedisUuidKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(RedisUuidKey pointKey) {
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
        return "RedisFilterInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
