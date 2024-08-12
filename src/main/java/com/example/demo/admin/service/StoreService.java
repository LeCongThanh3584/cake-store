package com.example.demo.admin.service;

import com.example.demo.entity.Store;
import jakarta.servlet.http.Part;

import java.util.List;

public interface StoreService {
    List<Store> getAllStores();
    List<Store> getAllStoresPaginationSearch(Integer pageNumber, Integer pageSize, String keyword);
    boolean addNewStore(Store newStore, Part imageStore);
    boolean updateStore(Store storeUpdate, Part imageStore);
    boolean deleteStore(int storeId, String deleted_by);
    Store getStoreById(int storeId);
    Integer getTotalPages(String keyword, int pageSize);

}
