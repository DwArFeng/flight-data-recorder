package com.dwarfeng.fdr.impl.cache.redis.bean.key;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RedisUuidKey implements Serializable {

    private static final long serialVersionUID = -1523467630414290246L;

    @JSONField(name = "uuid", ordinal = 1)
    private String uuid;

    public RedisUuidKey() {
    }

    public RedisUuidKey(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RedisUuidKey)) return false;

        RedisUuidKey that = (RedisUuidKey) o;

        return getUuid() != null ? getUuid().equals(that.getUuid()) : that.getUuid() == null;
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RedisUuidKey{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
