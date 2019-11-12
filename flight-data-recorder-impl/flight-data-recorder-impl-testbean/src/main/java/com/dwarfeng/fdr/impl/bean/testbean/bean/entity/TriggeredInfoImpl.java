package com.dwarfeng.fdr.impl.bean.testbean.bean.entity;

import com.dwarfeng.fdr.impl.bean.testbean.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredInfo;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class TriggeredInfoImpl implements TriggeredInfo {

    private static final long serialVersionUID = -3905788970847882443L;

    private UuidKeyImpl key;

    public TriggeredInfoImpl() {
    }

    public TriggeredInfoImpl(UuidKeyImpl key) {
        this.key = key;
    }

    @Override
    public UuidKeyImpl getKey() {
        return key;
    }

    public void setKey(UuidKeyImpl key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TriggedInfoImpl{" +
                "key=" + key +
                '}';
    }
}
