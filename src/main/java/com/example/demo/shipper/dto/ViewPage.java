package com.example.demo.shipper.dto;

public class ViewPage {
    int page;
    int storeId;
    int pageCount;
    int pageSize = 3;
    String query;

    public ViewPage(int page, int storeId, int pageCount, String query) {
        this.page = page;
        this.storeId = storeId;
        this.query = query;
        this.pageCount = pageCount;
    }

    public ViewPage(int page, int storeId, int pageCount, int pageSize, String query) {
        this.page = page;
        this.storeId = storeId;
        this.pageCount = pageCount;
        this.pageSize = pageSize;
        this.query = query;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
