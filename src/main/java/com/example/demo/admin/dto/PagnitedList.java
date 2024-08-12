package com.example.demo.admin.dto;

import java.util.List;

public class PagnitedList<T> {
    private int pageNo;
    public int pageSizeFact;
    private int pageSize;
    private int total;
    private int totalPages;
    private List<T> list;
    private boolean HasPreviousPage ;
    private boolean HasNextPage ;
    private int FirstItemIndex ;
    private int LastItemIndex ;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSizeFact() {
        return this.list.size();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isHasPreviousPage() {
        return (this.pageNo > 1);
    }

    public boolean isHasNextPage() {
        return (this.pageNo < this.totalPages);
    }

    public int getFirstItemIndex() {
        return (this.pageNo - 1) * this.pageSize + 1;
    }

    public int getLastItemIndex() {
        return Math.min(this.pageNo * this.pageSize, this.totalPages);
    }
}
