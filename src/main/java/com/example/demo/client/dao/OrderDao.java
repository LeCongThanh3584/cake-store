package com.example.demo.client.dao;

import com.example.demo.client.dto.CartViewDto;
import com.example.demo.client.dto.OrderDto;
import com.example.demo.client.dto.OrderViewDto;
import com.example.demo.entity.Order;
import com.example.demo.util.DatabaseUtil;
import com.example.demo.util.StringUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final String CREATE_ORDER = "insert into `order`" +
            "(id_store,id_admin,id_user,code,reciver,phone,address,total_money,created_at,created_by)\n" +
            "values (?,1,?,?,?,?,?,?,?,?)";

    private static final String ADD_ORDER_DETAIL = "insert into order_detail" +
            "(id_cake_store,id_order,quantity,price,created_at,created_by)\n" +
            "values (?,?,?,?,?,?)";

    private static final String ADD_ORDER_HISTORY = "insert into order_history" +
            "(id_order, description, status, created_at, created_by)\n" +
            "values (?,?,?,?,?)";

    private static final String GET_ORDER_BY_USER = "SELECT o.id, o.code, st.name, oh.status, o.phone, o.total_money " +
            "            from sweet_cake.order o INNER JOIN sweet_cake.user u ON o.id_user = u.id " +
            "            INNER JOIN sweet_cake.store st ON st.id = o.id_store " +
            "            INNER JOIN sweet_cake.order_history oh ON o.id = oh.id_order where u.id = ?" +
            "            AND oh.id = (SELECT id FROM order_history WHERE id_order = o.id  ORDER BY created_at DESC LIMIT 1)" +
            "            AND oh.status = ? AND CASE WHEN ? IS NOT NULL" +
            "            THEN (o.code like CONCAT('%', ? , '%')" +
            "                        OR st.name like CONCAT('%', ? , '%')" +
            "                        OR o.phone like CONCAT('%', ? , '%'))" +
            "            ELSE 1 = 1 END LIMIT ?, ?;";

    private static final String GET_TOTAL_ORDER_BY_USER = "SELECT count(*) " +
            "            from sweet_cake.order o INNER JOIN sweet_cake.user u ON o.id_user = u.id " +
            "            INNER JOIN sweet_cake.store st ON st.id = o.id_store " +
            "            INNER JOIN sweet_cake.order_history oh ON o.id = oh.id_order where u.id = ?" +
            "            AND oh.id = (SELECT id FROM order_history WHERE id_order = o.id  ORDER BY created_at DESC LIMIT 1)" +
            "            AND oh.status = ? " +
            "            AND CASE WHEN ? IS NOT NULL\n" +
            "            THEN (o.code like CONCAT('%', ? , '%')" +
            "                        OR st.name like CONCAT('%', ? , '%')" +
            "                        OR o.phone like CONCAT('%', ? , '%'))" +
            "            ELSE 1 = 1 END ";

    private static final String GET_ORDER_BY_ID = "select o.id, s.name, shipper.username as shipper, ad.username  as admin, " +
            "o.code,o.reciver, o.phone, o.total_money, o.address, oh.status " +
            "from sweet_cake.order o inner join store s ON s.id = o.id_store " +
            "left join sweet_cake.user shipper on o.id_shipper = shipper.id left join sweet_cake.user ad on o.id_admin = ad.id " +
            "inner join order_history oh on oh.id_order = o.id " +
            "WHERE o.id = ? and oh.id = (SELECT id FROM order_history WHERE id_order = o.id  ORDER BY created_at DESC LIMIT 1);";

    public OrderDto getOrderById(int orderId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                OrderDto order = new OrderDto();
                order.setId(resultSet.getInt(1));
                order.setStoreName(resultSet.getString(2));
                order.setShipperName(resultSet.getString(3));
                order.setAdminName(resultSet.getString(4));
                order.setCode(resultSet.getString(5));
                order.setReciver(resultSet.getString(6));
                order.setPhone(resultSet.getString(7));
                order.setTotalMoney(resultSet.getBigDecimal(8));
                order.setAddress(resultSet.getString(9));
                order.setStatus(resultSet.getInt(10));
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int totalRecord(String search, int status, int userId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_TOTAL_ORDER_BY_USER);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, status);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            preparedStatement.setString(6, search);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<OrderDto> getOrderByUser(int page, int pageSize, String search, int status, int userId) {
        List<OrderDto> list = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_USER);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, status);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            preparedStatement.setString(6, search);
            preparedStatement.setInt(7, page);
            preparedStatement.setInt(8, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderDto orderDto = new OrderDto();
                orderDto.setOrderId(resultSet.getLong("id"));
                orderDto.setCode(resultSet.getString("code"));
                orderDto.setStoreName(resultSet.getString("st.name"));
                orderDto.setPhone(resultSet.getString("phone"));
                orderDto.setStatus(resultSet.getInt("status"));
                orderDto.setTotalMoney(resultSet.getBigDecimal("total_money"));
                list.add(orderDto);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void CreateOrder(Integer userId, String username, OrderViewDto orderView) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, orderView.getIdStore());
            statement.setInt(2, userId);
            statement.setString(3, StringUtil.generateString(6));
            statement.setString(4, orderView.getReceiver());
            statement.setString(5, orderView.getPhone());
            statement.setString(6, orderView.getAddress());
            statement.setBigDecimal(7, orderView.getTotalMoney());
            statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(9, username);

            int result = statement.executeUpdate();
            if (!(result > 0)) {
                throw new SQLException("create order failed");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            orderView.setId(resultSet.getInt(1));

            for (CartViewDto orderDetail : orderView.getOrderDetails()) {
                addOrderDetail(connection, orderView, orderDetail, username);
            }
            addOrderHistory(connection, orderView.getId(), "NEW CLIENT ORDER", 0, username);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addOrderHistory(Connection connection, Integer orderId, String description, Integer status, String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER_HISTORY);
        preparedStatement.setInt(1, orderId);
        preparedStatement.setString(2, description);
        preparedStatement.setInt(3, status);
        preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setString(5, username);

        int newResult = preparedStatement.executeUpdate();
        if (!(newResult > 0)) {
            throw new SQLException("add order history failed");
        }
    }

    private void addOrderDetail(Connection connection, OrderViewDto orderView, CartViewDto orderDetail, String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER_DETAIL);
        preparedStatement.setInt(1, orderDetail.getCakeStoreId());
        preparedStatement.setInt(2, orderView.getId());
        preparedStatement.setInt(3, orderDetail.getQuantity());
        preparedStatement.setBigDecimal(4, orderDetail.getPrice());
        preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setString(6, username);

        int newResult = preparedStatement.executeUpdate();
        if (!(newResult > 0)) {
            throw new SQLException("add order detail failed");
        }
    }
}
