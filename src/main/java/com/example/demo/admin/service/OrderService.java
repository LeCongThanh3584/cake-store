package com.example.demo.admin.service;

import com.example.demo.admin.dto.OrderChangeDto;
import com.example.demo.admin.dto.OrderHistoryCountDto;
import com.example.demo.admin.dto.OrderStatisticDto;
import com.example.demo.admin.dto.OrderDto;
import com.example.demo.admin.dto.PagnitedList;
import com.example.demo.entity.Order;

import java.util.List;

public interface OrderService {
    PagnitedList<OrderDto> getAllOrder(int storeId,String search,int pageNo, int pageSize);

    int createOrder(Order order);

    OrderDto getOrderDtoById(int orderId);

    Order getById(int orderId);

    boolean update(Order order);

    List<OrderHistoryCountDto> getOrderHistoryCount(Integer storeId, String dateType);

    List<OrderStatisticDto> getOrderStatistics(Integer storeId, String dateType);

    OrderStatisticDto getOrderStatistic(Integer storeId, String dateType);

    List<OrderChangeDto> getLatestChange(Integer storeId, String dateType);
}
