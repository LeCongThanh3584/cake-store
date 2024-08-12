package com.example.demo.admin.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CakeResponse {

    private Long id;

    private String code;

    private String name;

    private String category;

    private String image;

    private String createdBy;

    private Timestamp createdAt;

    public CakeResponse(Long id, String code, String name, String category, String image, String createdBy, Timestamp createdAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.category = category;
        this.image = image;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public CakeResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
