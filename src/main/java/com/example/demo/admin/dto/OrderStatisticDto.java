package com.example.demo.admin.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class OrderStatisticDto {
    private String statTime;
    private Integer revenue;
    private Integer sale;
    private Integer customer;

    public OrderStatisticDto() {
    }

    public String getStatTime() {
        return statTime;
    }

    public void setStatTime(Date date) {
        this.statTime = date.toString();
    }

    public void setStatTime(Integer hour) {
        this.statTime = LocalDateTime.now().toLocalDate().atStartOfDay().withHour(hour).toString();
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }
}
