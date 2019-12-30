package com.dwarfeng.fdr.impl.handler.fnt.struct;

import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.handler.Trigger;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface StructuredTrigger extends Trigger {

    void applyConfig(Object config) throws TriggerException;

    void applyPointGuid(long pointGuid) throws TriggerException;

    void applyTriggerGuid(long triggerGuid) throws TriggerException;
}
