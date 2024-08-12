package com.example.demo.client.dao;

import com.example.demo.entity.Store;
import com.example.demo.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StoreDao {
    private static final String GET_STORES = "select " +
            "    distinct store.id, store.* from store join cake_store\n" +
            "    on cake_store.id_store = store.id\n" +
            "    where store.deleted_at is null and  cake_store.id_cake = ?  " +
            "    and cake_store.expiration_date > ?;";
    private static final String GET_ALL = "select * from store where store.deleted_at is null;";

    public List<Store> GetStores(Integer storeId) {
        List<Store> stores = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(GET_STORES);
            ps.setInt(1, storeId);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                Store store = new Store();
                store.setId(result.getInt("id"));
                store.setName(result.getString("name"));
                store.setAddress(result.getString("address"));
                store.setImage(result.getString("image"));
                store.setPhone(result.getString("phone"));
                store.setStatus(result.getInt("status"));

                stores.add(store);
            }
            return stores;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Store> GetAll() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            List<Store> stores = new ArrayList<>();
            ResultSet result = connection.createStatement().executeQuery(GET_ALL);

            while (result.next()) {
                Store store = new Store();
                store.setId(result.getInt("id"));
                store.setName(result.getString("name"));
                store.setAddress(result.getString("address"));
                store.setImage(result.getString("image"));
                store.setPhone(result.getString("phone"));
                store.setStatus(result.getInt("status"));

                stores.add(store);
            }
            return stores;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
