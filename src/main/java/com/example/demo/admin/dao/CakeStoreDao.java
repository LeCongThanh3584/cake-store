package com.example.demo.admin.dao;

import com.example.demo.admin.dto.CakeStoreResponse;
import com.example.demo.entity.CakeStore;
import com.example.demo.util.DatabaseUtil;
import org.apache.http.ConnectionClosedException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CakeStoreDao {
    public List<CakeStoreResponse> getAllCakeByStore(int storeId) {
        List<CakeStoreResponse> list = new ArrayList<>();
        String sql = "SELECT cs.id, cs.id_store, s.name AS store_name, cs.id_cake, c.name AS cake_name, " +
                 "c.image AS cake_image ,c.code, cs.price, cs.quantity, cs.production_date, cs.expiration_date " +
                 "FROM cake_store cs " +
                 "JOIN store s ON cs.id_store = s.id " +
                 "JOIN cake c ON cs.id_cake = c.id " +
                 "WHERE cs.id_store = ? AND cs.expiration_date > NOW()";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, storeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CakeStoreResponse cakeStoreResponse = new CakeStoreResponse();
                cakeStoreResponse.setId(rs.getInt("id"));
                cakeStoreResponse.setIdStore(rs.getInt("id_store"));
                cakeStoreResponse.setStoreName(rs.getString("store_name"));
                cakeStoreResponse.setIdCake(rs.getInt("id_cake"));
                cakeStoreResponse.setImageCake(rs.getString("cake_image"));
                cakeStoreResponse.setCakeName(rs.getString("cake_name"));
                cakeStoreResponse.setCodeCake(rs.getString("code"));
                cakeStoreResponse.setPrice(rs.getBigDecimal("price"));
                cakeStoreResponse.setQuantity(rs.getInt("quantity"));
                cakeStoreResponse.setProductionDate(rs.getTimestamp("production_date").toLocalDateTime());
                cakeStoreResponse.setExpirationDate(rs.getTimestamp("expiration_date").toLocalDateTime());
                list.add(cakeStoreResponse);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
         return list;
    }

    public CakeStoreResponse getCakeStoreById(int cakeStoreId) {
        String sql = "SELECT cs.*, c.id, c.name AS cake_name, c.code, c.image, ct.name AS category_name " +
                "FROM cake_store cs " +
                "JOIN cake c ON cs.id_cake = c.id " +
                "JOIN category ct ON c.id_category = ct.id " +
                "WHERE cs.deleted_at IS NULL AND cs.id = ?";

        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cakeStoreId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                CakeStoreResponse cakeStoreResponse = new CakeStoreResponse();
                cakeStoreResponse.setId(rs.getInt("id"));
                cakeStoreResponse.setIdStore(rs.getInt("id_store"));
                cakeStoreResponse.setIdCake(rs.getInt("id_cake"));
                cakeStoreResponse.setPrice(rs.getBigDecimal("price"));
                cakeStoreResponse.setQuantity(rs.getInt("quantity"));

                cakeStoreResponse.setProductionDate(rs.getTimestamp("production_date").toLocalDateTime());
                cakeStoreResponse.setExpirationDate(rs.getTimestamp("expiration_date").toLocalDateTime());
                cakeStoreResponse.setStatus(rs.getInt("status"));
                cakeStoreResponse.setCakeName(rs.getString("cake_name"));
                cakeStoreResponse.setCodeCake(rs.getString("code"));
                cakeStoreResponse.setImageCake(rs.getString("image"));
                cakeStoreResponse.setCategoryName(rs.getString("category_name"));

                return cakeStoreResponse;
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

         return null;
    }

    public List<CakeStoreResponse> getCakeForSaleOfStore(Integer storeId, String keyword, Integer pageNumber, Integer pageSize) {
        List<CakeStoreResponse> cakeStoreList = new ArrayList<>();

        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT cs.id, cs.id_store, cs.id_cake, c.code AS code_cake, " +
                "s.name AS store_name, c.name AS cake_name, c.image, cs.price, cs.quantity, " +
                "cs.production_date, cs.expiration_date, cs.status FROM cake_store cs " +
                "JOIN cake c ON cs.id_cake = c.id " +
                "JOIN store s ON cs.id_store = s.id " +
                "WHERE cs.deleted_at IS NULL AND cs.id_store = ? AND " +
                "CASE WHEN ? IS NOT NULL THEN (c.code LIKE ? OR c.name LIKE ?) ELSE 1=1 END " +
                "ORDER BY cs.created_at DESC LIMIT ?, ?";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, storeId);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, "%" + keyword + "%");
            preparedStatement.setInt(5, (pageNumber - 1) * pageSize);
            preparedStatement.setInt(6, pageSize);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                CakeStoreResponse cakeStoreResponse = new CakeStoreResponse();
                cakeStoreResponse.setId(resultSet.getInt("id"));
                cakeStoreResponse.setIdStore(resultSet.getInt("id_store"));
                cakeStoreResponse.setStoreName(resultSet.getString("store_name"));
                cakeStoreResponse.setIdCake(resultSet.getInt("id_cake"));
                cakeStoreResponse.setCakeName(resultSet.getString("cake_name"));
                cakeStoreResponse.setImageCake(resultSet.getString("image"));
                cakeStoreResponse.setPrice(resultSet.getBigDecimal("price"));
                cakeStoreResponse.setCodeCake(resultSet.getString("code_cake"));
                cakeStoreResponse.setQuantity(resultSet.getInt("quantity"));
                cakeStoreResponse.setProductionDate(resultSet.getTimestamp("production_date").toLocalDateTime());
                cakeStoreResponse.setExpirationDate(resultSet.getTimestamp("expiration_date").toLocalDateTime());
                cakeStoreResponse.setStatus(resultSet.getInt("status"));

                cakeStoreList.add(cakeStoreResponse);
            }

            return cakeStoreList;
        }catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return cakeStoreList;
    }

    public int countCakeStore(String keyword, Integer storeId) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT COUNT(cs.id) FROM cake_store cs " +
                "JOIN cake c ON cs.id_cake = c.id " +
                "JOIN store s ON cs.id_store = s.id " +
                "WHERE cs.deleted_at IS NULL AND cs.id_store = ? " +
                "AND CASE WHEN ? IS NOT NULL THEN (c.code LIKE ? OR c.name LIKE ?) ELSE 1=1 END";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, storeId);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, "%" + keyword + "%");
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt(1);
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                resultSet.close();
                preparedStatement.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return 0;
    }

    public boolean addNewCakeToStore(CakeStore newCakeStore) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "INSERT INTO cake_store(id_store, id_cake, price, quantity, " +
                "production_date, expiration_date, status, created_at, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, newCakeStore.getIdStore());
            preparedStatement.setInt(2, newCakeStore.getIdCake());
            preparedStatement.setBigDecimal(3, newCakeStore.getPrice());
            preparedStatement.setInt(4, newCakeStore.getQuantity());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(newCakeStore.getProductionDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(newCakeStore.getExpirationDate()));
            preparedStatement.setInt(7, newCakeStore.getStatus());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(9, newCakeStore.getCreatedBy());

            int result = preparedStatement.executeUpdate();

            if(result > 0) {
                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateCakeStore(CakeStore cakeStoreUpdate) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "UPDATE cake_store SET price = ?, quantity = ?, production_date = ?, " +
                "expiration_date = ?, status = ?, updated_at = ?, updated_by = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setBigDecimal(1, cakeStoreUpdate.getPrice());
            preparedStatement.setInt(2, cakeStoreUpdate.getQuantity());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(cakeStoreUpdate.getProductionDate()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(cakeStoreUpdate.getExpirationDate()));
            preparedStatement.setInt(5, cakeStoreUpdate.getStatus());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, cakeStoreUpdate.getUpdatedBy());
            preparedStatement.setInt(8, cakeStoreUpdate.getId());

            int result = preparedStatement.executeUpdate();

            if(result > 0) {
                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteCakeStore(Integer cakeStoreId, String deletedBy) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "UPDATE cake_store SET deleted_at = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, cakeStoreId);

            int result = preparedStatement.executeUpdate();

            if(result > 0) {
                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public List<CakeStoreResponse> getAllCakeByExpritedDate(int storeId) {
        List<CakeStoreResponse> list = new ArrayList<>();
        String sql = "SELECT cs.id, cs.id_store, s.name AS store_name, cs.id_cake, c.name AS cake_name, " +
                 "c.image AS cake_image ,c.code, cs.price, cs.quantity, cs.production_date, cs.expiration_date " +
                 "FROM cake_store cs " +
                 "JOIN store s ON cs.id_store = s.id " +
                 "JOIN cake c ON cs.id_cake = c.id " +
                 "WHERE (CASE WHEN ? > 0 THEN cs.id_store = ? ELSE TRUE END) AND cs.expiration_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 3 DAY)";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, storeId);
            ps.setInt(2,storeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CakeStoreResponse cakeStoreResponse = new CakeStoreResponse();
                cakeStoreResponse.setId(rs.getInt("id"));
                cakeStoreResponse.setIdStore(rs.getInt("id_store"));
                cakeStoreResponse.setStoreName(rs.getString("store_name"));
                cakeStoreResponse.setIdCake(rs.getInt("id_cake"));
                cakeStoreResponse.setImageCake(rs.getString("cake_image"));
                cakeStoreResponse.setCakeName(rs.getString("cake_name"));
                cakeStoreResponse.setCodeCake(rs.getString("code"));
                cakeStoreResponse.setPrice(rs.getBigDecimal("price"));
                cakeStoreResponse.setQuantity(rs.getInt("quantity"));
                cakeStoreResponse.setProductionDate(rs.getTimestamp("production_date").toLocalDateTime());
                cakeStoreResponse.setExpirationDate(rs.getTimestamp("expiration_date").toLocalDateTime());
                list.add(cakeStoreResponse);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
         return list;
    }
}
