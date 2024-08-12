package com.example.demo.admin.service;

import com.example.demo.admin.dto.OrderDetailDto;
import com.example.demo.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    void create(OrderDetail orderDetail);

    List<OrderDetailDto> getAllByOrder(int orderId);

    OrderDetail getOrderDetailById(int orderDetailId);

    void update(OrderDetail orderDetail);

    void delete(int orderDetailId);
}
