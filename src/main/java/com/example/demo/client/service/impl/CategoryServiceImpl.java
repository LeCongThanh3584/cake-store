package com.example.demo.client.service.impl;

import com.example.demo.client.dao.CategoryDao;
import com.example.demo.client.service.CategoryService;
import com.example.demo.entity.Category;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDao();

    @Override
    public List<Category> GetCategories() {
        return categoryDao.getCategories();
    }
}
