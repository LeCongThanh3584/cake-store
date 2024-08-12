package com.example.demo.shipper.servlet;

import com.example.demo.entity.Store;
import com.example.demo.entity.User;
import com.example.demo.shipper.dto.OrderDto;
import com.example.demo.shipper.dto.OrderViewDto;
import com.example.demo.shipper.dto.ViewPage;
import com.example.demo.shipper.service.Impl.OrderServiceImpl;
import com.example.demo.shipper.service.Impl.StoreServiceImpl;
import com.example.demo.shipper.service.OrderService;
import com.example.demo.shipper.service.StoreService;
import com.example.demo.util.Constant.ORDER_STATE;
import com.example.demo.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "shipperServlet", value = "/shipper/*")
public class IndexServlet extends HttpServlet {
    private final Integer pageSize = 3;
    private OrderService orderService;
    private StoreService storeService;

    public void init() {
        orderService = new OrderServiceImpl();
        storeService = new StoreServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        switch (requestURI) {
            case "/shipper/view":
                renderAvailableOrders(request, response);
                break;
            case "/shipper/history":
                renderHistoryOrders(request, response);
                break;
            case "/shipper/active":
                renderActiveOrders(request, response);
                break;
            default:
                if (requestURI.startsWith("/shipper/order")) {
                    renderDetail(request, response);
                } else {
                    response.sendRedirect("/shipper/view");
                }
        }
    }

    private ViewPage getViewPage(HttpServletRequest request, Integer orderState, Integer shipperId) {
        String query = ParamUtil.get(request, "query");
        int storeId = ParamUtil.getInt(request, "storeId");
        int page = ParamUtil.getInt(request, "page");

        Integer pageCount = orderService.CountOrders(shipperId, storeId, orderState, query);
        page = Math.max(Math.min(page, pageCount), 1);

        return new ViewPage(page, storeId, pageCount, pageSize, query);
    }

    private void renderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer orderId = ParamUtil.parseInt(request.getRequestURI().substring(15));
        User user = (User) request.getAttribute("user");
        OrderViewDto orderView = orderService.getOrderView(user.getId(), orderId);

        if (orderView == null) {
            response.sendRedirect("/shipper/view");
        } else {
            request.setAttribute("orderView", orderView);
            request.getRequestDispatcher("/WEB-INF/shipper/detail.jsp")
                    .forward(request, response);
        }
    }


    private void renderHistoryOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getAttribute("user");
        ViewPage viewPage = getViewPage(request, ORDER_STATE.DELIVERED, user.getId());
        List<OrderDto> orders = orderService.Search(user.getId(), viewPage, ORDER_STATE.DELIVERED);

        responseView(request, viewPage);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/shipper/history.jsp")
                .forward(request, response);
    }

    private void renderActiveOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        ViewPage viewPage = getViewPage(request, ORDER_STATE.DELIVERING, user.getId());
        List<OrderDto> orders = orderService.Search(user.getId(), viewPage, ORDER_STATE.DELIVERING);
        String error = request.getParameter("error");

        responseView(request, viewPage);
        request.setAttribute("orders", orders);
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/shipper/active.jsp")
                .forward(request, response);
    }

    private void renderAvailableOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ViewPage viewPage = getViewPage(request, ORDER_STATE.CONFIRMED, 0);
        List<OrderDto> orders = orderService.Search(0, viewPage, ORDER_STATE.CONFIRMED);
        String error = request.getParameter("error");

        responseView(request, viewPage);
        request.setAttribute("orders", orders);
        request.setAttribute("error", error);
        request.getRequestDispatcher("/WEB-INF/shipper/view.jsp")
                .forward(request, response);
    }

    private void responseView(HttpServletRequest request, ViewPage viewPage) {
        int page = viewPage.getPage();
        int pageCount = viewPage.getPageCount();

        int maxPage = (int) Math.ceil((double) pageCount / pageSize);
        int startPage = page > 3 ? page - 3 : 1;
        int endPage = Math.min(maxPage <= page ? page : page + 3, maxPage);

        List<Store> stores = storeService.GetStores();

        request.setAttribute("stores", stores);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", pageCount);

        request.setAttribute("pageSize", pageSize);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getRequestURI()) {
            case "/shipper/select":
                selectOrder(request, response);
                break;
            case "/shipper/deliver":
                deliverOrder(request, response);
                break;
            default:
                response.sendRedirect("/shipper/view");
        }
    }

    private void selectOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = ParamUtil.getInt(request, "orderId");
        String orderMessage = request.getParameter("description");
        User user = (User) request.getAttribute("user");

        String msg = orderService.selectOrder(user.getId(), user.getUsername(), orderId, orderMessage);
        if (msg == null) {
            response.sendRedirect("/shipper/active");
        } else {
            response.sendRedirect("/shipper/view?error=" + msg);
        }
    }

    private void deliverOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = ParamUtil.getInt(request, "orderId");
        String orderMessage = request.getParameter("description");
        User user = (User) request.getAttribute("user");

        String msg = orderService.deliverOrder(user.getUsername(), orderId, orderMessage);
        if (msg == null) {
            response.sendRedirect("/shipper/history");
        } else {
            response.sendRedirect("/shipper/active?error=" + msg);
        }
    }

    public void destroy() {
    }
}