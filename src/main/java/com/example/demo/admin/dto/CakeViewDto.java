package com.example.demo.admin.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CakeViewDto {
    private Integer id;
    private String name;
    private String image;
    private String minPrice;
    private String maxPrice;

    private String storeName;
    private Integer quantity;
    private Integer revenue;
    private LocalDateTime productionDate;
    private LocalDateTime expirationDate;

    public CakeViewDto() {

    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Date getProductionDate() {
        return Date.from(productionDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpirationDate() {
        return Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}
