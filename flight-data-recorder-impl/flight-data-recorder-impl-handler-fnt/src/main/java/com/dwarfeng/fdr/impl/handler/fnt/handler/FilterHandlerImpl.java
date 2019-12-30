package com.dwarfeng.fdr.impl.handler.fnt.handler;

import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterHandlerImpl implements FilterHandler {

    @Autowired
    private FilterHandlerDelegate delegate;

    @Override
    public Filter make(long pointGuid, long filterGuid, String content) throws FilterException {
        return delegate.make(pointGuid, filterGuid, content);
    }
}
