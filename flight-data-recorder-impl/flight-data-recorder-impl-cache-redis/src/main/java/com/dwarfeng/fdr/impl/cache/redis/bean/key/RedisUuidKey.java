package com.dwarfeng.fdr.impl.cache.redis.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Objects;

public class RedisUuidKey implements UuidKey {

    private static final long serialVersionUID = 5709087570416541857L;

    public RedisUuidKey() {
    }

    @JSONField(name = "uuid", ordinal = 1)
    private String uuid;

    public RedisUuidKey(String uuid) {
        this.uuid = uuid;
    }

    public static RedisUuidKey of(String uuid) {
        return new RedisUuidKey(uuid);
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o)) return false;
        if (!(o instanceof UuidKey)) return false;

        UuidKey that = (UuidKey) o;

        return Objects.equals(this.getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UuidKeyImpl [uuid=" + uuid + "]";
    }


}
