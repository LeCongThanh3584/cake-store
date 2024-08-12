package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.CakeStoreResponse;
import com.example.demo.admin.dto.OrderDetailDto;
import com.example.demo.admin.dto.OrderDto;
import com.example.demo.admin.dto.PagnitedList;
import com.example.demo.admin.service.*;
import com.example.demo.admin.service.impl.*;
import com.example.demo.entity.*;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ordersServlet",
        value = {"/admin/orders","/admin/create-order",
                "/admin/update-order","/admin/update-item","/admin/delete-item"})
public class OrdersServlet extends HttpServlet {

    private OrderService orderService = new OrderServiceImpl();

    private StoreService storeService = new StoreServiceImpl();

    private UserService userService = new UserServiceImpl();

    private CakeStoreService cakeStoreSercice = new CakeStoreServiceImpl();

    private OrderDetailService orderDetailService = new OrderDetailServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/admin/orders":
                this.getAllSearch(request,response);
                break;
            case "/admin/create-order":
                this.getCreateOrder(request,response);
                break;
            case "/admin/update-order":
                this.getUpdateById(request,response);
                break;
            case "/admin/update-item":
                this.getUpdateItemOrder(request,response);
                break;
            case "/admin/delete-item":
                this.deleteItemOrder(request,response);
                break;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/admin/create-order":
                this.createOrder(request,response);
                break;
            case "/admin/update-order":
                this.updateOrder(request,response);
                break;
            case "/admin/update-item":
                this.updateItemOrder(request,response);
                break;
        }
    }

    public void getAllSearch(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        String page = request.getParameter("page");
        String storId = request.getParameter("storeId");
        String search = request.getParameter("search");
        int pageNo = 1;
        int pageSize = 10;
        int storeId;

        HttpSession session = request.getSession();
        Integer roleUser = (Integer) session.getAttribute("role");
        String username = (String) session.getAttribute("username");
        if(roleUser != 0){
            storeId = userService.getUserByUsername(username).getIdStore();
        } else {
            if (storId == null || storId.isEmpty()) {
                storeId = 0;
            } else {
                storeId = Integer.parseInt(storId);
            }
        }

        if (page != null && !page.isEmpty()) {
            pageNo = Integer.parseInt(page);
        }

        PagnitedList<OrderDto> list = orderService.getAllOrder(storeId, search, pageNo, pageSize);
        List<Store> stores = storeService.getAllStores();
        request.setAttribute("stores",stores);
        request.setAttribute("listOrders", list);
        request.setAttribute("storeId",storeId);
        request.setAttribute("search",search);
        request.getRequestDispatcher("/WEB-INF/admin/order/view-orders.jsp").forward(request, response);
    }

    public void getCreateOrder(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        int storeId = 0;
        HttpSession session = request.getSession();
        Integer roleUser = (Integer) session.getAttribute("role");
        String username = (String) session.getAttribute("username");
        if(roleUser != 0){
            storeId = userService.getUserByUsername(username).getIdStore();
        }
        List<Store> stores = storeService.getAllStores();
        List<User> users = userService.getAllUsers();
        request.setAttribute("stores",stores);
        request.setAttribute("storeId",storeId);
        request.setAttribute("users",users);
        request.getRequestDispatcher("/WEB-INF/admin/order/create-order.jsp")
                .forward(request, response);
    }

    public void createOrder(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        String user =  request.getParameter("user_id");
        String store = request.getParameter("store_id");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String receiver = request.getParameter("receiver");

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("username");
        User userAdmin = userService.getUserByUsername(admin);

        try {
            Order order = new Order();
            order.setIdAdmin(userAdmin.getId());
            order.setTotalMoney(BigDecimal.ZERO);
            if(StringUtils.isNotBlank(user)){
                order.setIdUser(Integer.parseInt(user));
            } else {
                order.setIdUser(null);
            }
            if(StringUtils.isNotBlank(store)){
                order.setIdStore(Integer.parseInt(store));
            } else {
                List<Store> stores = storeService.getAllStores();
                List<User> users = userService.getAllUsers();
                request.setAttribute("stores",stores);
                request.setAttribute("users",users);
                request.setAttribute("error","store is not null");
                request.getRequestDispatcher("/WEB-INF/admin/order/create-order.jsp").forward(request, response);
            }
            order.setPhone(phone);
            order.setAddress(address);
            order.setReciver(receiver);
            order.setCode(StringUtil.generateString(6));
            order.setCreatedAt(LocalDateTime.now());
            order.setCreatedBy(admin);

            int idOrder = orderService.createOrder(order);
            response.sendRedirect("/admin/update-item?id="+idOrder);
        } catch (NumberFormatException e){
            throw new RuntimeException(e);
        }
    }

    public void getUpdateById(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        try{
            int orderId = Integer.parseInt(id);
            Order order = orderService.getById(orderId);
            List<Store> stores = storeService.getAllStores();
            List<User> users = userService.getAllUsers();
            request.setAttribute("order",order);
            request.setAttribute("stores",stores);
            request.setAttribute("users",users);
            request.getRequestDispatcher("/WEB-INF/admin/order/update-order.jsp")
                .forward(request, response);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void updateOrder(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String customer = request.getParameter("user_id");
        String receiver = request.getParameter("receiver");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String store = request.getParameter("store_id");

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("username");
        try{
            int orderId = Integer.parseInt(id);
            int storeId = Integer.parseInt(store);
            Order order = orderService.getById(orderId);
            if(StringUtils.isNotBlank(customer)){
                order.setIdUser(Integer.parseInt(customer));
            } else {
                order.setIdUser(null);
            }
            order.setReciver(receiver);
            order.setPhone(phone);
            order.setAddress(address);
            order.setIdStore(storeId);
            order.setUpdatedBy(admin);
            boolean isSuccess = orderService.update(order);
            request.getSession().setAttribute("info", "Update info order successfully!");
            response.sendRedirect("/admin/update-order?id="+orderId);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void getUpdateItemOrder(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        String id = request.getParameter("id");
        try{
            int orderId = Integer.parseInt(id);
            Order order = orderService.getById(orderId);
            List<CakeStoreResponse> cakeStores = cakeStoreSercice.getAllCakesByStore(order.getIdStore());
            List<OrderDetailDto> list = orderDetailService.getAllByOrder(orderId);

            request.setAttribute("list", list);
            request.setAttribute("orderId", orderId);
            request.setAttribute("cakeStores", cakeStores);
            request.getRequestDispatcher("/WEB-INF/admin/order/edit-item-order.jsp")
                    .forward(request, response);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    public void updateItemOrder(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        request.setCharacterEncoding("UTF-8");
        String itemId = request.getParameter("itemId");
        String orderIdStr = request.getParameter("orderId");
        String cakeIdStr = request.getParameter("cakeId");
        String quantityStr = request.getParameter("quantity");
        String cakePriceStr = request.getParameter("cake_price");
        String cakeQuantityStr = request.getParameter("cake_quantity");
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("username");

        try {
            int orderId = Integer.parseInt(orderIdStr);
            int cakeId = Integer.parseInt(cakeIdStr);
            int quantity = Integer.parseInt(quantityStr);
            int cakeQuantity = Integer.parseInt(cakeQuantityStr);
            BigDecimal cakePrice = new BigDecimal(cakePriceStr);

            if (quantity <= 0) {
                request.getSession().setAttribute("error1","Quantity must be greater than zero");
                response.sendRedirect("/admin/update-item?id="+orderId);
                return;
            }

            if (quantity > cakeQuantity) {
                request.getSession().setAttribute("error2","Quantity is not enough");
                response.sendRedirect("/admin/update-item?id="+orderId);
                return;
            }

            OrderDetail orderDetail;
            if (StringUtil.stringIsNullOrEmty(itemId)) {
                orderDetail = new OrderDetail();
                orderDetail.setIdOrder(orderId);
                orderDetail.setIdCakeStore(cakeId);
                orderDetail.setPrice(cakePrice);
                orderDetail.setQuantity(quantity);
                orderDetail.setCreatedBy(admin);
                orderDetailService.create(orderDetail);
                request.getSession().setAttribute("success","Add Cake Successfully!");
            } else {
                int item = Integer.parseInt(itemId);
                orderDetail = orderDetailService.getOrderDetailById(item);
                orderDetail.setIdCakeStore(cakeId);
                orderDetail.setPrice(cakePrice);
                orderDetail.setQuantity(quantity);
                orderDetail.setUpdatedBy(admin);
                orderDetailService.update(orderDetail);
                request.getSession().setAttribute("success","Update Cake Successfully!");
            }

            response.sendRedirect("/admin/update-item?id=" + orderId);
        } catch (NumberFormatException e) {
            int orderId = Integer.parseInt(orderIdStr);
            request.getSession().setAttribute("error","Select cake");
            response.sendRedirect("/admin/update-item?id="+orderId);
        }
    }

    public void deleteItemOrder(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
        String item = request.getParameter("itemId");
        String order = request.getParameter("orderId");
        int itemId = Integer.parseInt(item);
        orderDetailService.delete(itemId);
        response.sendRedirect("/admin/update-item?id="+order);
    }
}