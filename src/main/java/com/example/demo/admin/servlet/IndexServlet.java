package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.CakeViewDto;
import com.example.demo.admin.dto.OrderChangeDto;
import com.example.demo.admin.dto.OrderHistoryCountDto;
import com.example.demo.admin.dto.OrderStatisticDto;
import com.example.demo.admin.service.CakeService;
import com.example.demo.admin.service.OrderService;
import com.example.demo.admin.service.StoreService;
import com.example.demo.admin.service.impl.CakeServiceImpl;
import com.example.demo.admin.service.impl.OrderServiceImpl;
import com.example.demo.admin.service.impl.StoreServiceImpl;
import com.example.demo.entity.Store;
import com.example.demo.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "adminServlet", value = "/admin")
public class IndexServlet extends HttpServlet {
    private OrderService orderService;
    private CakeService cakeService;
    private StoreService storeService;

    public void init() {
        orderService = new OrderServiceImpl();
        cakeService = new CakeServiceImpl();
        storeService = new StoreServiceImpl();
    }

    private Integer getStoreId(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        if (user.getRole() == 0) {
            return request.getParameter("storeId") == null || request.getParameter("storeId").isEmpty()
                    ? null
                    : Integer.parseInt(request.getParameter("storeId"));
        } else {
            return user.getIdStore();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        User user = (User) request.getAttribute("user");
        String dateType = request.getParameter("dateType") == null || request.getAttribute("dateType") == ""
                ? "" : request.getParameter("dateType");
        Integer storeId = getStoreId(request);
        List<OrderStatisticDto> statistics = orderService.getOrderStatistics(storeId, dateType);
        OrderStatisticDto statistic = orderService.getOrderStatistic(storeId, dateType);

        List<OrderHistoryCountDto> historyCounts = orderService.getOrderHistoryCount(storeId, dateType);
        List<OrderChangeDto> latestChange = orderService.getLatestChange(storeId, dateType);
        List<CakeViewDto> topSelling = cakeService.getTopSelling(storeId, dateType);

        List.of(0, 1, 2, 3).forEach(value -> request.setAttribute("status" + value, 0));
        historyCounts.forEach(item -> request.setAttribute("status" + item.getStatus(), item.getCount()));

        request.setAttribute("statistics", statistics);
        request.setAttribute("topSelling", topSelling);
        request.setAttribute("latestChange", latestChange);
        request.setAttribute("statistic", statistic);

        if (user.getRole() == 0) {
            List<Store> stores = storeService.getAllStores();
            request.setAttribute("stores", stores);
        }
        request.setAttribute("role", user.getRole());
        request.setAttribute("user", user);
        request.setAttribute("storeId", storeId);
        request.getRequestDispatcher("/WEB-INF/admin/index/index.jsp")
                .forward(request, response);
    }

    public void destroy() {
    }
}