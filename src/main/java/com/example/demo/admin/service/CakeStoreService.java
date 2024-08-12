package com.example.demo.admin.service;

import com.example.demo.admin.dto.CakeStoreResponse;
import com.example.demo.entity.Cake;
import com.example.demo.entity.CakeStore;

import java.util.List;

public interface CakeStoreService {
    List<CakeStoreResponse> getAllCakesByStore(int id);
    CakeStoreResponse getCakeStoreById(Integer cakeStoreId);
    List<CakeStoreResponse> getCakeForSaleOfStore(Integer storeId, String keyword, Integer pageNumber, Integer pageSize);
    boolean addNewCakeToStore(CakeStore newCakeStore);
    boolean updateCakeStore(CakeStore cakeStoreUpdate);
    Integer getTotalPages(String keyword, Integer pageSize, Integer storeId);
    boolean deleteCakeStore(Integer cakeStoreId, String deletedBy);

}
