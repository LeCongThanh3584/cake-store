package com.example.demo.client.service;

import com.example.demo.client.dto.OrderViewDto;
import com.example.demo.entity.Address;

import java.util.List;

public interface CheckoutService {
    List<OrderViewDto> CreateOrder(Integer userId, Address address, String[] cartIds);

    void saveOrders(Integer userId, String username, Address address, String[] cartIds);
}