package com.example.demo.admin.dto;

import com.example.demo.util.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class OrderChangeDto {
    private Integer id;
    private String storeName;
    private String code;
    private String address;
    private String receiver;
    private Integer status;
    private BigDecimal totalMoney;


    private LocalDateTime createdAt;
    private String createdBy;
    private String timeAgo;

    public OrderChangeDto() {
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.timeAgo = DateUtil.timeSince(createdAt);
        this.createdAt = createdAt;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
