package com.example.demo.client.service;

import com.example.demo.entity.Store;

import java.util.List;

public interface StoreService {
    List<Store> GetStores(Integer id);

    List<Store> GetAll();
}
