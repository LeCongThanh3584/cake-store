package com.example.demo.entity;

public class ProductCart extends BaseEntity {
    private Integer idUser;

    private Integer idCakeStore;

    private Integer quantity;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdCakeStore() {
        return idCakeStore;
    }

    public void setIdCakeStore(Integer idCakeStore) {
        this.idCakeStore = idCakeStore;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
