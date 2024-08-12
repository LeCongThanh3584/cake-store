package com.example.demo.entity;

import jakarta.servlet.http.Part;

import java.time.LocalDateTime;

public class Cake extends BaseEntity {

    private Long idCategory;

    private String code;

    private String name;

    private Integer weight;

    private String image;

    private String color;

    private String size;

    private Integer height;

    private Part part;

    private Integer length;

    private String description;

    private Integer status;

    public Cake() {
    }

    public Cake(Integer id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, LocalDateTime deletedAt, String deletedBy, Long idCategory, String code, String name, Integer weight, String image, String color, String size, Integer height, Part part, Integer length, String description, Integer status) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deletedAt, deletedBy);
        this.idCategory = idCategory;
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.image = image;
        this.color = color;
        this.size = size;
        this.height = height;
        this.part = part;
        this.length = length;
        this.description = description;
        this.status = status;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
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

    @Override
    public String toString() {
        return "Cake{" +
                "idCategory=" + idCategory +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", image='" + image + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", height=" + height +
                ", length=" + length +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
