package com.dwarfeng.fdr.impl.bean.validate.bean.entity;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredInfo;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class TriggeredInfoImpl implements TriggeredInfo {

    private static final long serialVersionUID = -3905788970847882443L;

    private UuidKey key;

    public TriggeredInfoImpl() {
    }

    public TriggeredInfoImpl(UuidKey key) {
        this.key = key;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    public void setKey(UuidKey key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TriggedInfoImpl{" +
                "key=" + key +
                '}';
    }
}
