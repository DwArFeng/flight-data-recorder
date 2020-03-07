package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 生效触发器元数据。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class EnabledTriggerMeta implements Dto {

    private static final long serialVersionUID = -7341079294921709546L;

    private long serialVersion;
    private List<TriggerInfo> triggerInfos;

    public EnabledTriggerMeta() {
    }

    public EnabledTriggerMeta(long serialVersion, List<TriggerInfo> triggerInfos) {
        this.serialVersion = serialVersion;
        this.triggerInfos = triggerInfos;
    }

    public long getSerialVersion() {
        return serialVersion;
    }

    public void setSerialVersion(long serialVersion) {
        this.serialVersion = serialVersion;
    }

    public List<TriggerInfo> getTriggerInfos() {
        return triggerInfos;
    }

    public void setTriggerInfos(List<TriggerInfo> triggerInfos) {
        this.triggerInfos = triggerInfos;
    }

    @Override
    public String toString() {
        return "EnabledTriggerMeta{" +
                "serialVersion=" + serialVersion +
                ", triggerInfos=" + triggerInfos +
                '}';
    }
}
