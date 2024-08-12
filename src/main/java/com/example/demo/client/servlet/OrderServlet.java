package com.example.demo.client.servlet;

import com.example.demo.client.dto.OrderDetailDto;
import com.example.demo.client.dto.OrderDto;
import com.example.demo.client.service.OrderDetailService;
import com.example.demo.client.service.OrderHistoryService;
import com.example.demo.client.service.OrderService;
import com.example.demo.client.service.UserService;
import com.example.demo.client.service.impl.OrderDetailServiceImpl;
import com.example.demo.client.service.impl.OrderHistoryServiceImpl;
import com.example.demo.client.service.impl.OrderServiceImpl;
import com.example.demo.client.service.impl.UserServiceImpl;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = {"/order/view", "/order/", "/order/detail"})
public class OrderServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    private final OrderService orderService = new OrderServiceImpl();

    private final OrderHistoryService orderHistoryService = new OrderHistoryServiceImpl();

    private final OrderDetailService orderDetailService = new OrderDetailServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/order/view":
                this.viewOrder(request, response);
                break;
            case "/order/detail":
                this.updateOrder(request, response);
                break;
        }
    }

    public void viewOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        if (!StringUtil.stringIsNullOrEmty(request.getParameter("page"))) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.getUserByUsername(username);
        String search = request.getParameter("search");
        int status = 0;
        if (!StringUtil.stringIsNullOrEmty(request.getParameter("status"))) {
            status = Integer.valueOf(request.getParameter("status"));
        }

        int totalRecords = orderService.totalRecord(search, status, user.getId());
        int totalPage = (int) Math.ceil(totalRecords * 1.0 / 10);
        List<OrderDto> orderDtoList = orderService.getOrdersByUserId((page - 1) * 10, 10, search, status, user.getId());
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("listOrder", orderDtoList);
        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.ORDER_FOLDER_FILE + "/order.jsp").forward(request, response);
    }

    public void updateOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        OrderDto order = orderService.getOrderById(id);
        List<OrderHistory> history = orderHistoryService.getAllOrderHistoryByIdOrder(id);
        List<OrderDetailDto> listOrderDtail = orderDetailService.getOrderDetailByIdOrder(id);
        request.setAttribute("orderDetail", listOrderDtail);
        request.setAttribute("order", order);
        request.setAttribute("orderHistory", history);
        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.ORDER_FOLDER_FILE + "/order-detail.jsp").forward(request, response);
    }

}
