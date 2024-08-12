package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CakeStore extends BaseEntity {
    private Integer idStore;

    private Integer idCake;

    private BigDecimal price;

    private Integer quantity;

    private LocalDateTime productionDate;

    private LocalDateTime expirationDate;

    private Integer status;

    public CakeStore() {
    }

    public Integer getIdStore() {
        return idStore;
    }

    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }

    public Integer getIdCake() {
        return idCake;
    }

    public void setIdCake(Integer idCake) {
        this.idCake = idCake;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getProductionDate() {
        return this.productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDateTime getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CakeStore{" +
                "idStore=" + idStore +
                ", idCake=" + idCake +
                ", price=" + price +
                ", quantity=" + quantity +
                ", productionDate=" + productionDate +
                ", expirationDate=" + expirationDate +
                ", status=" + status +
                '}';
    }
}
