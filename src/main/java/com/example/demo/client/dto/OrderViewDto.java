package com.example.demo.client.dto;

import com.example.demo.entity.Address;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderViewDto {
    private List<CartViewDto> orderDetails = new ArrayList();

    private Integer id;
    private Integer idStore;
    private Integer idAdmin;
    private Integer idShipper;
    private Integer idUser;
    private String storeName;
    private String storeAddress;

    private String code;
    private String receiver;
    private String phone;
    private String address;
    private BigDecimal totalMoney = BigDecimal.ZERO;

    public OrderViewDto() {
    }

    public void addDetail(CartViewDto viewDto) {
        BigDecimal addMoney = viewDto.getPrice()
                .multiply(BigDecimal.valueOf(viewDto.getQuantity()));
        this.totalMoney = this.totalMoney.add(addMoney);
        this.orderDetails.add(viewDto);
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

    public List<CartViewDto> getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(List<CartViewDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setAddress(Address address) {
        this.address = address.getDistrict() + ", " +
                address.getWard() + ", " +
                address.getProvince();
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
