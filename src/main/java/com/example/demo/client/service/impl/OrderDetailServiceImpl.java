package com.example.demo.client.service.impl;

import com.example.demo.client.dao.OrderDetailDao;
import com.example.demo.client.dto.OrderDetailDto;
import com.example.demo.client.service.OrderDetailService;

import java.util.List;

public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailDao orderDetailDao = new OrderDetailDao();

    @Override
    public List<OrderDetailDto> getOrderDetailByIdOrder(int idOrder) {
        return orderDetailDao.getOrderDetailByOrder(idOrder);
    }
}
