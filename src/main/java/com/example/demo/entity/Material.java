package com.example.demo.entity;

import java.time.LocalDateTime;

public class Material extends BaseEntity {

    private String code;

    private String name;

    private String description;

    public Material() {
    }

    public Material(Integer id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy, LocalDateTime deletedAt, String deletedBy, String code, String name, String description) {
        super(id, createdAt, createdBy, updatedAt, updatedBy, deletedAt, deletedBy);
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Material(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
