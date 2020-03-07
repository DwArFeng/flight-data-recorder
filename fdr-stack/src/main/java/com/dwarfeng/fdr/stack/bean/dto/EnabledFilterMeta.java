package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;

/**
 * 生效过滤器元数据。
 *
 * @author DwArFeng
 * @since 1.1.0.a
 */
public class EnabledFilterMeta implements Dto {

    private static final long serialVersionUID = -4809955409087301826L;

    private long serialVersion;
    private List<FilterInfo> filterInfos;

    public EnabledFilterMeta() {
    }

    public EnabledFilterMeta(long serialVersion, List<FilterInfo> filterInfos) {
        this.serialVersion = serialVersion;
        this.filterInfos = filterInfos;
    }

    public long getSerialVersion() {
        return serialVersion;
    }

    public void setSerialVersion(long serialVersion) {
        this.serialVersion = serialVersion;
    }

    public List<FilterInfo> getFilterInfos() {
        return filterInfos;
    }

    public void setFilterInfos(List<FilterInfo> filterInfos) {
        this.filterInfos = filterInfos;
    }

    @Override
    public String toString() {
        return "EnabledFilterMeta{" +
                "serialVersion=" + serialVersion +
                ", filterInfos=" + filterInfos +
                '}';
    }
}
