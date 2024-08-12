package com.example.demo.client.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CakeViewDto {
    private Integer id;
    private String name;
    private String image;
    private String category;
    private String minPrice;
    private String maxPrice;

    private LocalDateTime productionDate;
    private LocalDateTime expirationDate;

    public CakeViewDto() {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
