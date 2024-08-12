package com.example.demo.admin.dto;

public class MaterialCakeResponse {

    private Integer id;

    private Integer idMaterial;

    private String code;

    private String name;

    private Integer weight;

    private String description;

    public MaterialCakeResponse(Integer id, Integer idMaterial, String code, String name, Integer weight, String description) {
        this.id = id;
        this.idMaterial = idMaterial;
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.description = description;
    }

    public MaterialCakeResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }
}
