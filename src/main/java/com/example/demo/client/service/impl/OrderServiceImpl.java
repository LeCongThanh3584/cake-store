package com.example.demo.client.service.impl;

import com.example.demo.client.dao.OrderDao;
import com.example.demo.client.dto.OrderDto;
import com.example.demo.client.service.OrderService;
import com.example.demo.entity.Order;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao = new OrderDao();

    @Override
    public List<OrderDto> getOrdersByUserId(int page, int pageSize, String search, int status, int userId) {
        return orderDao.getOrderByUser(page, pageSize, search, status, userId);
    }

    @Override
    public int totalRecord(String search, int status, int userId) {
        return orderDao.totalRecord(search, status, userId);
    }

    @Override
    public OrderDto getOrderById(int idOrder) {
        return orderDao.getOrderById(idOrder);
    }
}
