package com.example.demo.shipper.dao;

import com.example.demo.shipper.dto.OrderDetailDto;
import com.example.demo.shipper.dto.OrderDto;
import com.example.demo.shipper.dto.OrderHistoryDto;
import com.example.demo.shipper.dto.OrderViewDto;
import com.example.demo.util.Constant;
import com.example.demo.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    static final String GET_ORDER_STATUS = "select * from order_history\n" +
            "        where order_history.deleted_at is null and order_history.id_order = ?\n" +
            "        order by created_at desc;";
    static final String GET_ORDER_DETAIL = "select order_detail.*,cake.*,cake_store.*\n" +
            "    from order_detail\n" +
            "    left join cake_store on  order_detail.id_cake_store = cake_store.id\n" +
            "    left join cake on cake_store.id_cake = cake.id\n" +
            "    where order_detail.deleted_at is null and order_detail.id_order = ?;\n";
    private static final String COUNT_ORDER = "select count(*)" +
            "    from `order`left join order_history a\n" +
            "    on `order`.id = a.id_order and a.created_at = (select max(b.created_at)\n" +
            "                                                    from order_history b\n" +
            "                                                    where a.id_order = b.id_order)\n" +
            "    where `order`.deleted_at is null and a.status = ?\n" +
            "    and (`order`.reciver like ? or `order`.code like ?\n" +
            "    or  `order`.phone like ? or `order`.address like ?)\n";
    private static final String SEARCH_ORDER = "select  `order`.*, a.status, a.description " +
            "    from `order`left join order_history a\n" +
            "    on `order`.id = a.id_order and a.created_at = (select max(b.created_at)\n" +
            "                                                    from order_history b\n" +
            "                                                    where a.id_order = b.id_order)\n" +
            "    where `order`.deleted_at is null and a.status = ?\n" +
            "    and (`order`.reciver like ? or `order`.code like ?\n" +
            "    or  `order`.phone like ? or `order`.address like ?)";
    private static final String INSERT_ORDER_HISTORY = "insert into " +
            "    order_history(id_order, description, status, created_at, created_by) \n" +
            "    values (?,?,?,?,?);\n";
    private static final String UPDATE_ORDER = "update `order` " +
            "set id_shipper = ?,updated_at = ?,updated_by = ? " +
            "where `order`.deleted_at is null and id =? ;";
    private static final String GET_ORDER = "select user.username, store.*,`order`.* from `order`\n" +
            "    left join store on `order`.id_store = store.id\n" +
            "    left join user on user.id = `order`.id_user\n" +
            "    where `order`.deleted_at is null and`order`.id = ?;";

    String getSearchStr(int shipperId, int storeId, String string) {
        String sqlStr = string;
        if (storeId > 0) {
            sqlStr += " and `order`.id_store = " + storeId;
        }
        if (shipperId > 0) {
            sqlStr += " and `order`.id_shipper = '" + shipperId + "'";
        }
        return sqlStr;
    }

    public Integer CountOrder(int shipperId, int storeId, Integer orderState, String query) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlStr = getSearchStr(shipperId, storeId, COUNT_ORDER);

        try {
            PreparedStatement statement = connection.prepareStatement(sqlStr);
            statement.setInt(1, orderState);
            statement.setString(2, "%" + query + "%");
            statement.setString(3, "%" + query + "%");
            statement.setString(4, "%" + query + "%");
            statement.setString(5, "%" + query + "%");

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<OrderDto> SearchOrder(Integer shipperId, Integer storeId, Integer orderState, String query, Integer page, Integer pageSize) {
        Connection connection = DatabaseUtil.getConnection();
        List<OrderDto> list = new ArrayList<>();
        String sqlStr = getSearchStr(shipperId, storeId, SEARCH_ORDER);
        sqlStr += " order by `order`.created_at desc limit " + pageSize
                + " offset " + (page - 1) * pageSize;
        try {
            PreparedStatement statement = connection.prepareStatement(sqlStr);
            statement.setInt(1, orderState);
            statement.setString(2, "%" + query + "%");
            statement.setString(3, "%" + query + "%");
            statement.setString(4, "%" + query + "%");
            statement.setString(5, "%" + query + "%");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                OrderDto order = new OrderDto();
                order.setId(rs.getInt("order.id"));
                order.setIdStore(rs.getInt("order.id_store"));
                order.setIdAdmin(rs.getInt("order.id_admin"));
                order.setIdShipper(rs.getInt("order.id_shipper"));
                order.setIdUser(rs.getInt("order.id_user"));
                order.setCode(rs.getString("order.code"));
                order.setReciver(rs.getString("order.reciver"));
                order.setPhone(rs.getString("order.phone"));
                order.setAddress(rs.getString("order.address"));
                order.setCreatedAt(rs.getTimestamp("order.created_at").toLocalDateTime());
                order.setTotalMoney(rs.getBigDecimal("order.total_money"));

                order.setStatus(rs.getInt("a.status"));
                order.setDescription(rs.getString("a.description"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void selectOrder(Integer shipperId, String shipperName, Integer orderId, String orderMessage) {
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER);
            statement.setInt(1, shipperId);
            statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(3, shipperName);
            statement.setInt(4, orderId);

            int result = statement.executeUpdate();
            if (!(result > 0)) {
                throw new RuntimeException("update order failed");
            }
            PreparedStatement newStatement = connection.prepareStatement(INSERT_ORDER_HISTORY);
            newStatement.setInt(1, orderId);
            newStatement.setString(2, orderMessage);
            newStatement.setInt(3, Constant.ORDER_STATE.DELIVERING);
            newStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            newStatement.setString(5, shipperName);

            int newResult = newStatement.executeUpdate();
            if (!(newResult > 0)) {
                throw new RuntimeException("select order failed");
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deliverOrder(String shipperName, Integer orderId, String orderMessage) {
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement newStatement = connection.prepareStatement(INSERT_ORDER_HISTORY);
            newStatement.setInt(1, orderId);
            newStatement.setString(2, orderMessage);
            newStatement.setInt(3, Constant.ORDER_STATE.DELIVERED);
            newStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            newStatement.setString(5, shipperName);

            int newResult = newStatement.executeUpdate();
            if (!(newResult > 0)) {
                throw new RuntimeException("deliver order failed");
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<OrderHistoryDto> getOrderHistory(Integer orderId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            List<OrderHistoryDto> list = new ArrayList<>();
            PreparedStatement newStatement = connection.prepareStatement(GET_ORDER_STATUS);
            newStatement.setInt(1, orderId);

            ResultSet result = newStatement.executeQuery();
            while (result.next()) {
                OrderHistoryDto order = new OrderHistoryDto();
                order.setId(result.getInt("id"));
                order.setStatus(result.getInt("status"));
                order.setDescription(result.getString("description"));

                order.setCreatedAt(result.getTimestamp("created_at").toLocalDateTime());
                order.setCreatedBy(result.getString("created_by"));
                list.add(order);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderDetailDto> getOrderDetail(Integer orderId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            List<OrderDetailDto> list = new ArrayList<>();
            PreparedStatement newStatement = connection.prepareStatement(GET_ORDER_DETAIL);
            newStatement.setInt(1, orderId);

            ResultSet result = newStatement.executeQuery();
            while (result.next()) {
                OrderDetailDto orderDetail = new OrderDetailDto();
                orderDetail.setName(result.getString("cake.name"));
                orderDetail.setImage(result.getString("cake.image"));
                orderDetail.setPrice(result.getBigDecimal("order_detail.price"));
                orderDetail.setQuantity(result.getInt("order_detail.quantity"));

                orderDetail.setExpirationDate(result.getTimestamp("cake_store.expiration_date").toLocalDateTime());
                orderDetail.setCreatedAt(result.getTimestamp("order_detail.created_at").toLocalDateTime());
                orderDetail.setCreatedBy(result.getString("order_detail.created_by"));
                list.add(orderDetail);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OrderViewDto getOrderView(Integer shipperId, Integer orderId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement newStatement = connection.prepareStatement(GET_ORDER);
            newStatement.setInt(1, orderId);

            ResultSet result = newStatement.executeQuery();
            if (!result.next()) {
                throw new RuntimeException("get order failed");
            }
            OrderViewDto order = new OrderViewDto();
            order.setId(result.getInt("order.id"));
            order.setIdStore(result.getInt("order.id_store"));
            order.setIdAdmin(result.getInt("order.id_admin"));
            order.setIdShipper(result.getInt("order.id_shipper"));
            order.setIdUser(result.getInt("order.id_user"));

            order.setCode(result.getString("order.code"));
            order.setReceiver(result.getString("order.reciver"));
            order.setPhone(result.getString("order.phone"));
            order.setAddress(result.getString("order.address"));
            order.setTotalMoney(result.getBigDecimal("order.total_money"));

            order.setStoreName(result.getString("store.name"));
            order.setStoreAddress(result.getString("store.address"));
            order.setUsername(result.getString("username"));

            order.setOrderHistory(getOrderHistory(orderId));
            order.setOrderDetails(getOrderDetail(orderId));
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
