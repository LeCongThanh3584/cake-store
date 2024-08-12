package com.example.demo.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartViewDto {
    private Integer id;
    private Integer storeId;
    private Integer cakeStoreId;

    private String name;
    private String storeName;
    private String storeAddress;

    private Integer stock;
    private Integer quantity;
    private BigDecimal price;
    private String image;

    private LocalDateTime expirationDate;
    private boolean expired;

    public CartViewDto() {
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        this.expired = LocalDateTime.now().isAfter(expirationDate);
    }

    public boolean isExpired() {
        return expired;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCakeStoreId() {
        return cakeStoreId;
    }

    public void setCakeStoreId(Integer cakeStoreId) {
        this.cakeStoreId = cakeStoreId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
