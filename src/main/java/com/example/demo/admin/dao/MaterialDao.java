package com.example.demo.admin.dao;

import com.example.demo.entity.Material;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaterialDao {

    public List<Material> getAll(int page, int pageSize, String search) {
        List<Material> MaterialList = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT mt.* FROM material mt WHERE mt.deleted_at IS NULL AND " +
                    " CASE WHEN ? IS NOT NULL THEN (mt.code LIKE ? OR mt.name LIKE ? ) ELSE 1 = 1 END " +
                    " ORDER BY mt.created_at DESC LIMIT ?, ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            preparedStatement.setInt(4, page);
            preparedStatement.setInt(5, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Material Material = new Material();
                Material.setId(resultSet.getInt("id"));
                Material.setCode(resultSet.getString("code"));
                Material.setName(resultSet.getString("name"));
                Material.setDescription(resultSet.getString("description"));
                Material.setCreatedBy(resultSet.getString("created_by"));
//                Material.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                MaterialList.add(Material);
            }
            return MaterialList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Material> getMaterialNotInCake(int idCake) {
        List<Material> MaterialList = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT mt.* FROM material mt WHERE mt.deleted_at IS NULL AND " +
                    "mt.id NOT IN (select id_material from material_cake where id_cake = ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idCake);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Material Material = new Material();
                Material.setId(resultSet.getInt("id"));
                Material.setCode(resultSet.getString("code"));
                Material.setName(resultSet.getString("name"));
                Material.setDescription(resultSet.getString("description"));
                Material.setCreatedBy(resultSet.getString("created_by"));
//                Material.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                MaterialList.add(Material);
            }
            return MaterialList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Material getOneById(long id) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT ct.* FROM material ct WHERE ct.id = ? AND ct.deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Material Material = new Material();
                Material.setId(resultSet.getInt("id"));
                Material.setCode(resultSet.getString("code"));
                Material.setName(resultSet.getString("name"));
                Material.setDescription(resultSet.getString("description"));
//                Material.setCreatedAt(resultSet.getString("created_at"));
//                Material.setUpdatedAt(resultSet.getString("updated_at"));
                Material.setUpdatedBy(resultSet.getString("updated_by"));
                Material.setCreatedBy(resultSet.getString("created_by"));
                return Material;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotalRecords(String search) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM material WHERE deleted_at IS NULL AND CASE " +
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


    public boolean createMaterial(Material material) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO material (code, name, description, created_at, created_by) VALUES (?, ?, ?, NOW(), ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, material.getCode());
            preparedStatement.setString(2, material.getName());
            preparedStatement.setString(3, material.getDescription());
            preparedStatement.setString(4, material.getCreatedBy());
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

    public boolean updateMaterial(Material material) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE material SET code = ?, name = ?, description = ?, updated_at = NOW(), updated_by = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, material.getCode());
            preparedStatement.setString(2, material.getName());
            preparedStatement.setString(3, material.getDescription());
            preparedStatement.setString(4, material.getUpdatedBy());
            preparedStatement.setLong(5, material.getId());
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


    public boolean deleteMaterial(Material material) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE material SET deleted_at = NOW(), deleted_by = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, material.getDeletedBy());
            preparedStatement.setInt(2, material.getId());
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
