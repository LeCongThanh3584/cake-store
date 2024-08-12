package com.example.demo.admin.service;

import com.example.demo.admin.dao.OrderDao;
import com.example.demo.admin.dto.OrderDto;
import com.example.demo.admin.dto.PagnitedList;
import com.example.demo.admin.service.impl.OrderServiceImpl;
import com.example.demo.entity.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


public class OrderServiceTest {
    @Mock
    private OrderDao orderDao;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() {
        Order order = Mockito.mock(Order.class);
        order.setIdStore(1);
        order.setAddress("Hà Đông, Hà Nội");
        order.setReciver("Người Nhận");
        order.setIdAdmin(1);
        order.setCode("123456");
        order.setPhone("3203918935");
        order.setTotalMoney(new BigDecimal(0));
        order.setCreatedBy("minhquy");
        int expectedOrderId = 123;

        Mockito.when(orderDao.create(order)).thenReturn(expectedOrderId);

        int orderId = orderService.createOrder(order);

        Assert.assertEquals(expectedOrderId, orderId);
    }

    @Test
    public void testGetOrderDtoById() {
        int orderId = 1;
        OrderDto expectedOrderDto = Mockito.mock(OrderDto.class);
        Mockito.when(orderDao.getOrderDtoById(orderId)).thenReturn(expectedOrderDto);

        OrderDto actualOrderDto = orderService.getOrderDtoById(orderId);

        Assert.assertEquals(expectedOrderDto, actualOrderDto);
        Mockito.verify(orderDao, Mockito.times(1)).getOrderDtoById(orderId);
    }

    @Test
    public void testGetOrderById(){
        int orderId = 1;
        Order expectedOrder = Mockito.mock(Order.class);
        Mockito.when(orderDao.getOrderById(orderId)).thenReturn(expectedOrder);

        Order actualOrder = orderService.getById(orderId);

        Assert.assertEquals(expectedOrder, actualOrder);
        Mockito.verify(orderDao, Mockito.times(1)).getOrderById(orderId);
    }

    @Test
    public void testUpdateOrder() {
        Order order = Mockito.mock(Order.class);
        order.setId(1);
        order.setIdStore(1);
        order.setAddress("Hà Đông, Hà Nội");
        order.setReciver("Người Nhận");
        order.setIdAdmin(1);
        order.setCode("123456");
        order.setPhone("3203918935");
        order.setTotalMoney(new BigDecimal(100));
        order.setCreatedBy("minhquy");

        Mockito.when(orderDao.update(order)).thenReturn(true);

        boolean check = orderService.update(order);

        Assert.assertTrue(check);
    }

    @Test
    public void testGetAllOrder(){
        int storeId = 1;
        String search = "test";
        int pageNo = 1;
        int pageSize = 10;
        int totalOrders = 25;
        OrderDto orderDto = Mockito.mock(OrderDto.class);
        OrderDto orderDto1 = Mockito.mock(OrderDto.class);
        List<OrderDto> orderList = Arrays.asList(orderDto, orderDto1);

        Mockito.when(orderDao.totalRecord(storeId, search)).thenReturn(totalOrders);
        Mockito.when(orderDao.getSearchOrdersByPage(storeId, search, 0, pageSize)).thenReturn(orderList);

        PagnitedList<OrderDto> result = orderService.getAllOrder(storeId, search, pageNo, pageSize);

        Assert.assertEquals(orderList, result.getList());
        Assert.assertEquals(pageNo, result.getPageNo());
        Assert.assertEquals(totalOrders, result.getTotal());
        Assert.assertEquals(pageSize, result.getPageSize());
        Assert.assertEquals((int) Math.ceil((double) totalOrders / pageSize), result.getTotalPages());

        Mockito.verify(orderDao, Mockito.times(1)).totalRecord(storeId, search);
        Mockito.verify(orderDao, Mockito.times(1)).getSearchOrdersByPage(storeId, search, 0, pageSize);
    }
}
