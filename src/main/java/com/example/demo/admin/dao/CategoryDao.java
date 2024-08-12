package com.example.demo.admin.dao;

import com.example.demo.entity.Category;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {


    public List<Category> getAll(int page, int pageSize, String search) {
        List<Category> categoryList = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT ct.* FROM category ct WHERE ct.deleted_at IS NULL AND " +
                    "CASE WHEN ? IS NOT NULL THEN (ct.code LIKE ? OR ct.name LIKE ? ) ELSE 1 = 1 END " +
                    "ORDER BY ct.created_at DESC LIMIT ?, ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            preparedStatement.setInt(4, page);
            preparedStatement.setInt(5, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setCode(resultSet.getString("code"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setUpdatedBy(resultSet.getString("updated_by"));
                category.setCreatedBy(resultSet.getString("created_by"));
                categoryList.add(category);
            }
            return categoryList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int totalRecord(String search) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM category WHERE deleted_at IS NULL AND CASE " +
                    "WHEN ? IS NOT NULL THEN (code LIKE ? OR name LIKE ? ) ELSE 1 = 1 END";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Category getOneById(long id) {

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT ct.* FROM category ct WHERE ct.id = ? AND ct.deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setCode(resultSet.getString("code"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
//                category.setCreatedAt(LocalDateTime.parse(resultSet.getString("created_at")));
//                category.setUpdatedAt(LocalDateTime.parse(resultSet.getString("updated_at")));
                category.setUpdatedBy(resultSet.getString("updated_by"));
                category.setCreatedBy(resultSet.getString("created_by"));
                return category;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean createCategory(Category category) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO category (code, name, description, created_at, created_by) VALUES (?, ?, ?, NOW(), ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getCode());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getDescription());
            preparedStatement.setString(4, category.getCreatedBy());
            int queryResult = preparedStatement.executeUpdate();
            if (queryResult > 0) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCategory(Category category) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE category SET code = ?, name = ?, description = ?, updated_at = NOW(),  updated_by = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getCode());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getDescription());
            preparedStatement.setString(4, category.getUpdatedBy());
            preparedStatement.setLong(5, category.getId());
            int queryResult = preparedStatement.executeUpdate();
            if (queryResult > 0) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteCategory(Category category) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE category SET  deleted_at = NOW(), deleted_by = ? WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getDeletedBy());
            preparedStatement.setLong(2, category.getId());
            int queryResult = preparedStatement.executeUpdate();
            if (queryResult > 0) {
                connection.commit();
                return true;
            }
            connection.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
