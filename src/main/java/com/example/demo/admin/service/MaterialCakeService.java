package com.example.demo.admin.service;

import com.example.demo.admin.dto.MaterialCakeResponse;
import com.example.demo.entity.MaterialCake;

import java.util.List;

public interface MaterialCakeService {

    boolean createBatchMaterCake(List<MaterialCake> materialCakeList);

    List<MaterialCakeResponse> getMaterialCakeByCake(int cakeId);

}
