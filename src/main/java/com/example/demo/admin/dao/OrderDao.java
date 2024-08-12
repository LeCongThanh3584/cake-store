package com.example.demo.admin.dao;

import com.example.demo.admin.dto.OrderChangeDto;
import com.example.demo.admin.dto.OrderHistoryCountDto;
import com.example.demo.admin.dto.OrderStatisticDto;
import com.example.demo.admin.dto.OrderDto;
import com.example.demo.entity.Order;
import com.example.demo.util.Constant;
import com.example.demo.util.DatabaseUtil;
import com.example.demo.util.DateUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDao {

    public int create(Order order) {
        int generatedOrderId = -1;

        try (Connection conn = DatabaseUtil.getConnection()) {
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            String sql = "INSERT INTO `order` (id_store, id_admin, id_user, code, reciver, phone, address," +
                    "total_money, created_at, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, order.getIdStore());
                ps.setInt(2, order.getIdAdmin());
                if (order.getIdUser() != null) {
                    ps.setInt(3, order.getIdUser());
                } else {
                    ps.setNull(3, Types.INTEGER);
                }
                ps.setString(4, order.getCode());
                ps.setString(5, order.getReciver());
                ps.setString(6, order.getPhone());
                ps.setString(7, order.getAddress());
                ps.setBigDecimal(8, order.getTotalMoney());
                ps.setTimestamp(9, timestamp);
                ps.setString(10, order.getCreatedBy());
                ps.executeUpdate();
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedOrderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }

            String sql1 = "INSERT INTO sweet_cake.order_history (id_order,status,created_at,created_by) " +
                    "VALUES(?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setInt(1, generatedOrderId);
                ps.setInt(2, Constant.ORDER_STATE.PENDING);
                ps.setTimestamp(3, timestamp);
                ps.setString(4, order.getCreatedBy());
                ps.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedOrderId;
    }

    public boolean update(Order order) {
        String sql = "update `order` set id_store=?,id_admin=?,id_user=?,code=?,reciver=?,phone=?," +
                "address=?,total_money=?,updated_at=?,updated_by=? where id = ?";
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, order.getIdStore());
            ps.setInt(2, order.getIdAdmin());
            if (order.getIdUser() != null) {
                ps.setInt(3, order.getIdUser());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, order.getCode());
            ps.setString(5, order.getReciver());
            ps.setString(6, order.getPhone());
            ps.setString(7, order.getAddress());
            ps.setBigDecimal(8, order.getTotalMoney());
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            ps.setTimestamp(9, timestamp);
            ps.setString(10, order.getUpdatedBy());
            ps.setInt(11, order.getId());
            ps.executeUpdate();
            conn.commit();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int totalRecord(int storeId, String search) {
        String sql = "SELECT COUNT(*) FROM sweet_cake.order " +
                "LEFT JOIN user ON sweet_cake.order.id_user = user.id";
        if (storeId > 0) {
            sql += " WHERE sweet_cake.order.id_store = ?";
        }
        if (search != null && !search.isEmpty()) {
            sql += (storeId > 0) ? " AND (user.full_name LIKE ? OR sweet_cake.order.reciver LIKE ?)" :
                    " WHERE (user.full_name LIKE ? OR sweet_cake.order.reciver LIKE ?)";
        }
        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (storeId > 0) {
                ps.setInt(paramIndex++, storeId);
            }
            if (search != null && !search.isEmpty()) {
                String searchString = "%" + search + "%";
                ps.setString(paramIndex++, searchString);
                ps.setString(paramIndex, searchString);
            }
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public List<OrderDto> getSearchOrdersByPage(int storeId,String search,int pageNo, int pageSize){
        List<OrderDto> list = new ArrayList<>();
        String sql = "SELECT o.*, u.full_name, " +
                "(SELECT h.status " +
                " FROM sweet_cake.order_history h " +
                " WHERE h.id_order = o.id " +
                " ORDER BY h.created_at DESC " +
                " LIMIT 1) AS latest_status " +
                "FROM sweet_cake.order o " +
                "LEFT JOIN user u ON o.id_user = u.id " +
                "WHERE (CASE WHEN ? > 0 THEN o.id_store = ? ELSE TRUE END) " +
                "AND (CASE WHEN ? IS NOT NULL AND ? <> '' THEN u.full_name LIKE ? OR o.reciver LIKE ? ELSE TRUE END) " +
                "ORDER BY o.created_at DESC " +
                "LIMIT ?, ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, storeId);
            ps.setInt(2, storeId);
            ps.setString(3, search);
            ps.setString(4, search);
            ps.setString(5, "%" + search + "%");
            ps.setString(6, "%" + search + "%");
            ps.setInt(7, pageNo);
            ps.setInt(8, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                OrderDto order = new OrderDto();
                order.setId(rs.getInt("id"));
                order.setIdStore(rs.getInt("id_store"));
                order.setIdAdmin(rs.getInt("id_admin"));
                order.setIdShipper(rs.getInt("id_shipper"));
                order.setIdUser(rs.getInt("id_user"));
                order.setCode(rs.getString("code"));
                order.setReciver(rs.getString("reciver"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                order.setStatus(rs.getInt("latest_status"));
                order.setTotalMoney(rs.getBigDecimal("total_money"));
                list.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public OrderDto getOrderDtoById(int id){
        String sql = "SELECT o.*, s.name AS storeName, u1.full_name AS userName, " +
                 "u2.full_name AS shipperName,u3.full_name AS adminName " +
                 "FROM sweet_cake.order o LEFT JOIN store s ON o.id_store = s.id " +
                 "LEFT JOIN user u1 ON o.id_user = u1.id " +
                 "LEFT JOIN user u2 ON o.id_shipper = u2.id " +
                 "LEFT JOIN user u3 ON o.id_admin = u3.id " +
                 "WHERE o.id = ?";
        OrderDto order = new OrderDto();
        try(Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order.setId(rs.getInt("id"));
                order.setIdStore(rs.getInt("id_store"));
                order.setStoreName(rs.getString("storeName"));
                order.setIdAdmin(rs.getInt("id_admin"));
                order.setAdminName(rs.getString("adminName"));
                order.setIdShipper(rs.getInt("id_shipper"));
                order.setShipperName(rs.getString("shipperName"));
                order.setIdUser(rs.getInt("id_user"));
                order.setUserName(rs.getString("userName"));
                order.setCode(rs.getString("code"));
                order.setReciver(rs.getString("reciver"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotalMoney(rs.getBigDecimal("total_money"));
                order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                order.setCreatedBy(rs.getString("created_by"));
            }
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order getOrderById(int id){
        String sql = "SELECT o.* FROM sweet_cake.order o WHERE o.id = ?";
        Order order = new Order();
        try(Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                order.setId(rs.getInt("id"));
                order.setIdStore(rs.getInt("id_store"));
                order.setIdAdmin(rs.getInt("id_admin"));
                order.setIdShipper(rs.getInt("id_shipper"));
                order.setIdUser(rs.getInt("id_user"));
                order.setCode(rs.getString("code"));
                order.setReciver(rs.getString("reciver"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotalMoney(rs.getBigDecimal("total_money"));
                order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                order.setCreatedBy(rs.getString("created_by"));
            }
            return order;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<OrderHistoryCountDto> getOrderHistoryCount(Integer storeId, String dateType) {
        List<OrderHistoryCountDto> list = new ArrayList<>();
        String sql = "select count(a.id) as count,a.status from order_history a\n" +
                "    join `order` on a.id_order = `order`.id\n" +
                "    where a.created_at >= ?  and `order`.deleted_at is null" +
                "    and a.created_at = (select max(b.created_at)\n" +
                "                      from order_history b\n" +
                "                      where a.id_order = b.id_order)";
        if (storeId != null) {
            sql += " and `order`.id_store = " + storeId;
        }
        sql += " group by a.status;";

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(DateUtil.getDateType(dateType)));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int status = rs.getInt("status");
                int count = rs.getInt("count");
                list.add(new OrderHistoryCountDto(status, count));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderChangeDto> getLatestChange(Integer storeId, String dateType) {
        List<OrderChangeDto> list = new ArrayList<>();
        String sql = "select store.name,a.*,`order`.* from `order`\n" +
                "    join store on `order`.id_store = store.id\n" +
                "    join order_history a  on `order`.id = a.id_order\n" +
                "    where a.created_at >= ?\n" +
                "    and a.created_at = (select max(b.created_at)\n" +
                "                        from order_history b\n" +
                "                        where a.id_order = b.id_order)";
        if (storeId != null) {
            sql += "\n and `order`.id_store = " + storeId;
        }
        sql += "\n order by a.created_at desc limit 5;";

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(DateUtil.getDateType(dateType)));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderChangeDto dto = new OrderChangeDto();
                dto.setId(rs.getInt("order.id"));
                dto.setStoreName(rs.getString("store.name"));
                dto.setStatus(rs.getInt("a.status"));
                dto.setCreatedAt(rs.getTimestamp("a.created_at").toLocalDateTime());
                dto.setCreatedBy(rs.getString("a.created_by"));

                dto.setReceiver(rs.getString("reciver"));
                dto.setAddress(rs.getString("address"));
                dto.setCode(rs.getString("code"));
                dto.setTotalMoney(rs.getBigDecimal("total_money"));

                list.add(dto);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getStatisticString(Integer storeId, String dateType) {
        String query = "select %s(a.created_at) as stat_time, sum(`order`.total_money) as revenue,\n" +
                "    count(distinct `order`.id) as sale,\n" +
                "    count(distinct `order`.id_user) as customer from `order`\n" +
                "    join order_history a on `order`.id = a.id_order\n" +
                "    where a.created_at >= ?  and `order`.deleted_at is null\n" +
                "    and a.created_at = (select max(b.created_at)\n" +
                "                        from order_history b\n" +
                "                        where a.id_order = b.id_order)\n" +
                "    and a.status = 3\n";
        if (storeId != null) {
            query += " and `order`.id_store = " + storeId;
        }
        query += " group by %s (a.created_at);";
        if (Objects.equals(dateType, "week") || Objects.equals(dateType, "month")) {
            query = String.format(query, "date", "date");
        } else {
            query = String.format(query, "hour", "hour");
        }
        return query;
    }

    public List<OrderStatisticDto> getOrderStatistics(Integer storeId, String dateType) {
        String sql = getStatisticString(storeId, dateType);
        List<OrderStatisticDto> list = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(DateUtil.getDateType(dateType)));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderStatisticDto dto = new OrderStatisticDto();
                dto.setCustomer(rs.getInt("customer"));
                dto.setRevenue(rs.getInt("revenue"));
                dto.setSale(rs.getInt("sale"));

                if (dateType.equals("month") || dateType.equals("week")) {
                    dto.setStatTime(rs.getDate("stat_time"));
                } else {
                    dto.setStatTime(rs.getInt("stat_time"));
                }

                list.add(dto);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public OrderStatisticDto getOrderStatistic(Integer storeId, String dateType) {
        String sql = "select sum(`order`.total_money) as revenue,\n" +
                "    count(distinct `order`.id) as sale,\n" +
                "    count(distinct `order`.id_user) as customer from `order`\n" +
                "    join order_history a on `order`.id = a.id_order\n" +
                "    where a.created_at >= ?  and `order`.deleted_at is null\n" +
                "    and a.created_at = (select max(b.created_at)\n" +
                "                        from order_history b\n" +
                "                        where a.id_order = b.id_order)\n" +
                "    and a.status = 3\n";
        if (storeId != null) {
            sql += " and `order`.id_store = " + storeId;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(DateUtil.getDateType(dateType)));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                OrderStatisticDto dto = new OrderStatisticDto();
                dto.setCustomer(rs.getInt("customer"));
                dto.setRevenue(rs.getInt("revenue"));
                dto.setSale(rs.getInt("sale"));

                return dto;
            }
            return new OrderStatisticDto();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
