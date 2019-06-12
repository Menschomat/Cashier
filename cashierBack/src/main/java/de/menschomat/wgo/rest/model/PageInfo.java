package de.menschomat.wgo.rest.model;

public class PageInfo {
    public PageInfo(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int pageCount;
}
