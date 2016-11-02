package org.yxs.plugin.page.support;

import org.yxs.plugin.page.interceptor.PageUtil;

/**
 * Created by medusa on 2016/11/1.
 * explain:
 */

public class RowBounds {
    private long nextPage;
    private long pageSize = PageUtil.PAGE_SIZE;
    private long rowCount;
    private Object v;

    public RowBounds(long nextPage) {
        this.nextPage = nextPage;
    }

    public RowBounds(long nextPage, Object v) {
        this.nextPage = nextPage;
        this.v = v;
    }

    public RowBounds(long nextPage, long pageSize) {
        this.nextPage = nextPage;
        this.pageSize = pageSize;
    }

    public RowBounds(long nextPage, long pageSize, Object v) {
        this.nextPage = nextPage;
        this.pageSize = pageSize;
        this.v = v;
    }

    public long getNextPage() {
        return nextPage;
    }

    public void setNextPage(long nextPage) {
        this.nextPage = nextPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public Object getV() {
        return v;
    }

    public void setV(Object v) {
        this.v = v;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }
}
