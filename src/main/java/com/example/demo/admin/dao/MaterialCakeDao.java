package com.example.demo.admin.dao;

import com.example.demo.admin.dto.MaterialCakeResponse;
import com.example.demo.entity.MaterialCake;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialCakeDao {

    private static final String CREATE_LIST_MATERIALl_CAKE =
            "INSERT material_cake (id_material, id_cake, description, weight, created_at, created_by) VALUES (?, ?, ?, ?, NOW(), ?)";

    private static final String GET_CAKE_MATERIAL_BY_CAKE =
            "select mc.id, ma.id as idMaterial, ma.code, ma.name, mc.weight, mc.description from material_cake mc " +
                    " inner join material ma on mc.id_material = ma.id where mc.id_cake = ?";

    private static final String DELETE_MATERIAL_CAKE_BY_CAKE = "delete from material_cake where id_cake = ?";

    public boolean deleteMaterialCakeByCake(Connection connection, int idCake) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MATERIAL_CAKE_BY_CAKE)) {
            preparedStatement.setInt(1, idCake);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows <= 0) {
                throw new SQLException("loi roi ne");
            }
        }
        return true;
    }

    public boolean createListMaterial(List<MaterialCake> materialCakeList) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_LIST_MATERIALl_CAKE)) {
                if (getCakeMaterialByCake(materialCakeList.get(0).getIdCake()).size() > 0) {
                    deleteMaterialCakeByCake(connection, materialCakeList.get(0).getIdCake());
                }
                for (MaterialCake materialCake : materialCakeList) {
                    preparedStatement.setInt(1, materialCake.getIdMaterial());
                    preparedStatement.setInt(2, materialCake.getIdCake());
                    preparedStatement.setString(3, materialCake.getDescription());
                    preparedStatement.setInt(4, materialCake.getWeight());
                    preparedStatement.setString(5, materialCake.getCreatedBy());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
                return true;
            } catch (Exception e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<MaterialCakeResponse> getCakeMaterialByCake(int idCake) {
        List<MaterialCakeResponse> listMaterialCake = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CAKE_MATERIAL_BY_CAKE);
            preparedStatement.setInt(1, idCake);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MaterialCakeResponse materialCake = new MaterialCakeResponse();
                materialCake.setId(resultSet.getInt("id"));
                materialCake.setIdMaterial(resultSet.getInt("idMaterial"));
                materialCake.setCode(resultSet.getString("code"));
                materialCake.setName(resultSet.getString("name"));
                materialCake.setWeight(resultSet.getInt("weight"));
                materialCake.setDescription(resultSet.getString("description"));
                listMaterialCake.add(materialCake);
            }
            return listMaterialCake;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
