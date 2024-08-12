package com.example.demo.admin.dao;

import com.example.demo.entity.OrderHistory;
import com.example.demo.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDao {

    public List<OrderHistory> getOrderHistoryByOrderId(int orderId) {
        List<OrderHistory> orderHistoryList = new ArrayList<OrderHistory>();
        String sql = "select * from order_history where id_order=? order by created_at desc";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setId(rs.getInt("id"));
                orderHistory.setIdOrder(rs.getInt("id_order"));
                orderHistory.setDescription(rs.getString("description"));
                orderHistory.setStatus(rs.getInt("status"));
                orderHistory.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                orderHistory.setCreatedBy(rs.getString("created_by"));
                orderHistoryList.add(orderHistory);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return orderHistoryList;
    }

    public OrderHistory getOrderHistoryId(int id) {
        OrderHistory orderHistory = new OrderHistory();
        String sql = "select * from order_history where id_order=? order by created_at desc LIMIT 1";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                orderHistory.setId(rs.getInt("id"));
                orderHistory.setIdOrder(rs.getInt("id_order"));
                orderHistory.setDescription(rs.getString("description"));
                orderHistory.setStatus(rs.getInt("status"));
                orderHistory.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                orderHistory.setCreatedBy(rs.getString("created_by"));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return orderHistory;
    }

    public void create(OrderHistory orderHistory){
        String sql = "INSERT INTO sweet_cake.order_history (id_order,description,status,created_at,created_by) " +
                "VALUES(?,?,?,?,?)";
        try (Connection conn = DatabaseUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,orderHistory.getIdOrder());
            ps.setString(2,orderHistory.getDescription());
            ps.setInt(3, orderHistory.getStatus());
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            ps.setTimestamp(4, timestamp);
            ps.setString(5,orderHistory.getCreatedBy());
            ps.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
