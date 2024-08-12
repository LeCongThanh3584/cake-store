package com.example.demo.admin.service;


import com.example.demo.admin.dao.OrderDetailDao;
import com.example.demo.admin.dto.OrderDetailDto;
import com.example.demo.admin.service.impl.OrderDetailServiceImpl;
import com.example.demo.entity.OrderDetail;
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

public class OrderDetailServiceTest {
    @Mock
    private OrderDetailDao orderDetailDao;
    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrderDetail(){
        OrderDetail orderDetail = Mockito.mock(OrderDetail.class);
        orderDetailService.create(orderDetail);
        Mockito.verify(orderDetailDao, Mockito.times(1)).create(orderDetail);
    }

    @Test
    public void testUpdateOrderDetail(){
        OrderDetail orderDetail = Mockito.mock(OrderDetail.class);
        orderDetailService.update(orderDetail);
        Mockito.verify(orderDetailDao, Mockito.times(1)).update(orderDetail);
    }

    @Test
    public void testDeleteOrderDetail(){
        int orderDetailId = 1;
        orderDetailService.delete(orderDetailId);
        Mockito.verify(orderDetailDao, Mockito.times(1)).delete(orderDetailId);
    }

    @Test
    public void testGetOrderDetailById(){
        int orderDetailId = 1;
        OrderDetail orderDetail = Mockito.mock(OrderDetail.class);
        Mockito.when(orderDetailDao.getOrderDetailById(orderDetailId)).thenReturn(orderDetail);
        OrderDetail result = orderDetailService.getOrderDetailById(orderDetailId);

        Assert.assertEquals(orderDetail, result);
        Mockito.verify(orderDetailDao, Mockito.times(1)).getOrderDetailById(orderDetailId);
    }

    @Test
    public void testGetAllOrderDetail(){
        int orderId = 1;
        OrderDetailDto orderDetailDto = Mockito.mock(OrderDetailDto.class);
        OrderDetailDto orderDetailDto1 = Mockito.mock(OrderDetailDto.class);
        List<OrderDetailDto> expectedOrderDetails = Arrays.asList(orderDetailDto,orderDetailDto1);
        Mockito.when(orderDetailDao.getOrderDetailsByOrder(orderId)).thenReturn(expectedOrderDetails);

        List<OrderDetailDto> actualOrderDetails = orderDetailService.getAllByOrder(orderId);

        Assert.assertEquals(expectedOrderDetails, actualOrderDetails);
        Mockito.verify(orderDetailDao, Mockito.times(1)).getOrderDetailsByOrder(orderId);
    }
}
