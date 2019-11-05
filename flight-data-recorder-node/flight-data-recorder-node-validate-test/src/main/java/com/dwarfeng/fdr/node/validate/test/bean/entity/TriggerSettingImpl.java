package com.dwarfeng.fdr.node.validate.test.bean.entity;

import com.dwarfeng.fdr.node.validate.test.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;

public class TriggerSettingImpl implements TriggerSetting {

    private static final long serialVersionUID = -3656468063489794952L;

    private NameKeyImpl key;

    private String triggerData;
    private String remark;

    public TriggerSettingImpl() {
    }

    public TriggerSettingImpl(NameKeyImpl key, String triggerData, String remark) {
        this.key = key;
        this.triggerData = triggerData;
        this.remark = remark;
    }

    @Override
    public NameKeyImpl getKey() {
        return key;
    }

    public void setKey(NameKeyImpl key) {
        this.key = key;
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
    public String toString() {
        return "TriggerSettingImpl{" +
                "key=" + key +
                ", triggerData='" + triggerData + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
