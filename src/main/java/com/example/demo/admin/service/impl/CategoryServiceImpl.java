package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.CategoryDao;
import com.example.demo.admin.service.CategoryService;
import com.example.demo.entity.Category;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDao();

    @Override
    public List<Category> getAllCategories(int page, int pageSize, String search) {
        return categoryDao.getAll(page, pageSize, search);
    }

    @Override
    public Integer totalRecord(String search) {
        return categoryDao.totalRecord(search);
    }

    @Override
    public Category getOneById(Long id) {
        return categoryDao.getOneById(id);
    }

    @Override
    public boolean createCategory(Category category) {
        return categoryDao.createCategory(category);
    }

    @Override
    public boolean updateCategory(Category category) {
        return categoryDao.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(Category category) {
        return categoryDao.deleteCategory(category);
    }

    @Override
    public Map<String, String> validateUpdate(HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "name is required");
        }

        String code = request.getParameter("code");
        if (code == null || code.trim().isEmpty()) {
            errors.put("code", "code is required");
        }

        return errors;
    }
}
