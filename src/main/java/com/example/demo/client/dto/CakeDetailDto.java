package com.example.demo.client.dto;

import java.util.ArrayList;
import java.util.List;

public class CakeDetailDto {
    private final List<CakeStoreDto> cakeStores = new ArrayList<>();
    private Integer id;
    private Long idCategory;
    private String code;
    private String name;
    private String image;
    private String category;
    private String size;
    private Integer height;
    private Integer length;
    private Integer weight;
    private String color;
    private String description;
    private Integer status;

    public CakeDetailDto() {
    }

    public void addStore(CakeStoreDto cakeStore) {
        this.cakeStores.add(cakeStore);
    }

    public List<CakeStoreDto> getCakeStores() {
        return cakeStores;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
