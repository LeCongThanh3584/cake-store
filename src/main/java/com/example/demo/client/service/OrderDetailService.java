package com.example.demo.client.service;

import com.example.demo.client.dto.OrderDetailDto;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetailDto> getOrderDetailByIdOrder(int idOrder);

}
