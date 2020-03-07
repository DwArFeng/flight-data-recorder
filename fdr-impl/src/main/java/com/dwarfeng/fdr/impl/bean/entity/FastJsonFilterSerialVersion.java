package com.dwarfeng.fdr.impl.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

public class FastJsonFilterSerialVersion implements Bean {

    private static final long serialVersionUID = 1391485844524166220L;

    @JSONField(name = "key")
    private FastJsonLongIdKey key;

    @JSONField(name = "serial_version")
    private long serialVersion;

    public FastJsonFilterSerialVersion() {
    }

    public FastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonLongIdKey key) {
        this.key = key;
    }

    public long getSerialVersion() {
        return serialVersion;
    }

    public void setSerialVersion(long serialVersion) {
        this.serialVersion = serialVersion;
    }

    @Override
    public String toString() {
        return "FastJsonFilterSerialVersion{" +
                "key=" + key +
                ", serialVersion=" + serialVersion +
                '}';
    }
}
