package com.example.demo.admin.service;

import com.example.demo.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryService {
    List<OrderHistory> getOrderHistoryByOrderId(int orderId);

    void createHistory(OrderHistory orderHistory);

    OrderHistory getOrderHistoryByOrderLimit(int orderId);
}
