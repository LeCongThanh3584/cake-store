package com.example.demo.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDetailDto {

    private Integer orderDetailId;

    private String cakeCode;

    private String cakeImage;

    private String name;
    private LocalDateTime productionDate;
    private LocalDateTime expirationDate;
    private BigDecimal price;
    private Long quantity;

    public OrderDetailDto() {
    }

    public OrderDetailDto(Integer orderDetailId, String cakeCode, String cakeImage, String name, LocalDateTime productionDate, LocalDateTime expirationDate, BigDecimal price, Long quantity) {
        this.orderDetailId = orderDetailId;
        this.cakeCode = cakeCode;
        this.cakeImage = cakeImage;
        this.name = name;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getCakeCode() {
        return cakeCode;
    }

    public void setCakeCode(String cakeCode) {
        this.cakeCode = cakeCode;
    }

    public String getCakeImage() {
        return cakeImage;
    }

    public void setCakeImage(String cakeImage) {
        this.cakeImage = cakeImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
