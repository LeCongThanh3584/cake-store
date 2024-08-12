package com.example.demo.admin.service;

import com.example.demo.entity.Category;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    List<Category> getAllCategories(int page, int pageSize, String serach);

    Integer totalRecord(String search);

    Category getOneById(Long id);

    boolean createCategory(Category category);

    boolean updateCategory(Category category);

    boolean deleteCategory(Category category);

    Map<String, String> validateUpdate(HttpServletRequest request);

}
