package com.example.demo.admin.dto;

import com.example.demo.entity.BaseEntity;

import java.math.BigDecimal;

public class OrderDetailDto extends BaseEntity {
    private Integer idCakeStore;

    private String cakeName;

    private String cakeImage;

    private Integer idOrder;

    private Integer quantity;

    private BigDecimal price;

    public Integer getIdCakeStore() {
        return idCakeStore;
    }

    public void setIdCakeStore(Integer idCakeStore) {
        this.idCakeStore = idCakeStore;
    }

    public String getCakeImage() {
        return cakeImage;
    }

    public void setCakeImage(String cakeImage) {
        this.cakeImage = cakeImage;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
