package com.example.demo.client.dao;

import com.example.demo.client.dto.OrderDetailDto;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDao {

    private final String GET_ORDER_DETAIL_BY_ORDER = "select od.id, c.code, c.image, c.name, ct.production_date," +
            " ct.expiration_date, od.price, od.quantity " +
            "from sweet_cake.order o inner join sweet_cake.order_detail od on o.id = od.id_order " +
            "inner join cake_store ct on ct.id = od.id_cake_store  inner join cake c on c.id = ct.id_cake " +
            "where o.id = ?";

    public List<OrderDetailDto> getOrderDetailByOrder(int idOrder) {
        List<OrderDetailDto> objectList = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_DETAIL_BY_ORDER);
            preparedStatement.setInt(1, idOrder);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetailDto dto = new OrderDetailDto();
                dto.setOrderDetailId(resultSet.getInt(1));
                dto.setCakeCode(resultSet.getString(2));
                dto.setCakeImage(resultSet.getString(3));
                dto.setName(resultSet.getString(4));
//                dto.setProductionDate(resultSet.getString(5));
//                dto.setExpirationDate(resultSet.getString(6));
                dto.setPrice(resultSet.getBigDecimal(7));
                dto.setQuantity(resultSet.getLong(8));
                objectList.add(dto);
            }
            return objectList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
