package com.example.demo.client.service;

import com.example.demo.client.dto.OrderDto;
import com.example.demo.entity.Order;

import java.util.List;

public interface OrderService {

    List<OrderDto> getOrdersByUserId(int page, int pageSize, String search, int status, int userId);

    int totalRecord(String search, int status, int userId);

    OrderDto getOrderById(int idOrder);

}
