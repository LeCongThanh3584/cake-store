package com.example.demo.client.service.impl;

import com.example.demo.client.dao.CakeDao;
import com.example.demo.client.dto.CakeDetailDto;
import com.example.demo.client.dto.CakePage;
import com.example.demo.client.dto.CakeViewDto;
import com.example.demo.client.service.CakeService;

import java.util.List;


public class CakeServiceImpl implements CakeService {
    private final CakeDao cakeDao = new CakeDao();

    @Override
    public List<CakeViewDto> GetRecommendation() {
        return cakeDao.getCakes();
    }

    @Override
    public Integer CountSearch(CakePage cakePage) {
        return cakeDao.CountSearch(cakePage);
    }

    @Override
    public List<CakeViewDto> Search(CakePage cakePage, Integer pageSize, Integer offset) {
        return cakeDao.searchCakes(cakePage, pageSize, offset);
    }

    @Override
    public CakeDetailDto GetCakeById(Integer storeId, Integer id) {
        return cakeDao.GetById(storeId, id);
    }
}
