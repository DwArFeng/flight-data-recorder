package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Entity;

/**
 * Redis分类对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RedisCategory implements Entity<RedisUuidKey> {

    private static final long serialVersionUID = -8187976428762830248L;

    @JSONField(name = "key", ordinal = 1)
    private RedisUuidKey key;

    @JSONField(name = "parent_key", ordinal = 2)
    private RedisUuidKey parentKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public RedisCategory() {
    }

    public RedisCategory(RedisUuidKey key, RedisUuidKey parentKey, String name, String remark) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
    }

    public RedisUuidKey getKey() {
        return key;
    }

    public void setKey(RedisUuidKey key) {
        this.key = key;
    }

    public RedisUuidKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(RedisUuidKey parentKey) {
        this.parentKey = parentKey;
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

    @Override
    public String toString() {
        return "RedisCategory{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
