package com.example.demo.admin.service;

import com.example.demo.admin.service.impl.OrderHistoryServiceImpl;
import com.example.demo.admin.dao.OrderHistoryDao;
import com.example.demo.entity.OrderHistory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class OrderHistoryServiceTest {
    @Mock
    private OrderHistoryDao orderHistoryDao;
    @InjectMocks
    private OrderHistoryServiceImpl orderHistoryService;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrderHistoryByOrderId(){
        OrderHistory orderHistory = Mockito.mock(OrderHistory.class);
        OrderHistory orderHistory1 = Mockito.mock(OrderHistory.class);
        List<OrderHistory> list = Arrays.asList(orderHistory,orderHistory1);
        int orderId = 1;
        Mockito.when(orderHistoryDao.getOrderHistoryByOrderId(orderId)).thenReturn(list);

        List<OrderHistory> orderHistoryList = orderHistoryService.getOrderHistoryByOrderId(orderId);

        Assert.assertEquals(list,orderHistoryList);
        Mockito.verify(orderHistoryDao,Mockito.times(1)).getOrderHistoryByOrderId(orderId);
    }

    @Test
    public void testCreateHistory(){
        OrderHistory orderHistory = Mockito.mock(OrderHistory.class);
        orderHistoryService.createHistory(orderHistory);
        Mockito.verify(orderHistoryDao, Mockito.times(1)).create(orderHistory);
    }

    @Test
    public void testGetOrderHistoryByOrderLimit(){
        OrderHistory orderHistory = Mockito.mock(OrderHistory.class);
        int id  = 10;
        Mockito.when(orderHistoryDao.getOrderHistoryId(id)).thenReturn(orderHistory);
        OrderHistory result = orderHistoryService.getOrderHistoryByOrderLimit(id);
        Assert.assertEquals(orderHistory,result);
        Mockito.verify(orderHistoryDao, Mockito.times(1)).getOrderHistoryId(id);
    }
}
