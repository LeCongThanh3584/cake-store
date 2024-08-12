package com.example.demo.client.dto;

import com.example.demo.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CakeStoreDto extends BaseEntity {
    private final List<CakeDetailDto> cakes = new ArrayList<>();
    private Integer idStore;
    private Integer idCake;
    private String storeName;
    private String cakeName;

    private BigDecimal price;
    private int quantity;
    private Integer status;

    private LocalDateTime productionDate;
    private LocalDateTime expirationDate;

    public CakeStoreDto() {
    }

    public List<CakeDetailDto> getCakes() {
        return cakes;
    }

    public void addCake(CakeDetailDto cake) {
        this.cakes.add(cake);
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
