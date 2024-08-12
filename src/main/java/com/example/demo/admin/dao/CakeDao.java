package com.example.demo.admin.dao;

import com.example.demo.admin.dto.CakeResponse;
import com.example.demo.admin.dto.CakeViewDto;
import com.example.demo.entity.Cake;
import com.example.demo.util.DatabaseUtil;
import com.example.demo.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CakeDao {
    public List<CakeViewDto> getTopSelling(Integer storeId, String dateType) {
        List<CakeViewDto> cakes = new ArrayList<>();
        String sql = "select cake.*,\n" +
                "       min(order_detail.price) as min_price,\n" +
                "       max(order_detail.price) as max_price,\n" +
                "       sum(order_detail.quantity) as quantity,\n" +
                "       sum(order_detail.price) as revenue from cake\n" +
                "    join cake_store on cake.id = cake_store.id_cake\n" +
                "    join order_detail on cake_store.id = order_detail.id_cake_store\n" +
                "    join order_history a on order_detail.id_order = a.id_order\n" +
                "    join `order` on order_detail.id_order = `order`.id\n" +
                "    join store on `order`.id_store = store.id" +
                "    where a.created_at >= ?\n" +
                "    and a.created_at = (select max(b.created_at)\n" +
                "                      from order_history b\n" +
                "                      where a.id_order = b.id_order)\n" +
                "    and `order`.deleted_at is null ";
        if (storeId != null) {
            sql += " and `order`.id_store = " + storeId;
        }
        sql += "\n group by cake.id order by revenue desc limit 5";

        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(DateUtil.getDateType(dateType)));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CakeViewDto cakeDto = new CakeViewDto();
                cakeDto.setId(resultSet.getInt("cake.id"));
                cakeDto.setName(resultSet.getString("cake.name"));
                cakeDto.setImage(resultSet.getString("cake.image"));

                cakeDto.setMinPrice(resultSet.getString("min_price"));
                cakeDto.setMaxPrice(resultSet.getString("max_price"));
                cakeDto.setRevenue(resultSet.getInt("revenue"));
                cakeDto.setQuantity(resultSet.getInt("quantity"));
                cakes.add(cakeDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cakes;
    }


    public List<CakeResponse> getAllCake(int page, int pageSize, String search) {
        List<CakeResponse> cakeList = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT ck.id, ck.code, ck.name AS name, ct.name AS category, ck.image," +
                    " ck.created_by, ck.created_at FROM cake ck INNER JOIN category ct on ck.id_category = ct.id " +
                    " WHERE ck.deleted_at IS NULL AND CASE WHEN ? IS NOT NULL THEN (ck.code LIKE ? OR ck.name LIKE ? ) ELSE 1 = 1 END" +
                    " ORDER BY ck.created_at DESC LIMIT ?,?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            preparedStatement.setInt(4, page);
            preparedStatement.setInt(5, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CakeResponse cakeDTO = new CakeResponse();
                cakeDTO.setId(resultSet.getLong("id"));
                cakeDTO.setCode(resultSet.getString("code"));
                cakeDTO.setName(resultSet.getString("name"));
                cakeDTO.setCategory(resultSet.getString("category"));
                cakeDTO.setImage(resultSet.getString("image"));
                cakeDTO.setCreatedBy(resultSet.getString("created_by"));
                cakeDTO.setCreatedAt(resultSet.getTimestamp("created_at"));
                cakeList.add(cakeDTO);
            }
            return cakeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int totalRecord(String search) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM cake WHERE deleted_at IS NULL AND CASE " +
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

    public Cake getOneById(long id) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM cake WHERE id = ? AND deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cake cake = new Cake();
                cake.setId(resultSet.getInt("id"));
                cake.setCode(resultSet.getString("code"));
                cake.setName(resultSet.getString("name"));
                cake.setImage(resultSet.getString("image"));
                cake.setIdCategory(resultSet.getLong("id_category"));
                cake.setCreatedBy(resultSet.getString("created_by"));
                cake.setWeight(resultSet.getInt("weight"));
                cake.setColor(resultSet.getString("color"));
                cake.setStatus(resultSet.getInt("status"));
                cake.setHeight(resultSet.getInt("height"));
                cake.setLength(resultSet.getInt("length"));
                cake.setSize(resultSet.getString("size"));
                cake.setDescription(resultSet.getString("description"));
                cake.setImage(resultSet.getString("image"));
//                cakeDTO.setCreatedAt(resultSet.getTimestamp("created_at"));
                cake.setUpdatedBy(resultSet.getString("updated_by"));
                return cake;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cake getCodeByCode(String code) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM cake WHERE code = ? AND deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cake cake = new Cake();
                cake.setId(resultSet.getInt("id"));
                cake.setCode(resultSet.getString("code"));
                cake.setName(resultSet.getString("name"));
                cake.setImage(resultSet.getString("image"));
                cake.setIdCategory(resultSet.getLong("id_category"));
                cake.setCreatedBy(resultSet.getString("created_by"));
                cake.setWeight(resultSet.getInt("weight"));
                cake.setColor(resultSet.getString("color"));
                cake.setStatus(resultSet.getInt("status"));
                cake.setHeight(resultSet.getInt("height"));
                cake.setLength(resultSet.getInt("length"));
                cake.setSize(resultSet.getString("size"));
                cake.setDescription(resultSet.getString("description"));
                cake.setImage(resultSet.getString("image"));
//                cakeDTO.setCreatedAt(resultSet.getTimestamp("created_at"));
                cake.setUpdatedBy(resultSet.getString("updated_by"));
                return cake;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createCake(Cake cake) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO `sweet_cake`.`cake` (`id_category`, `name`, `code`, `weight`, `image`, `color`," +
                    " `size`, `height`, `length`, `description`, `status`, `created_at`, `created_by`)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, cake.getIdCategory());
            preparedStatement.setString(2, cake.getName());
            preparedStatement.setString(3, cake.getCode());
            preparedStatement.setLong(4, cake.getWeight());
            preparedStatement.setString(5, cake.getImage());
            preparedStatement.setString(6, cake.getColor());
            preparedStatement.setString(7, cake.getSize());
            preparedStatement.setLong(8, cake.getHeight());
            preparedStatement.setLong(9, cake.getLength());
            preparedStatement.setString(10, cake.getDescription());
            preparedStatement.setLong(11, cake.getStatus());
            preparedStatement.setString(12, cake.getCreatedBy());
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

    public boolean updateCake(Cake cake) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE cake SET id_category = ?, name = ?, code = ?, weight = ?, image = ?, " +
                    " color = ?, size = ?, height = ?, length = ?, description = ?, status = ?," +
                    " updated_at = NOW(), updated_by = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, cake.getIdCategory());
            preparedStatement.setString(2, cake.getName());
            preparedStatement.setString(3, cake.getCode());
            preparedStatement.setLong(4, cake.getWeight());
            preparedStatement.setString(5, cake.getImage());
            preparedStatement.setString(6, cake.getColor());
            preparedStatement.setString(7, cake.getSize());
            preparedStatement.setLong(8, cake.getHeight());
            preparedStatement.setLong(9, cake.getLength());
            preparedStatement.setString(10, cake.getDescription());
            preparedStatement.setInt(11, cake.getStatus());
            preparedStatement.setString(12, cake.getUpdatedBy());
            preparedStatement.setLong(13, cake.getId());
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

    public boolean deleteCake(Cake cake) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE cake SET deleted_at = NOW(), deleted_by = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cake.getDeletedBy());
            preparedStatement.setInt(2, cake.getId());
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

    public List<CakeResponse> getAllCake() {
        List<CakeResponse> cakeResponseList = new ArrayList<>();

        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT ck.id, ck.code, ck.name AS cake_name, ck.image, ct.name AS category_name " +
                "FROM cake ck JOIN category ct ON ck.id_category = ct.id " +
                "WHERE ck.deleted_at IS NULL ORDER BY ck.created_at DESC ";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CakeResponse cakeResponse = new CakeResponse();
                cakeResponse.setId(resultSet.getLong("id"));
                cakeResponse.setName(resultSet.getString("cake_name"));
                cakeResponse.setCode(resultSet.getString("code"));
                cakeResponse.setCategory(resultSet.getString("category_name"));
                cakeResponse.setImage(resultSet.getString("image"));

                cakeResponseList.add(cakeResponse);
            }

            return cakeResponseList;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return cakeResponseList;
    }
}
