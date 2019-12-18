package com.dwarfeng.fdr.impl.handler.event.kafka.handler;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.EventException;
import com.dwarfeng.fdr.stack.handler.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventHandlerImpl implements EventHandler {

    @Autowired
    private EventHandlerDelegate delegate;

    @Override
    public void fireDataTriggered(TriggeredValue triggeredValue) throws EventException {
        delegate.fireDataTriggered(triggeredValue);
    }
}
