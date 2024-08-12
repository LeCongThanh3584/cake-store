package com.example.demo.shipper.service.Impl;

import com.example.demo.shipper.dao.OrderDao;
import com.example.demo.shipper.dto.*;
import com.example.demo.shipper.service.OrderService;
import com.example.demo.util.Constant;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao = new OrderDao();

    @Override
    public Integer CountOrders(Integer shipperId, Integer storeId, Integer orderState, String query) {
        return orderDao.CountOrder(shipperId, storeId, orderState, query);
    }

    @Override
    public List<OrderDto> Search(Integer shipperId, ViewPage viewPage, Integer orderState) {
        return orderDao.SearchOrder(shipperId,
                viewPage.getStoreId(),
                orderState,
                viewPage.getQuery(),
                viewPage.getPage(),
                viewPage.getPageSize()
        );
    }

    @Override
    public String selectOrder(Integer shipperId, String shipperName, Integer orderId, String orderMessage) {

        try {
            List<OrderDetailDto> orderDetails = orderDao.getOrderDetail(orderId);
            List<OrderHistoryDto> orderHistory = orderDao.getOrderHistory(orderId);
            boolean expired = orderDetails.stream()
                    .anyMatch(item -> item.getExpirationDate().isBefore(LocalDateTime.now()));
            boolean isConfirmed = orderHistory.stream()
                    .max(Comparator.comparing(OrderHistoryDto::getCreatedAt))
                    .orElse(new OrderHistoryDto())
                    .getStatus()
                    .equals(Constant.ORDER_STATE.CONFIRMED);
            if (expired) {
                return "Some items are expired";
            } else if (!isConfirmed) {
                return "This order is already confirmed";
            }
            orderDao.selectOrder(shipperId, shipperName, orderId, orderMessage);
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String deliverOrder(String shipperName, Integer orderId, String orderMessage) {
        orderDao.deliverOrder(shipperName, orderId, orderMessage);
        return null;
    }

    @Override
    public OrderViewDto getOrderView(Integer shipperId, Integer orderId) {
        return orderDao.getOrderView(shipperId, orderId);
    }
}
