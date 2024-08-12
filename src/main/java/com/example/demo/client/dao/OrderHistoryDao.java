package com.example.demo.client.dao;

import com.example.demo.entity.OrderHistory;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDao {

    private final String GET_ALL_BY_ID_ORDER = "SELECT * FROM order_history WHERE id_order = ? ORDER BY created_at desc";

    public List<OrderHistory> getAllOrderHistoryByIdOrder(int idOrder) {
        List<OrderHistory> listOrderHistory = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_BY_ID_ORDER);
            preparedStatement.setInt(1, idOrder);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setId(resultSet.getInt(1));
                orderHistory.setIdOrder(resultSet.getInt(2));
                orderHistory.setDescription(resultSet.getString(3));
                orderHistory.setStatus(resultSet.getInt(4));
//                orderHistory.setCreatedAt(resultSet.getString(5));
                orderHistory.setCreatedBy(resultSet.getString(6));
                listOrderHistory.add(orderHistory);
            }
            return listOrderHistory;
        } catch (Exception e) {

        }
        return null;
    }

}
