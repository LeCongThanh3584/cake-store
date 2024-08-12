package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.CakeDao;
import com.example.demo.admin.dao.CakeStoreDao;
import com.example.demo.admin.dao.StoreDao;
import com.example.demo.admin.dto.CakeStoreResponse;
import com.example.demo.admin.service.CakeStoreService;
import com.example.demo.entity.Cake;
import com.example.demo.entity.CakeStore;
import com.example.demo.entity.Store;

import java.util.List;

public class CakeStoreServiceImpl implements CakeStoreService {
    private CakeStoreDao cakeStoreDao;

    private StoreDao storeDao;

    private CakeDao cakeDao;

    public CakeStoreServiceImpl() {
        cakeStoreDao = new CakeStoreDao();
        storeDao = new StoreDao();
        cakeDao = new CakeDao();
    }

    @Override
    public List<CakeStoreResponse> getAllCakesByStore(int id) {
        return cakeStoreDao.getAllCakeByStore(id);
    }

    @Override
    public CakeStoreResponse getCakeStoreById(Integer cakeStoreId) {
        return cakeStoreDao.getCakeStoreById(cakeStoreId);
    }

    @Override
    public List<CakeStoreResponse> getCakeForSaleOfStore(Integer storeId, String keyword, Integer pageNumber, Integer pageSize) {
        Store existStore = storeDao.getStoreById(storeId);
        if(existStore == null) {  //Store không tồn tại
            return null;
        }

        return cakeStoreDao.getCakeForSaleOfStore(storeId, keyword, pageNumber, pageSize);
    }

    @Override
    public boolean addNewCakeToStore(CakeStore newCakeStore) {
        Store existStore = storeDao.getStoreById(newCakeStore.getIdStore());
        if(existStore == null) {  //Store không tồn tại
            return false;
        }

        return cakeStoreDao.addNewCakeToStore(newCakeStore);
    }

    @Override
    public boolean updateCakeStore(CakeStore cakeStoreUpdate) {
        Cake existCake = cakeDao.getOneById(cakeStoreUpdate.getIdCake());
        if(existCake == null) {
            return false;
        }

        Store existStore = storeDao.getStoreById(cakeStoreUpdate.getIdStore());
        if(existStore == null) {
            return false;
        }

        return cakeStoreDao.updateCakeStore(cakeStoreUpdate);
    }

    @Override
    public Integer getTotalPages(String keyword, Integer pageSize, Integer storeId) {
        int count = cakeStoreDao.countCakeStore(keyword, storeId);
        return (int) Math.ceil(count * 1.0 / pageSize);
    }

    @Override
    public boolean deleteCakeStore(Integer cakeStoreId, String deletedBy) {
        CakeStoreResponse existCakeStore = cakeStoreDao.getCakeStoreById(cakeStoreId);
        if(existCakeStore == null) {  //Cakestore không tồn tại để xoá
            return false;
        }

        return cakeStoreDao.deleteCakeStore(cakeStoreId, deletedBy);
    }
}
