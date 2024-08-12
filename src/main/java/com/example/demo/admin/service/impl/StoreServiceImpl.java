package com.example.demo.admin.service.impl;

import com.cloudinary.Cloudinary;
import com.example.demo.admin.dao.StoreDao;
import com.example.demo.admin.service.StoreService;
import com.example.demo.entity.Store;
import com.example.demo.util.UploadUtil;
import jakarta.servlet.http.Part;

import java.util.List;

public class StoreServiceImpl implements StoreService {
    private StoreDao storeDao;

    public StoreServiceImpl() {
        this.storeDao = new StoreDao();
    }

    @Override
    public List<Store> getAllStores() {
        return storeDao.getAllStores();
    }

    @Override
    public List<Store> getAllStoresPaginationSearch(Integer pageNumber, Integer pageSize, String keyWord) {
        return storeDao.getAllStoresPaginationSearch(pageNumber, pageSize, keyWord);
    }

    @Override
    public boolean addNewStore(Store newStore, Part imageStore) {
        String linkImageStore = UploadUtil.uploadImage(imageStore, newStore.getCode());  //Upload ảnh lên cloudinary
        newStore.setImage(linkImageStore);
        return storeDao.addNewStore(newStore);
    }

    @Override
    public boolean updateStore(Store storeUpdate, Part imageStore) {
        Store existStore = storeDao.getStoreById(storeUpdate.getId());
        if(existStore == null) {  //Store không tồn tại để cập nhật
            return false;
        }

        if(imageStore.getSize() == 0) { //Không gửi ảnh lên
            storeUpdate.setImage(existStore.getImage()); //Lấy ảnh cũ
        } else {  //Có gửi ảnh lên
            String linkImageStore = UploadUtil.uploadImage(imageStore, storeUpdate.getCode()); //upload và lấy đường link ảnh mới về
            storeUpdate.setImage(linkImageStore); //Lấy ảnh mới
        }

        return storeDao.updateStore(storeUpdate);
    }

    @Override
    public boolean deleteStore(int storeId, String deleted_by) {
        Store existStore = storeDao.getStoreById(storeId);
        if(existStore == null) {  //store không tồn tại để xoá
            return false;
        }
        return storeDao.deleteStore(storeId, deleted_by);
    }

    @Override
    public Store getStoreById(int storeId) {
        return storeDao.getStoreById(storeId);
    }

    @Override
    public Integer getTotalPages(String keyword, int pageSize) {
        int count = storeDao.countStore(keyword);
        return (int) Math.ceil(count * 1.0 / pageSize);
    }
}
