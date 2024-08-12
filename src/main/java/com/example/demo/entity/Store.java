package com.example.demo.entity;

public class Store extends BaseEntity {

    private String code;

    private String name;

    private String image;

    private String address;

    private String phone;

    private Integer status;

    public Store(Integer id) {
        super(id);
    }

    public Store() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Store{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }
}
