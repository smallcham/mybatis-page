package org.yxs.plugin.support;

import org.yxs.plugin.interceptor.PageUtil;

/**
 * Created by medusa on 2016/11/1.
 * explain:
 */
public class RowBounds {
    private long nextPage;
    private long pageSize = PageUtil.PAGE_SIZE;
    private long rowCount;
    private Object object;

    public RowBounds(long nextPage) {
        this.nextPage = nextPage;
    }

    public RowBounds(long nextPage, Object object) {
        this.nextPage = nextPage;
        this.object = object;
    }

    public RowBounds(long nextPage, long pageSize) {
        this.nextPage = nextPage;
        this.pageSize = pageSize;
    }

    public RowBounds(long nextPage, long pageSize, Object object) {
        this.nextPage = nextPage;
        this.pageSize = pageSize;
        this.object = object;
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

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public long getRowCount() {
        return rowCount;
    }

    public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
    }
}
