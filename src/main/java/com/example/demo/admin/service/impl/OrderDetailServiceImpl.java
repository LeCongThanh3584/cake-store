package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.OrderDetailDao;
import com.example.demo.admin.dto.OrderDetailDto;
import com.example.demo.admin.service.OrderDetailService;
import com.example.demo.entity.OrderDetail;

import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderDetailDao orderDetailDao = new OrderDetailDao();

    @Override
    public void create(OrderDetail orderDetail) {
        orderDetailDao.create(orderDetail);
    }

    @Override
    public List<OrderDetailDto> getAllByOrder(int orderId) {
        return orderDetailDao.getOrderDetailsByOrder(orderId);
    }

    @Override
    public OrderDetail getOrderDetailById(int orderDetailId) {
        return orderDetailDao.getOrderDetailById(orderDetailId);
    }

    @Override
    public void update(OrderDetail orderDetail) {
        orderDetailDao.update(orderDetail);
    }

    @Override
    public void delete(int orderDetailId) {
        orderDetailDao.delete(orderDetailId);
    }
}
