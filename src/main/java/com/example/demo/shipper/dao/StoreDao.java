package com.example.demo.shipper.dao;

import com.example.demo.entity.Store;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDao {
    private static final String GET_STORES = "select * from store where store.deleted_at is null;";

    public List<Store> GetStores() {
        List<Store> stores = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            ResultSet result = connection.createStatement().executeQuery(GET_STORES);

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
