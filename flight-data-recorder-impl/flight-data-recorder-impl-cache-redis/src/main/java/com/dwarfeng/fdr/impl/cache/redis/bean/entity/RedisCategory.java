package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.cache.redis.bean.key.RedisGuidKey;

import java.io.Serializable;

/**
 * Redis分类对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RedisCategory implements Serializable {

    private static final long serialVersionUID = -8187976428762830248L;

    @JSONField(name = "key", ordinal = 1)
    private RedisGuidKey key;

    @JSONField(name = "parent_key", ordinal = 2)
    private RedisGuidKey parentKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "remark", ordinal = 4)
    private String remark;

    public RedisCategory() {
    }

    public RedisCategory(RedisGuidKey key, RedisGuidKey parentKey, String name, String remark) {
        this.key = key;
        this.parentKey = parentKey;
        this.name = name;
        this.remark = remark;
    }

    public RedisGuidKey getKey() {
        return key;
    }

    public void setKey(RedisGuidKey key) {
        this.key = key;
    }

    public RedisGuidKey getParentKey() {
        return parentKey;
    }

    public void setParentKey(RedisGuidKey parentKey) {
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
