package com.dwarfeng.fdr.impl.handler.fnt.preset;

import com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredFilter;
import com.dwarfeng.fdr.stack.exception.FilterException;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public abstract class AbstractStructuredFilter implements StructuredFilter {

    protected Object config;
    protected String pointUuid;
    protected String filterUuid;

    public AbstractStructuredFilter() {
    }

    @Override
    public void applyConfig(Object config) throws FilterException {
        this.config = config;
    }

    @Override
    public void applyPointUuid(String pointUuid) throws FilterException {
        this.pointUuid = pointUuid;
    }

    @Override
    public void applyFilterUuid(String filterUuid) throws FilterException {
        this.filterUuid = filterUuid;
    }
}
