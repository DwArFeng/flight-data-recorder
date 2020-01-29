package com.dwarfeng.fdr.impl.handler.fnt.preset;

import com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredTrigger;
import com.dwarfeng.fdr.stack.exception.TriggerException;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public abstract class AbstractStructuredTrigger implements StructuredTrigger {

    protected Object config;
    protected long pointLongId;
    protected long triggerLongIdKey;

    public AbstractStructuredTrigger() {
    }

    @Override
    public void applyConfig(Object config) throws TriggerException {
        this.config = config;
    }

    @Override
    public void applyPointGuid(long pointGuid) throws TriggerException {
        this.pointLongId = pointGuid;
    }

    @Override
    public void applyTriggerGuid(long triggerGuid) throws TriggerException {
        this.triggerLongIdKey = triggerGuid;
    }
}
