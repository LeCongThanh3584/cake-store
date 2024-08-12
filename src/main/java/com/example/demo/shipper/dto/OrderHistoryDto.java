package com.example.demo.shipper.dto;

import com.example.demo.entity.BaseEntity;

public class OrderHistoryDto extends BaseEntity {
    private Integer status;
    private String description;
    private String statusName;

    String toStatusName() {
        switch (status) {
            case 0:
                return "PENDING";
            case 1:
                return "CONFIRMED";
            case 2:
                return "DELIVERING";
            case 3:
                return "DELIVERED";
            case 4:
                return "CANCELLED";
            default:
                return "UNKNOWN";
        }
    }

    public String getStatusName() {
        return statusName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
        this.statusName = toStatusName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
