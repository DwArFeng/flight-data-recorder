package com.dwarfeng.fdr.sdk.validation.bean.dto;

import com.dwarfeng.fdr.sdk.validation.group.Insert;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.Default;
import java.io.Serializable;

public class ValidationLookupPagingInfo implements Serializable {

    private static final long serialVersionUID = -9054439470560202956L;

    private boolean paging;

    @PositiveOrZero(groups = {Default.class, Insert.class})
    private int page;

    @PositiveOrZero(groups = {Default.class, Insert.class})
    private int rows;

    public ValidationLookupPagingInfo() {
    }

    public boolean isPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
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
        return "ApiLookupPagingInfo{" +
                "paging=" + paging +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
