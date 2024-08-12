package com.example.demo.admin.service;

import com.example.demo.entity.Material;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface MaterialService {

    List<Material> getAllMaterials(int page, int pageSize, String serach);

    List<Material> getMaterialNotInCake(int cakeId);

    Material getOneById(Long id);

    boolean deleteById(Material material);

    int getTotalRecords(String search);

    boolean createMaterial(Material material);

    boolean updateMaterial(Material material);

    Map<String, String> validateUpdate(HttpServletRequest request);

}
