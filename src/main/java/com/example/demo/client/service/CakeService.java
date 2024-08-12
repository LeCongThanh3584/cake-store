package com.example.demo.client.service;

import com.example.demo.client.dto.CakeDetailDto;
import com.example.demo.client.dto.CakePage;
import com.example.demo.client.dto.CakeViewDto;

import java.util.List;

public interface CakeService {
    List<CakeViewDto> GetRecommendation();

    Integer CountSearch(CakePage cakePage);

    List<CakeViewDto> Search(CakePage cakePage, Integer pageSize, Integer offset);

    CakeDetailDto GetCakeById(Integer storeId, Integer id);
}