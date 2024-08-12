package com.example.demo.admin.servlet;


import com.example.demo.admin.dto.OrderDto;
import com.example.demo.admin.service.*;
import com.example.demo.entity.OrderHistory;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

public class ChangeOrderStatusServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderHistoryService orderHistoryService;

    @Mock
    private OrderDetailService orderDetailService;

    @InjectMocks
    private ChangeOrderServlet orderServlet;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("username")).thenReturn("admin");
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        Mockito.when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetOrderDetails() throws ServletException, IOException {
        Mockito.when(session.getAttribute("role")).thenReturn(1);
        Mockito.when(request.getRequestURI()).thenReturn("/admin/order");
        Mockito.when(request.getParameter("id")).thenReturn("1");

        OrderDto order = Mockito.mock(OrderDto.class);
        Mockito.when(orderService.getOrderDtoById(1)).thenReturn(order);

        Mockito.when(orderDetailService.getAllByOrder(Integer.parseInt(("1")))).thenReturn(Collections.emptyList());
        Mockito.when(orderHistoryService.getOrderHistoryByOrderId(Integer.parseInt(("1")))).thenReturn(Collections.emptyList());

        orderServlet.doGet(request,response);

        Mockito.verify(request).setAttribute("order", order);
        Mockito.verify(request).setAttribute("orderDetails", Collections.emptyList());
        Mockito.verify(request).setAttribute("orderHistories", Collections.emptyList());
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/order/detail-order.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostOrderStatus() throws ServletException, IOException {
        Mockito.when(session.getAttribute("role")).thenReturn(1);
        Mockito.when(request.getRequestURI()).thenReturn("/admin/order-status");
        Mockito.when(request.getParameter("order-id")).thenReturn("1");
        Mockito.when(request.getParameter("description")).thenReturn("status change");
        Mockito.when(request.getParameter("status")).thenReturn("2");
        Mockito.when(request.getParameter("store_id")).thenReturn("1");
        Mockito.when(request.getParameter("page")).thenReturn("1");
        Mockito.when(request.getParameter("search")).thenReturn("a");

        OrderHistory orderHistory = Mockito.mock(OrderHistory.class);
        Mockito.when(orderHistory.getStatus()).thenReturn(Constant.ORDER_STATE.PENDING);
        Mockito.when(orderHistoryService.getOrderHistoryByOrderLimit(Integer.parseInt("1"))).thenReturn(orderHistory);

        orderServlet.doPost(request,response);

        Mockito.verify(response).sendRedirect("/admin/orders?page=1&store_id=1&search=a");
    }

    @Test
    public void testDoPostOrderStatusDelvering() throws ServletException, IOException {
        Mockito.when(session.getAttribute("role")).thenReturn(1);
        Mockito.when(request.getRequestURI()).thenReturn("/admin/order-status");
        Mockito.when(request.getParameter("order-id")).thenReturn("1");
        Mockito.when(request.getParameter("description")).thenReturn("status change");
        Mockito.when(request.getParameter("status")).thenReturn("2");
        Mockito.when(request.getParameter("store_id")).thenReturn("1");
        Mockito.when(request.getParameter("page")).thenReturn("1");
        Mockito.when(request.getParameter("search")).thenReturn("a");

        OrderHistory orderHistory = Mockito.mock(OrderHistory.class);
        Mockito.when(orderHistory.getStatus()).thenReturn(Constant.ORDER_STATE.DELIVERED);
        Mockito.when(orderHistoryService.getOrderHistoryByOrderLimit(Integer.parseInt("1"))).thenReturn(orderHistory);

        orderServlet.doPost(request,response);

        Mockito.verify(response).sendRedirect("/admin/orders?page=1&store_id=1&search=a");
    }

    @Test
    public void testDoPostOrderStatusCanceled() throws ServletException, IOException {
        Mockito.when(session.getAttribute("role")).thenReturn(1);
        Mockito.when(request.getRequestURI()).thenReturn("/admin/order-status");
        Mockito.when(request.getParameter("order-id")).thenReturn("1");
        Mockito.when(request.getParameter("description")).thenReturn("status change");
        Mockito.when(request.getParameter("status")).thenReturn("2");
        Mockito.when(request.getParameter("store_id")).thenReturn("1");
        Mockito.when(request.getParameter("page")).thenReturn("1");
        Mockito.when(request.getParameter("search")).thenReturn("a");

        OrderHistory orderHistory = Mockito.mock(OrderHistory.class);
        Mockito.when(orderHistory.getStatus()).thenReturn(Constant.ORDER_STATE.CANCELLED);
        Mockito.when(orderHistoryService.getOrderHistoryByOrderLimit(Integer.parseInt("1"))).thenReturn(orderHistory);

        orderServlet.doPost(request,response);

        Mockito.verify(response).sendRedirect("/admin/orders?page=1&store_id=1&search=a");
    }
}
