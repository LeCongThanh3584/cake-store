package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.OrderDetailDto;
import com.example.demo.admin.dto.OrderDto;
import com.example.demo.admin.service.*;
import com.example.demo.admin.service.impl.*;
import com.example.demo.entity.OrderHistory;
import com.example.demo.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "updateOrderServlet", value = {"/admin/order","/admin/order-status"})
public class ChangeOrderServlet extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();

    private OrderHistoryService orderHistoryService = new OrderHistoryServiceImpl();

    private OrderDetailService orderDetailService = new OrderDetailServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/admin/order":
                this.getDetailOrder(request,response);
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        String uri = request.getRequestURI();
        switch (uri){
            case "/admin/order-status":
                this.changeStatusOrder(request,response);
                break;
        }
    }

    public void getDetailOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        String id = request.getParameter("id");
        int orderId = Integer.parseInt(id);
        OrderDto order = orderService.getOrderDtoById(orderId);
        List<OrderDetailDto> orderDetails = orderDetailService.getAllByOrder(orderId);
        List<OrderHistory> orderHistories = orderHistoryService.getOrderHistoryByOrderId(orderId);
        request.setAttribute("order",order);
        request.setAttribute("orderDetails",orderDetails);
        request.setAttribute("orderHistories",orderHistories);
        request.getRequestDispatcher(Constant.PATH_ADMIN+"/order/detail-order.jsp")
                .forward(request, response);
    }

    public void changeStatusOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        int orderId = Integer.parseInt(request.getParameter("order-id"));
        String description =  request.getParameter("description");
        int status = Integer.parseInt(request.getParameter("status"));
        String storeId = request.getParameter("store_id");
        String page = request.getParameter("page");
        String search = request.getParameter("search");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        OrderHistory historyOrder = orderHistoryService.getOrderHistoryByOrderLimit(orderId);

        if(!historyOrder.getStatus().equals(Constant.ORDER_STATE.DELIVERED) &&
                !historyOrder.getStatus().equals(Constant.ORDER_STATE.CANCELLED) ) {
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setIdOrder(orderId);
            orderHistory.setDescription(description);
            orderHistory.setStatus(status);
            orderHistory.setCreatedBy(username);
            orderHistoryService.createHistory(orderHistory);
        }
        response.sendRedirect("/admin/orders?page="+page+"&store_id="+storeId+"&search="+search);
    }
}