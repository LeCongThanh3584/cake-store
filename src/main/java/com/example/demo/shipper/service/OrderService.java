package com.example.demo.shipper.service;

import com.example.demo.shipper.dto.OrderDto;
import com.example.demo.shipper.dto.OrderViewDto;
import com.example.demo.shipper.dto.ViewPage;

import java.util.List;

public interface OrderService {
    Integer CountOrders(Integer shipperId, Integer storeId, Integer orderState, String query);

    List<OrderDto> Search(Integer shipperId, ViewPage viewPage, Integer orderState);

    String selectOrder(Integer shipperId, String shipperName, Integer orderId, String orderMessage);

    String deliverOrder(String shipperName, Integer orderId, String orderMessage);

    OrderViewDto getOrderView(Integer shipperId, Integer orderId);
}
