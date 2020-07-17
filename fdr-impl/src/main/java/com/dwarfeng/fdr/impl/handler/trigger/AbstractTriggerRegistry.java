package com.dwarfeng.fdr.impl.handler.trigger;

import com.dwarfeng.fdr.impl.handler.TriggerMaker;
import com.dwarfeng.fdr.impl.handler.TriggerSupporter;

import java.util.Objects;

/**
 * 抽象触发器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
public abstract class AbstractTriggerRegistry implements TriggerMaker, TriggerSupporter {

    protected String triggerType;

    public AbstractTriggerRegistry() {
    }

    public AbstractTriggerRegistry(String triggerType) {
        this.triggerType = triggerType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(triggerType, type);
    }

    @Override
    public String provideType() {
        return triggerType;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    @Override
    public String toString() {
        return "AbstractTriggerRegistry{" +
                "triggerType='" + triggerType + '\'' +
                '}';
    }
}
