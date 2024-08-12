package com.example.demo.client.dto;

import java.util.List;

public class CakePage {
    Integer categoryId;
    Integer storeId;
    Integer materialId;

    Integer from;
    Integer to;

    String query;
    String sortBy = "expiration_date";
    String direction = "desc";

    public CakePage() {
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        if (direction.equals("asc")) {
            this.direction = direction;
        } else {
            this.direction = "desc";
        }
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        List<String> sortable = List.of("production_date", "min_price", "max_price");

        if (sortable.stream().anyMatch(value -> value.equals(sortBy))) {
            this.sortBy = sortBy;
        } else {
            this.sortBy = "expiration_date";
        }
    }

}
