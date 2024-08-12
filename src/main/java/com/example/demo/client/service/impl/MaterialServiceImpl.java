package com.example.demo.client.service.impl;

import com.example.demo.client.dao.MaterialDao;
import com.example.demo.client.service.MaterialService;
import com.example.demo.entity.Material;

import java.util.List;

public class MaterialServiceImpl implements MaterialService {
    MaterialDao materialDao = new MaterialDao();

    @Override
    public List<Material> getAll() {
        return materialDao.getAll();
    }
}
