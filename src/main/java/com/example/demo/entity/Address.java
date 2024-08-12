package com.example.demo.entity;

public class Address extends BaseEntity {
    private Integer idUser;

    private String name;

    private String province;

    private String district;

    private String ward;

    private String phone;

    public Address() {
    }

    public Address(Integer idUser, String name, String province, String district, String ward, String phone) {
        this.idUser = idUser;
        this.name = name;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", ward='" + ward + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Address(String name, String province, String district, String ward, String phone) {
        this.name = name;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
    }
}
