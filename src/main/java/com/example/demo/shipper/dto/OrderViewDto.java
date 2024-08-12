package com.example.demo.shipper.dto;

import com.example.demo.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class OrderViewDto extends BaseEntity {
    private Integer idStore;
    private Integer idAdmin;
    private Integer idShipper;
    private Integer idUser;
    private Integer status;
    private String username;
    private String storeName;
    private String storeAddress;
    private String code;
    private String receiver;
    private String phone;
    private String address;
    private BigDecimal totalMoney;
    private List<OrderHistoryDto> orderHistory;
    private List<OrderDetailDto> orderDetails;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
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

    public List<OrderDetailDto> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDto> orderDetail) {
        this.orderDetails = orderDetail;
    }

    public List<OrderHistoryDto> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistoryDto> orderHistory) {
        this.orderHistory = orderHistory;
        this.status = orderHistory.stream()
                .max(Comparator.comparing(OrderHistoryDto::getStatus))
                .get().getStatus();
    }

    public Integer getIdStore() {
        return idStore;
    }

    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Integer getIdShipper() {
        return idShipper;
    }

    public void setIdShipper(Integer idShipper) {
        this.idShipper = idShipper;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
