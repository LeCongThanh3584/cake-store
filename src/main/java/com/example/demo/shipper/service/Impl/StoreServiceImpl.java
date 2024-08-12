package com.example.demo.shipper.service.Impl;

import com.example.demo.entity.Store;
import com.example.demo.shipper.dao.StoreDao;
import com.example.demo.shipper.service.StoreService;

import java.util.List;

public class StoreServiceImpl implements StoreService {
    private final StoreDao storeDao = new StoreDao();

    @Override
    public List<Store> GetStores() {
        return storeDao.GetStores();
    }
}
