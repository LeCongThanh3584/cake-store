package com.example.demo.client.dao;

import com.example.demo.entity.Material;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDao {
    private static final String GET_ALL = "select * from material " +
            "where material.deleted_at is null";

    public List<Material> getAll() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            List<Material> materials = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(GET_ALL);

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setName(resultSet.getString("name"));
                material.setDescription(resultSet.getString("description"));
                material.setCode(resultSet.getString("code"));

                materials.add(material);
            }
            return materials;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
