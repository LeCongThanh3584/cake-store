package com.example.demo.admin.service;

import com.example.demo.admin.dto.CakeResponse;
import com.example.demo.admin.dto.CakeViewDto;
import com.example.demo.entity.Cake;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CakeService {

    List<CakeResponse> getAllCakes(int page, int pageSize, String search);
    List<CakeResponse> getAllCakesForStoreAddNew();

    int totalRecord(String search);

    boolean createCake(Cake cake);

    boolean updateCake(Cake cake);

    boolean deleteCake(Cake cake);

    Cake getOneById(long id);

    Cake getCakeByCode(String code);

    String uploadImage(Part path, String folder) throws IOException;

    Map<String, String> validateCreate(HttpServletRequest request) throws IOException, ServletException;

    Map<String, String> validateUpdate(HttpServletRequest request) throws IOException;

    List<CakeViewDto> getTopSelling(Integer storeId, String dateType);
}
