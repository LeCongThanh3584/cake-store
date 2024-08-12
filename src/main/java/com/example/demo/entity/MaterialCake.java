package com.example.demo.entity;


import java.time.LocalDateTime;

public class MaterialCake extends BaseEntity {
    private Integer idMaterial;

    private Integer idCake;

    private String description;

    private Integer weight;

    public MaterialCake(Integer idMaterial, Integer idCake, String description, Integer weight) {
        this.idMaterial = idMaterial;
        this.idCake = idCake;
        this.description = description;
        this.weight = weight;
    }

    public MaterialCake() {
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Integer getIdCake() {
        return idCake;
    }

    public void setIdCake(Integer idCake) {
        this.idCake = idCake;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
