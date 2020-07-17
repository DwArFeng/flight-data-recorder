package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.impl.handler.FilterMaker;
import com.dwarfeng.fdr.impl.handler.FilterSupporter;

import java.util.Objects;

/**
 * 抽象过滤器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
public abstract class AbstractFilterRegistry implements FilterMaker, FilterSupporter {

    protected String filterType;

    public AbstractFilterRegistry() {
    }

    public AbstractFilterRegistry(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(filterType, type);
    }

    @Override
    public String provideType() {
        return filterType;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    @Override
    public String toString() {
        return "AbstractFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }
}
