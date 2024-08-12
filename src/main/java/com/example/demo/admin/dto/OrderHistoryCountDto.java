package com.example.demo.admin.dto;

public class OrderHistoryCountDto {
    private Integer status;
    private Integer count;

    public OrderHistoryCountDto(Integer status) {
        this.status = status;
        this.count = 0;
    }

    public OrderHistoryCountDto(Integer status, Integer count) {
        this.status = status;
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
