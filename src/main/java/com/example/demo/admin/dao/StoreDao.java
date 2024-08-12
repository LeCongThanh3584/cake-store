package com.example.demo.admin.dao;

import com.example.demo.entity.Store;
import com.example.demo.util.DatabaseUtil;
import com.example.demo.util.UploadUtil;
import jakarta.servlet.http.Part;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StoreDao {

    public List<Store> getAllStores() {
        List<Store> storeList = new ArrayList<>();

        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM store WHERE deleted_at IS NULL ORDER BY created_at DESC";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Store storeReturn = new Store();

                storeReturn.setId(resultSet.getInt("id"));
                storeReturn.setName(resultSet.getString("name"));
                storeReturn.setCode(resultSet.getString("code"));
                storeReturn.setImage(resultSet.getString("image"));
                storeReturn.setAddress(resultSet.getString("address"));
                storeReturn.setPhone(resultSet.getString("phone"));
                storeReturn.setStatus(resultSet.getInt("status"));

                storeList.add(storeReturn);
            }

            return storeList;

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

        return storeList;
    }

    public Store getStoreById(int storeId) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM store WHERE deleted_at IS NULL AND id = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, storeId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Store store = new Store();
                store.setId(resultSet.getInt("id"));
                store.setName(resultSet.getString("name"));
                store.setAddress(resultSet.getString("address"));
                store.setCode(resultSet.getString("code"));
                store.setPhone(resultSet.getString("phone"));
                store.setStatus(resultSet.getInt("status"));
                store.setImage(resultSet.getString("image"));

                return store;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public List<Store> getAllStoresPaginationSearch(Integer pageNumber, Integer pageSize, String keyword) {
        List<Store> storeList = new ArrayList<>();

        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM store " +
                "WHERE deleted_at IS NULL " +
                "AND CASE WHEN ? IS NOT NULL THEN (code LIKE ? OR name LIKE ? OR address LIKE ? OR phone LIKE ?) ELSE 1=1 END " +
                "ORDER BY created_at DESC LIMIT ?, ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
           preparedStatement = connection.prepareStatement(sqlQuery);
           preparedStatement.setString(1, keyword);
           preparedStatement.setString(2, "%" + keyword + "%");
           preparedStatement.setString(3, "%" + keyword + "%");
           preparedStatement.setString(4, "%" + keyword + "%");
           preparedStatement.setString(5, "%" + keyword + "%");
           preparedStatement.setInt(6, ((pageNumber - 1) * pageSize));
           preparedStatement.setInt(7, pageSize);
           resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Store storeReturn = new Store();

                storeReturn.setId(resultSet.getInt("id"));
                storeReturn.setName(resultSet.getString("name"));
                storeReturn.setCode(resultSet.getString("code"));
                storeReturn.setImage(resultSet.getString("image"));
                storeReturn.setAddress(resultSet.getString("address"));
                storeReturn.setPhone(resultSet.getString("phone"));
                storeReturn.setStatus(resultSet.getInt("status"));

                storeList.add(storeReturn);
            }

            return storeList;

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

        return storeList;
    }

    public boolean addNewStore(Store newStore) {
        Connection connection = DatabaseUtil.getConnection();  //Kết nối đến database
        String sqlQuery = "INSERT INTO store(name, code, image, address, phone, status, created_at, created_by) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
           preparedStatement = connection.prepareStatement(sqlQuery);
           preparedStatement.setString(1, newStore.getName());
           preparedStatement.setString(2, newStore.getCode());
           preparedStatement.setString(3, newStore.getImage());
           preparedStatement.setString(4, newStore.getAddress());
           preparedStatement.setString(5, newStore.getPhone());
           preparedStatement.setInt(6, newStore.getStatus());
           preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
           preparedStatement.setString(8, newStore.getCreatedBy());

           int result = preparedStatement.executeUpdate();
           if(result > 0) {
               connection.commit();
               return true;
           }

           connection.rollback();
           return false;

        } catch (SQLException ex) {
           ex.printStackTrace();
        } finally {
           try {
               connection.close();
               preparedStatement.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
        }
        return false;
    }

    public boolean updateStore(Store storeUpdate) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "UPDATE store SET name = ?, code = ?, phone = ?, " +
                "address = ?, image = ?, status = ?, updated_at = ?, updated_by = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, storeUpdate.getName());
            preparedStatement.setString(2, storeUpdate.getCode());
            preparedStatement.setString(3, storeUpdate.getPhone());
            preparedStatement.setString(4, storeUpdate.getAddress());
            preparedStatement.setString(5, storeUpdate.getImage());
            preparedStatement.setInt(6, storeUpdate.getStatus());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, storeUpdate.getUpdatedBy());
            preparedStatement.setInt(9, storeUpdate.getId());

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

    public int countStore(String keyword) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT COUNT(id) AS quantity_store FROM store " +
                "WHERE deleted_at IS NULL AND CASE WHEN ? IS NOT NULL " +
                "THEN (code LIKE ? OR name LIKE ? OR address LIKE ? OR phone LIKE ?) ELSE 1=1 END";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, "%" + keyword + "%");
            preparedStatement.setString(5, "%" + keyword + "%");
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt("quantity_store");
            }

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
        return 0;
    }

    public boolean deleteStore(int storeId, String deleted_by) {
        Connection connection =  DatabaseUtil.getConnection();
        String sqlQuery = "UPDATE store SET deleted_at = ?, deleted_by = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(2, deleted_by);
            preparedStatement.setInt(3, storeId);

            int result = preparedStatement.executeUpdate();

            if(result > 0) {
                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        } catch (SQLException ex) {
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
}
