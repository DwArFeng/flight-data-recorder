package com.dwarfeng.fdr.impl.cache.redis.bean.key;

import java.io.Serializable;
import java.util.Objects;

public class RedisGuidKey implements Serializable {

    private static final long serialVersionUID = -1525554631432081202L;

    private long guid;

    public RedisGuidKey() {
    }

    public RedisGuidKey(long guid) {
        this.guid = guid;
    }

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedisGuidKey that = (RedisGuidKey) o;
        return guid == that.guid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }

    @Override
    public String toString() {
        return "RedisGuidKey{" +
                "guid=" + guid +
                '}';
    }
}
