package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.OrderHistoryDao;
import com.example.demo.admin.service.OrderHistoryService;
import com.example.demo.entity.OrderHistory;

import java.util.List;

public class OrderHistoryServiceImpl implements OrderHistoryService {
    private OrderHistoryDao orderHistoryDao = new OrderHistoryDao();

    @Override
    public List<OrderHistory> getOrderHistoryByOrderId(int orderId) {
        return orderHistoryDao.getOrderHistoryByOrderId(orderId);
    }

    @Override
    public void createHistory(OrderHistory orderHistory) {
        orderHistoryDao.create(orderHistory);
    }

    @Override
    public OrderHistory getOrderHistoryByOrderLimit(int orderId) {
        return orderHistoryDao.getOrderHistoryId(orderId);
    }
}
