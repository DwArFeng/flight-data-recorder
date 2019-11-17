package com.dwarfeng.fdr.impl.handler.validation.bean.dto;

import javax.validation.constraints.Min;

/**
 * 查询分页信息对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationLookupPagingInfo {

    /**
     * 是否启用分页功能。
     */
    private boolean enabled;

    /**
     * 查询的页数。
     */
    @Min(0)
    private int page;

    /**
     * 每页返回的行数。
     */
    @Min(0)
    private int rows;

    public ValidationLookupPagingInfo() {
    }

    public ValidationLookupPagingInfo(boolean enabled, int page, int rows) {
        this.enabled = enabled;
        this.page = page;
        this.rows = rows;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ValidationLookupPagingInfo{" +
                "enabled=" + enabled +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
