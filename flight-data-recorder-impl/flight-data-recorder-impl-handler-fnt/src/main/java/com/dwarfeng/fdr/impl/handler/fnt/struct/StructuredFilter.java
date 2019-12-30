package com.dwarfeng.fdr.impl.handler.fnt.struct;

import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.handler.Filter;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface StructuredFilter extends Filter {

    void applyConfig(Object config) throws FilterException;

    void applyPointGuid(long pointGuid) throws FilterException;

    void applyFilterGuid(long filterGuid) throws FilterException;
}
