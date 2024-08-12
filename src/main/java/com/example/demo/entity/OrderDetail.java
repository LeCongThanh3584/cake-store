package com.example.demo.entity;

import java.math.BigDecimal;

public class OrderDetail extends BaseEntity {
    private Integer idCakeStore;

    private Integer idOrder;

    private Integer quantity;

    private BigDecimal price;

    public Integer getIdCakeStore() {
        return idCakeStore;
    }

    public void setIdCakeStore(Integer idCakeStore) {
        this.idCakeStore = idCakeStore;
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
