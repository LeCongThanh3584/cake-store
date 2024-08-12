package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.MaterialCakeDao;
import com.example.demo.admin.dto.MaterialCakeResponse;
import com.example.demo.admin.service.MaterialCakeService;
import com.example.demo.entity.MaterialCake;

import java.util.List;

public class MaterialCakeServiceImpl implements MaterialCakeService {

    private MaterialCakeDao materialCakeDao = new MaterialCakeDao();

    @Override
    public boolean createBatchMaterCake(List<MaterialCake> materialCakeList) {
        return materialCakeDao.createListMaterial(materialCakeList);
    }

    @Override
    public List<MaterialCakeResponse> getMaterialCakeByCake(int cakeId) {
        return materialCakeDao.getCakeMaterialByCake(cakeId);
    }
}
