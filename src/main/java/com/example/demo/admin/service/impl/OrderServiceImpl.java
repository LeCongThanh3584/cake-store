package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.OrderDao;
import com.example.demo.admin.dto.OrderChangeDto;
import com.example.demo.admin.dto.OrderHistoryCountDto;
import com.example.demo.admin.dto.OrderStatisticDto;
import com.example.demo.admin.dto.OrderDto;
import com.example.demo.admin.dto.PagnitedList;
import com.example.demo.admin.service.OrderService;
import com.example.demo.entity.Order;

import java.util.List;


public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDao();

    @Override
    public PagnitedList<OrderDto> getAllOrder(int storeId,String search,int pageNo, int pageSize) {
        int totalOrders = orderDao.totalRecord(storeId,search);
        int offset = (pageNo - 1) * pageSize;
        List<OrderDto> listOrderPage = orderDao.getSearchOrdersByPage(storeId,search,offset,pageSize);
        PagnitedList<OrderDto> pagnitedList = new PagnitedList<>();
        pagnitedList.setList(listOrderPage);
        pagnitedList.setPageNo(pageNo);
        pagnitedList.setTotal(totalOrders);
        pagnitedList.setPageSize(pageSize);
        pagnitedList.setTotalPages((int) Math.ceil((double) totalOrders / pageSize));
        return pagnitedList;
    }

    @Override
    public int createOrder(Order order) {
        return orderDao.create(order);
    }

    @Override
    public OrderDto getOrderDtoById(int orderId) {
        return orderDao.getOrderDtoById(orderId);
    }

    @Override
    public Order getById(int orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Override
    public boolean update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public List<OrderHistoryCountDto> getOrderHistoryCount(Integer storeId, String dateType) {
        return orderDao.getOrderHistoryCount(storeId, dateType);
    }

    @Override
    public List<OrderStatisticDto> getOrderStatistics(Integer storeId, String dateType) {
        return orderDao.getOrderStatistics(storeId, dateType);
    }

    @Override
    public OrderStatisticDto getOrderStatistic(Integer storeId, String dateType) {
        return orderDao.getOrderStatistic(storeId, dateType);
    }

    @Override
    public List<OrderChangeDto> getLatestChange(Integer storeId, String dateType) {
        return orderDao.getLatestChange(storeId, dateType);
    }
}
