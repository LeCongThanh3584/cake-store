package com.example.demo.admin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CakeStoreResponse {
    private Integer id;
    private Integer idStore;
    private String storeName;
    private Integer idCake;
    private String cakeName;
    private String codeCake;
    private String imageCake;
    private String categoryName;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime productionDate;
    private LocalDateTime expirationDate;
    private Integer status;

    public Integer getIdStore() {
        return idStore;
    }

    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getIdCake() {
        return idCake;
    }

    public void setIdCake(Integer idCake) {
        this.idCake = idCake;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getProductionDate() {
        return Date.from(this.productionDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpirationDate() {
        return Date.from(this.expirationDate.atZone(ZoneId.systemDefault()).toInstant());
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

    public String getCodeCake() {
        return codeCake;
    }

    public void setCodeCake(String codeCake) {
        this.codeCake = codeCake;
    }

    public String getImageCake() {
        return imageCake;
    }

    public void setImageCake(String imageCake) {
        this.imageCake = imageCake;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CakeStore{" +
                "idStore=" + idStore +
                ", storeName='" + storeName + '\'' +
                ", idCake=" + idCake +
                ", cakeName='" + cakeName + '\'' +
                ", codeCake='" + codeCake + '\'' +
                ", imageCake='" + imageCake + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", productionDate=" + productionDate +
                ", expirationDate=" + expirationDate +
                ", status=" + status +
                '}';
    }
}
