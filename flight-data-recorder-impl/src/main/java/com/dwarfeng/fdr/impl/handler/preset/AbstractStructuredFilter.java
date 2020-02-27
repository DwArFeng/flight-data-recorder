package com.dwarfeng.fdr.impl.handler.preset;

import com.dwarfeng.fdr.impl.handler.struct.StructuredFilter;
import com.dwarfeng.fdr.stack.exception.FilterException;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public abstract class AbstractStructuredFilter implements StructuredFilter {

    protected Object config;
    protected long pointLongId;
    protected long filterLongId;

    public AbstractStructuredFilter() {
    }

    @Override
    public void applyConfig(Object config) throws FilterException {
        this.config = config;
    }

    @Override
    public void applyPointGuid(long pointGuid) throws FilterException {
        this.pointLongId = pointGuid;
    }

    @Override
    public void applyFilterGuid(long filterGuid) throws FilterException {
        this.filterLongId = filterGuid;
    }
}
