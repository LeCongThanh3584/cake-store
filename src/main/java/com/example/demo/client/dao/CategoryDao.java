package com.example.demo.client.dao;

import com.example.demo.entity.Category;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private static final String GET_CATEGORIES = "select * from category " +
            "where category.deleted_at is null";

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList();

        try (Connection connection = DatabaseUtil.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(GET_CATEGORIES);
            
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setCode(resultSet.getString("code"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));

                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

}
