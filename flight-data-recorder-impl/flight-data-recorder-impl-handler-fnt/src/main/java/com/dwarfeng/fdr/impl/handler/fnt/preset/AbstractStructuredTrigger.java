package com.dwarfeng.fdr.impl.handler.fnt.preset;

import com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredTrigger;
import com.dwarfeng.fdr.stack.exception.TriggerException;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public abstract class AbstractStructuredTrigger implements StructuredTrigger {

    protected Object config;
    protected String pointUuid;
    protected String triggerUuid;

    public AbstractStructuredTrigger() {
    }

    @Override
    public void applyConfig(Object config) throws TriggerException {
        this.config = config;
    }

    @Override
    public void applyPointUuid(String pointUuid) throws TriggerException {
        this.pointUuid = pointUuid;
    }

    @Override
    public void applyTriggerUuid(String triggerUuid) throws TriggerException {
        this.triggerUuid = triggerUuid;
    }
}
