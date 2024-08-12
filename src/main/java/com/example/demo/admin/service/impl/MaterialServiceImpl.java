package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.MaterialDao;
import com.example.demo.admin.service.MaterialService;
import com.example.demo.entity.Material;
import com.example.demo.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialServiceImpl implements MaterialService {

    private MaterialDao materialDao = new MaterialDao();

    @Override
    public List<Material> getAllMaterials(int page, int pageSize, String serach) {
        return materialDao.getAll(page, pageSize, serach);
    }

    @Override
    public List<Material> getMaterialNotInCake(int cakeId) {
        return materialDao.getMaterialNotInCake(cakeId);
    }

    @Override
    public Material getOneById(Long id) {
        return materialDao.getOneById(id);
    }

    @Override
    public boolean deleteById(Material material) {
        return materialDao.deleteMaterial(material);
    }

    @Override
    public int getTotalRecords(String search) {
        return materialDao.getTotalRecords(search);
    }

    @Override
    public boolean createMaterial(Material material) {
        return materialDao.createMaterial(material);
    }

    @Override
    public boolean updateMaterial(Material material) {
        return materialDao.updateMaterial(material);
    }

    public Map<String, String> validateUpdate(HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        String name = request.getParameter("name");
        if (StringUtil.stringIsNullOrEmty(name)) {
            errors.put("name", "name is required");
        }

        String code = request.getParameter("code");
        if (StringUtil.stringIsNullOrEmty(code)) {
            errors.put("code", "code is required");
        }

        return errors;
    }
}
