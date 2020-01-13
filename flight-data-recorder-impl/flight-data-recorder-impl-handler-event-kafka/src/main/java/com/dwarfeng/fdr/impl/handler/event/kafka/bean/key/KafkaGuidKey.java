package com.dwarfeng.fdr.impl.handler.event.kafka.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.key.Key;

import java.util.Objects;

public class KafkaGuidKey implements Key {

    private static final long serialVersionUID = 7385650805164463866L;

    @JSONField(name = "guid", ordinal = 1)
    private long guid;

    public KafkaGuidKey() {
    }

    public KafkaGuidKey(long guid) {
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
        KafkaGuidKey that = (KafkaGuidKey) o;
        return guid == that.guid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }

    @Override
    public String toString() {
        return "KafkaGuidKey{" +
                "guid=" + guid +
                '}';
    }
}
