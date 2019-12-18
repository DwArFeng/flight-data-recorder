package com.dwarfeng.fdr.impl.handler.event.kafka.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.key.Key;

import java.util.Objects;

public class KafkaUuidKey implements Key {

    private static final long serialVersionUID = 4171274999207450938L;

    @JSONField(name = "uuid", ordinal = 1)
    private String uuid;

    public KafkaUuidKey() {
    }

    public KafkaUuidKey(String uuid) {
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
        if (o == null || getClass() != o.getClass()) return false;
        KafkaUuidKey that = (KafkaUuidKey) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "KafkaUuidKey{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
