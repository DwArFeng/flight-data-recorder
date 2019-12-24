package com.dwarfeng.fdr.stack.bean.dto;

/**
 * 查询分页信息对象。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class LookupPagingInfo implements Dto {

    public static final LookupPagingInfo LOOKUP_ALL = new LookupPagingInfo(false, 0, 0);

    private static final long serialVersionUID = -5393564201402936618L;

    /**
     * 是否分页。
     */
    private boolean paging;

    /**
     * 查询的页数。
     */
    private int page;
    /**
     * 每页返回的行数。
     */
    private int rows;

    public LookupPagingInfo() {
    }

    public LookupPagingInfo(boolean paging, int page, int rows) {
        this.paging = paging;
        this.page = page;
        this.rows = rows;
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
        return "LookupPagingInfo{" +
                "paging=" + paging +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}
