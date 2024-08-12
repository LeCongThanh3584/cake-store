package com.example.demo.client.dto;

import com.example.demo.entity.BaseEntity;

import java.math.BigDecimal;

public class OrderDto extends BaseEntity {

    private Long orderId;

    private String storeName;

    private Integer idAdmin;

    private String adminName;

    private Integer idShipper;

    private String shipperName;

    private Integer idUser;

    private String userName;

    private String code;

    private String reciver;

    private Integer status;

    private String address;

    private String phone;

    private BigDecimal totalMoney;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Integer getIdShipper() {
        return idShipper;
    }

    public void setIdShipper(Integer idShipper) {
        this.idShipper = idShipper;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderDto() {
    }

    public OrderDto(Long orderId, String storeName, Integer idAdmin, String adminName, Integer idShipper, String shipperName, Integer idUser, String userName, String code, String reciver, Integer status, String address, String phone, BigDecimal totalMoney) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.idAdmin = idAdmin;
        this.adminName = adminName;
        this.idShipper = idShipper;
        this.shipperName = shipperName;
        this.idUser = idUser;
        this.userName = userName;
        this.code = code;
        this.reciver = reciver;
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.totalMoney = totalMoney;
    }
}
