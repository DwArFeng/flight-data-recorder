package com.dwarfeng.fdr.impl.bean.validate.bean.entity;

import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class TriggerSettingImpl implements TriggerSetting {

    private static final long serialVersionUID = 1851383230321096992L;

    private UuidKey key;

    private String name;

    private String triggerData;

    private String remark;

    private boolean triggerBroadcast;

    private boolean triggerPersistence;

    public TriggerSettingImpl() {
    }

    public TriggerSettingImpl(UuidKey key, String name, String triggerData, String remark, boolean triggerBroadcast, boolean triggerPersistence) {
        this.key = key;
        this.name = name;
        this.triggerData = triggerData;
        this.remark = remark;
        this.triggerBroadcast = triggerBroadcast;
        this.triggerPersistence = triggerPersistence;
    }

    @Override
    public UuidKey getKey() {
        return key;
    }

    public void setKey(UuidKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTriggerData() {
        return triggerData;
    }

    public void setTriggerData(String triggerData) {
        this.triggerData = triggerData;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean isTriggerBroadcast() {
        return triggerBroadcast;
    }

    public void setTriggerBroadcast(boolean triggerBroadcast) {
        this.triggerBroadcast = triggerBroadcast;
    }

    @Override
    public boolean isTriggerPersistence() {
        return triggerPersistence;
    }

    public void setTriggerPersistence(boolean triggerPersistence) {
        this.triggerPersistence = triggerPersistence;
    }

    @Override
    public String toString() {
        return "TriggerSettingImpl{" +
                "key=" + key +
                ", triggerData='" + triggerData + '\'' +
                ", remark='" + remark + '\'' +
                ", triggerBroadcast=" + triggerBroadcast +
                ", triggerPersistence=" + triggerPersistence +
                '}';
    }
}
