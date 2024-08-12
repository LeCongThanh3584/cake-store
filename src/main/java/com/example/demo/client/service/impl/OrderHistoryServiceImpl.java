package com.example.demo.client.service.impl;

import com.example.demo.client.dao.OrderHistoryDao;
import com.example.demo.client.service.OrderHistoryService;
import com.example.demo.entity.OrderHistory;

import java.util.List;

public class OrderHistoryServiceImpl implements OrderHistoryService {

    private OrderHistoryDao orderHistoryDao = new OrderHistoryDao();

    @Override
    public List<OrderHistory> getAllOrderHistoryByIdOrder(int idOrder) {
        return orderHistoryDao.getAllOrderHistoryByIdOrder(idOrder);
    }
}
