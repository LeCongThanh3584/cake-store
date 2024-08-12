package com.example.demo.admin.dao;

import com.example.demo.admin.dto.OrderDetailDto;
import com.example.demo.admin.dto.CakeStoreResponse;
import com.example.demo.entity.CakeStore;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.util.DatabaseUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrderDetailDao {

    public void create(OrderDetail orderDetail) {
        try (Connection conn = DatabaseUtil.getConnection()) {

            CakeStoreDao cakeStoreDao = new CakeStoreDao();
            CakeStoreResponse cakeStoreResponse = cakeStoreDao.getCakeStoreById(orderDetail.getIdCakeStore());

            if (isSufficientQuantity(cakeStoreResponse, orderDetail)) {
                cakeStoreResponse.setQuantity(cakeStoreResponse.getQuantity() - orderDetail.getQuantity());

                updateCakeStoreQuantity(conn, cakeStoreResponse);

                OrderDao orderDao = new OrderDao();
                Order order = orderDao.getOrderById(orderDetail.getIdOrder());
                BigDecimal quantity = BigDecimal.valueOf(orderDetail.getQuantity());
                BigDecimal createMoney = orderDetail.getPrice().multiply(quantity);
                order.setTotalMoney(order.getTotalMoney().add(createMoney));

                updateOrderTotalMoney(conn, order);

                insertOrderDetail(conn, orderDetail);

                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(OrderDetail orderDetail) {
        try (Connection conn = DatabaseUtil.getConnection()) {

            int oldQuantity = getOldQuantity(orderDetail);
            int changeQuantity = orderDetail.getQuantity() - oldQuantity;

            CakeStoreDao cakeStoreDao = new CakeStoreDao();
            CakeStoreResponse cakeStoreResponse = cakeStoreDao.getCakeStoreById(orderDetail.getIdCakeStore());

            if (isSufficientQuantity(cakeStoreResponse, changeQuantity)) {
                cakeStoreResponse.setQuantity(cakeStoreResponse.getQuantity() - changeQuantity);

                updateCakeStoreQuantity(conn, cakeStoreResponse);

                OrderDao orderDao = new OrderDao();
                Order order = orderDao.getOrderById(orderDetail.getIdOrder());

                BigDecimal changeMoney = orderDetail.getPrice().multiply(BigDecimal.valueOf(changeQuantity));
                order.setTotalMoney(order.getTotalMoney().add(changeMoney));

                updateOrderTotalMoney(conn, order);

                updateOrderDetail(conn, orderDetail);

                conn.commit();
            } else {
                System.out.println("Not enough quantity in the store to update the order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<OrderDetailDto> getOrderDetailsByOrder(int orderId){
        List<OrderDetailDto> list = new ArrayList<>();
        String sql = "SELECT od.id, od.id_cake_store, od.id_order, od.quantity, od.price, od.created_at" +
                ", od.created_by, c.name AS cake_name, c.image AS cake_image, cs.production_date, cs.expiration_date " +
                "FROM sweet_cake.order_detail od " +
                "INNER JOIN sweet_cake.cake_store cs ON od.id_cake_store = cs.id " +
                "INNER JOIN sweet_cake.cake c ON cs.id_cake = c.id " +
                "WHERE od.id_order =" + orderId;
        try(Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                OrderDetailDto orderDetail = new OrderDetailDto();
                orderDetail.setId(rs.getInt("id"));
                orderDetail.setIdOrder(rs.getInt("id_order"));
                orderDetail.setIdCakeStore(rs.getInt("id_cake_store"));
                orderDetail.setCakeName(rs.getString("cake_name"));
                orderDetail.setCakeImage(rs.getString("cake_image"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setPrice(rs.getBigDecimal("price"));
                list.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public OrderDetail getOrderDetailById(int orderDetailId){
        OrderDetail orderDetail = new OrderDetail();
        String sql = "SELECT * FROM order_detail where id=?";
        try(Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,orderDetailId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                orderDetail.setId(rs.getInt("id"));
                orderDetail.setIdOrder(rs.getInt("id_order"));
                orderDetail.setIdCakeStore(rs.getInt("id_cake_store"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setPrice(rs.getBigDecimal("price"));
            }
            return orderDetail;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int orderDetailId){
        try(Connection conn = DatabaseUtil.getConnection()) {
            OrderDetail orderDetail =  this.getOrderDetailById(orderDetailId);

            CakeStoreDao cakeStoreDao = new CakeStoreDao();
            CakeStoreResponse cakeStoreResponse = cakeStoreDao.getCakeStoreById(orderDetail.getIdCakeStore());

            cakeStoreResponse.setQuantity(cakeStoreResponse.getQuantity() + orderDetail.getQuantity());
            updateCakeStoreQuantity(conn, cakeStoreResponse);

            OrderDao orderDao = new OrderDao();
            Order order = orderDao.getOrderById(orderDetail.getIdOrder());

            BigDecimal changeMoney = orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
            order.setTotalMoney(order.getTotalMoney().subtract(changeMoney));

            updateOrderTotalMoney(conn, order);

            deleteOrderDetail(conn, orderDetailId);

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean isSufficientQuantity(CakeStoreResponse cakeStoreResponse, OrderDetail orderDetail) {
        return cakeStoreResponse.getQuantity() >= orderDetail.getQuantity();
    }

    private boolean isSufficientQuantity(CakeStoreResponse cakeStoreResponse, int changeQuantity) {
        return cakeStoreResponse.getQuantity() >= changeQuantity;
    }

    private int getOldQuantity(OrderDetail orderDetail) throws SQLException {
        OrderDetail existingOrderDetail = this.getOrderDetailById(orderDetail.getId());
        return existingOrderDetail.getQuantity();
    }

    private void updateCakeStoreQuantity(Connection conn, CakeStoreResponse cakeStoreResponse)
            throws SQLException {
        String sql = "UPDATE sweet_cake.cake_store SET quantity = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cakeStoreResponse.getQuantity());
            ps.setInt(2, cakeStoreResponse.getId());
            ps.executeUpdate();
        }
    }

    private void updateOrderTotalMoney(Connection conn, Order order)
            throws SQLException {
        String sql = "UPDATE sweet_cake.order SET total_money = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, order.getTotalMoney());
            ps.setInt(2, order.getId());
            ps.executeUpdate();
        }
    }

    private void deleteOrderDetail(Connection conn, int orderDetailId) throws SQLException {
        String sql = "DELETE FROM order_detail WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderDetailId);
            ps.executeUpdate();
        }
    }

    private void insertOrderDetail(Connection conn, OrderDetail orderDetail)
            throws SQLException {
        String sql = "INSERT INTO sweet_cake.order_detail (id_cake_store, id_order, quantity," +
                " price, created_at, created_by) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderDetail.getIdCakeStore());
            ps.setInt(2, orderDetail.getIdOrder());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setBigDecimal(4, orderDetail.getPrice());
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            ps.setTimestamp(5, timestamp);
            ps.setString(6, orderDetail.getCreatedBy());
            ps.executeUpdate();
        }
    }

    private void updateOrderDetail(Connection conn, OrderDetail orderDetail) throws SQLException {
        String sql = "UPDATE sweet_cake.order_detail SET quantity = ?, price = ?, updated_at = ?, updated_by = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderDetail.getQuantity());
            ps.setBigDecimal(2, orderDetail.getPrice());

            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            ps.setTimestamp(3, timestamp);
            ps.setString(4, orderDetail.getUpdatedBy());
            ps.setInt(5, orderDetail.getId());
            ps.executeUpdate();
        }
    }
}
