package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 过滤器序列版本。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FilterSerialVersion implements Entity<LongIdKey> {

    private static final long serialVersionUID = 8425994693761873941L;

    private LongIdKey key;
    private long serialVersion;

    public FilterSerialVersion() {
    }

    public FilterSerialVersion(LongIdKey key, long serialVersion) {
        this.key = key;
        this.serialVersion = serialVersion;
    }

    @Override
    public LongIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(LongIdKey key) {
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
        return "FilterSerialVersion{" +
                "key=" + key +
                ", serialVersion=" + serialVersion +
                '}';
    }
}
