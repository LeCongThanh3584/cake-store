package com.example.demo.client.service.impl;

import com.example.demo.client.dao.StoreDao;
import com.example.demo.client.service.StoreService;
import com.example.demo.entity.Store;

import java.util.List;

public class StoreServiceImpl implements StoreService {
    private final StoreDao storeDao = new StoreDao();

    @Override
    public List<Store> GetStores(Integer id) {
        return storeDao.GetStores(id);
    }

    @Override
    public List<Store> GetAll() {
        return storeDao.GetAll();
    }
}
