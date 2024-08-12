package com.example.demo.client.service;

import com.example.demo.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryService {

    List<OrderHistory> getAllOrderHistoryByIdOrder(int idOrder);

}
