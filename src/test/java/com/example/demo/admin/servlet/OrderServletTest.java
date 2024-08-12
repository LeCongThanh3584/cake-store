package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.OrderDto;
import com.example.demo.admin.dto.PagnitedList;
import com.example.demo.admin.service.*;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;


public class OrderServletTest {

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
    private StoreService storeService;

    @Mock
    private UserService userService;

    @Mock
    private CakeStoreService cakeStoreSercice;

    @Mock
    private OrderDetailService orderDetailService;

    @InjectMocks
    private OrdersServlet orderServlet;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("username")).thenReturn("admin");
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        Mockito.when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetOrderViewAdmin() throws ServletException, IOException {
        String page = "1";
        String search = "testSearch";
        Mockito.when(session.getAttribute("role")).thenReturn(1);
        Mockito.when(request.getRequestURI()).thenReturn("/admin/orders");
        Mockito.when(request.getParameter("search")).thenReturn(search);
        Mockito.when(request.getParameter("page")).thenReturn(page);

        User adminUser = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(adminUser);
        PagnitedList<OrderDto> paginatedList = Mockito.mock(PagnitedList.class);
        Mockito.when(orderService.getAllOrder(
                Mockito.eq(adminUser.getIdStore()),
                Mockito.eq(search),
                Mockito.eq(Integer.parseInt(page)),
                Mockito.anyInt()
        )).thenReturn(paginatedList);

        Mockito.when(storeService.getAllStores()).thenReturn(Collections.emptyList());
        orderServlet.doGet(request, response);

        Mockito.verify(request).setCharacterEncoding("utf-8");
        Mockito.verify(request).setAttribute("stores", Collections.emptyList());
        Mockito.verify(request).setAttribute("listOrders", paginatedList);
        Mockito.verify(request).setAttribute("storeId", adminUser.getIdStore());
        Mockito.verify(request).setAttribute("search", search);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/order/view-orders.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetOrderViewAdminWithNoPage() throws ServletException, IOException {
        String page = null;
        String storeId = "123";
        String search = "testSearch";
        Mockito.when(session.getAttribute("role")).thenReturn(0);
        Mockito.when(request.getRequestURI()).thenReturn("/admin/orders");
        Mockito.when(request.getParameter("search")).thenReturn(search);
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("page")).thenReturn(page);

        User adminUser = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(adminUser);
        PagnitedList<OrderDto> paginatedList = Mockito.mock(PagnitedList.class);
        Mockito.when(orderService.getAllOrder(
                Mockito.eq(Integer.parseInt(storeId)),
                Mockito.eq(search),
                Mockito.eq(1),
                Mockito.anyInt()
        )).thenReturn(paginatedList);

        Mockito.when(storeService.getAllStores()).thenReturn(Collections.emptyList());
        orderServlet.doGet(request, response);

        Mockito.verify(request).setCharacterEncoding("utf-8");
        Mockito.verify(request).setAttribute("stores", Collections.emptyList());
        Mockito.verify(request).setAttribute("listOrders", paginatedList);
        Mockito.verify(request).setAttribute("storeId", Integer.parseInt(storeId));
        Mockito.verify(request).setAttribute("search", search);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/order/view-orders.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetOrderViewAdminWithNoStore() throws ServletException, IOException {
        String page = "1";
        String storeId = null;
        String search = "testSearch";
        Mockito.when(session.getAttribute("role")).thenReturn(0);
        Mockito.when(request.getRequestURI()).thenReturn("/admin/orders");
        Mockito.when(request.getParameter("search")).thenReturn(search);
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("page")).thenReturn(page);

        User adminUser = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(adminUser);
        PagnitedList<OrderDto> paginatedList = Mockito.mock(PagnitedList.class);
        Mockito.when(orderService.getAllOrder(
                Mockito.eq(0),
                Mockito.eq(search),
                Mockito.eq(Integer.parseInt(page)),
                Mockito.anyInt()
        )).thenReturn(paginatedList);

        Mockito.when(storeService.getAllStores()).thenReturn(Collections.emptyList());
        orderServlet.doGet(request, response);

        Mockito.verify(request).setCharacterEncoding("utf-8");
        Mockito.verify(request).setAttribute("stores", Collections.emptyList());
        Mockito.verify(request).setAttribute("listOrders", paginatedList);
        Mockito.verify(request).setAttribute("storeId", 0);
        Mockito.verify(request).setAttribute("search", search);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/order/view-orders.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetOrderViewSuperAdmin() throws ServletException, IOException {
        String page = "1";
        String storeId = "123";
        String search = "testSearch";
        Mockito.when(session.getAttribute("role")).thenReturn(0);
        Mockito.when(request.getRequestURI()).thenReturn("/admin/orders");
        Mockito.when(request.getParameter("search")).thenReturn(search);
        Mockito.when(request.getParameter("storeId")).thenReturn(storeId);
        Mockito.when(request.getParameter("page")).thenReturn(page);

        User adminUser = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(adminUser);
        PagnitedList<OrderDto> paginatedList = Mockito.mock(PagnitedList.class);
        Mockito.when(orderService.getAllOrder(
                Mockito.eq(Integer.parseInt(storeId)),
                Mockito.eq(search),
                Mockito.eq(Integer.parseInt(page)),
                Mockito.anyInt()
        )).thenReturn(paginatedList);

        Mockito.when(storeService.getAllStores()).thenReturn(Collections.emptyList());
        orderServlet.doGet(request, response);

        Mockito.verify(request).setCharacterEncoding("utf-8");
        Mockito.verify(request).setAttribute("stores", Collections.emptyList());
        Mockito.verify(request).setAttribute("listOrders", paginatedList);
        Mockito.verify(request).setAttribute("storeId", Integer.parseInt(storeId));
        Mockito.verify(request).setAttribute("search", search);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/order/view-orders.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }



    @Test
    public void testDoGetCreatOrderAdminView() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/create-order");
        Mockito.when(session.getAttribute("role")).thenReturn(0);

        Mockito.when(storeService.getAllStores()).thenReturn(Collections.emptyList());
        Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());
        orderServlet.doGet(request,response);

        Mockito.verify(request).setAttribute("stores", Collections.emptyList());
        Mockito.verify(request).setAttribute("storeId", 0);
        Mockito.verify(request).setAttribute("users", Collections.emptyList());
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN+"/order/create-order.jsp");
    }

    @Test
    public void testDoGetCreatOrderStoreSuperAdminView() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/create-order");
        Mockito.when(session.getAttribute("role")).thenReturn(1);
        User adminUser = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(adminUser);
        Mockito.when(storeService.getAllStores()).thenReturn(Collections.emptyList());
        Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());
        orderServlet.doGet(request,response);
        Mockito.verify(request).setAttribute("stores", Collections.emptyList());
        Mockito.verify(request).setAttribute("storeId", adminUser.getIdStore());
        Mockito.verify(request).setAttribute("users", Collections.emptyList());
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN+"/order/create-order.jsp");
    }

    @Test
    public void testPostCreateSuccess() throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/create-order");
        Mockito.when(request.getParameter("user_id")).thenReturn("1");
        Mockito.when(request.getParameter("store_id")).thenReturn("2");
        Mockito.when(request.getParameter("phone")).thenReturn("0347357396");
        Mockito.when(request.getParameter("address")).thenReturn("Hà Đông, Hà Nội");
        Mockito.when(request.getParameter("receiver")).thenReturn("Minh Quý");

        User adminUser = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(adminUser);

        int expectedOrderId = 35;
        Mockito.when(orderService.createOrder(Mockito.any(Order.class))).thenReturn(expectedOrderId);

        orderServlet.doPost(request, response);

        Mockito.verify(response).sendRedirect("/admin/update-item?id=" + expectedOrderId);
    }

    @Test
    public void testPostCreateSuccessUserNul() throws IOException, ServletException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/create-order");
        Mockito.when(request.getParameter("user_id")).thenReturn(null);
        Mockito.when(request.getParameter("store_id")).thenReturn("2");
        Mockito.when(request.getParameter("phone")).thenReturn("0347357396");
        Mockito.when(request.getParameter("address")).thenReturn("Hà Đông, Hà Nội");
        Mockito.when(request.getParameter("receiver")).thenReturn("Minh Quý");

        User adminUser = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(adminUser);

        int expectedOrderId = 35;
        Mockito.when(orderService.createOrder(Mockito.any(Order.class))).thenReturn(expectedOrderId);

        orderServlet.doPost(request, response);

        Mockito.verify(response).sendRedirect("/admin/update-item?id=" + expectedOrderId);
    }

    @Test
    public void testPostCreateFail() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/create-order");
        Mockito.when(request.getParameter("user_id")).thenReturn("1");
        Mockito.when(request.getParameter("store_id")).thenReturn(null);
        Mockito.when(request.getParameter("phone")).thenReturn("0347357396");
        Mockito.when(request.getParameter("address")).thenReturn("Hà Đông, Hà Nội");
        Mockito.when(request.getParameter("receiver")).thenReturn("Minh Quý");

        User adminUser = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(adminUser);
        orderServlet.doPost(request,response);

        Mockito.verify(request).setAttribute("error", "store is not null");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/order/create-order.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetUpdateOrder() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-order");
        Mockito.when(request.getParameter("id")).thenReturn("1");

        Order order = Mockito.mock(Order.class);
        Mockito.when(orderService.getById(Integer.parseInt("1"))).thenReturn(order);
        Mockito.when(storeService.getAllStores()).thenReturn(Collections.emptyList());
        Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());
        orderServlet.doGet(request,response);

        Mockito.verify(request).setAttribute("order", order);
        Mockito.verify(request).setAttribute("stores", Collections.emptyList());
        Mockito.verify(request).setAttribute("users", Collections.emptyList());
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/order/update-order.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostUpdateOrderSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-order");
        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("user_id")).thenReturn("1");
        Mockito.when(request.getParameter("store_id")).thenReturn("2");
        Mockito.when(request.getParameter("phone")).thenReturn("0347357396");
        Mockito.when(request.getParameter("address")).thenReturn("Hà Đông, Hà Nội");
        Mockito.when(request.getParameter("receiver")).thenReturn("Minh Quý");

        Order order = Mockito.mock(Order.class);
        Mockito.when(orderService.getById(Mockito.anyInt())).thenReturn(order);
        Mockito.when(orderService.update(Mockito.any(Order.class))).thenReturn(true);

        orderServlet.doPost(request,response);

        Mockito.verify(session).setAttribute("info", "Update info order successfully!");
        Mockito.verify(response).sendRedirect("/admin/update-order?id=1");
    }

    @Test
    public void testDoPostUpdateOrderSuccessUserNull() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-order");
        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("user_id")).thenReturn(null);
        Mockito.when(request.getParameter("store_id")).thenReturn("2");
        Mockito.when(request.getParameter("phone")).thenReturn("0347357396");
        Mockito.when(request.getParameter("address")).thenReturn("Hà Đông, Hà Nội");
        Mockito.when(request.getParameter("receiver")).thenReturn("Minh Quý");

        Order order = Mockito.mock(Order.class);
        Mockito.when(orderService.getById(Mockito.anyInt())).thenReturn(order);
        Mockito.when(orderService.update(Mockito.any(Order.class))).thenReturn(true);

        orderServlet.doPost(request,response);

        Mockito.verify(session).setAttribute("info", "Update info order successfully!");
        Mockito.verify(response).sendRedirect("/admin/update-order?id=1");
    }

    @Test
    public void testDoGetUpdateItemOrder() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-item");
        Mockito.when(request.getParameter("id")).thenReturn("1");
        Order mockOrder = Mockito.mock(Order.class);
        Mockito.when(orderService.getById(Integer.parseInt("1"))).thenReturn(mockOrder);
        Mockito.when(mockOrder.getIdStore()).thenReturn(123);
        Mockito.when(cakeStoreSercice.getAllCakesByStore(123)).thenReturn(Collections.emptyList());
        Mockito.when(orderDetailService.getAllByOrder(Integer.parseInt("1"))).thenReturn(Collections.emptyList());

        orderServlet.doGet(request,response);

        Mockito.verify(request).setAttribute("list", Collections.emptyList());
        Mockito.verify(request).setAttribute("orderId", Integer.parseInt("1"));
        Mockito.verify(request).setAttribute("cakeStores", Collections.emptyList());
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/order/edit-item-order.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }


    @Test
    public void testDoPostUpdateItemOrderSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-item");
        Mockito.when(request.getParameter("itemId")).thenReturn("789");
        Mockito.when(request.getParameter("orderId")).thenReturn("123");
        Mockito.when(request.getParameter("cakeId")).thenReturn("456");
        Mockito.when(request.getParameter("quantity")).thenReturn("3");
        Mockito.when(request.getParameter("cake_price")).thenReturn("10.50");
        Mockito.when(request.getParameter("cake_quantity")).thenReturn("5");

        OrderDetail orderDetail = Mockito.mock(OrderDetail.class);
        Mockito.when(orderDetailService.getOrderDetailById(789)).thenReturn(orderDetail);
        Mockito.doNothing().when(orderDetailService).update(Mockito.any());

        orderServlet.doPost(request,response);

        Mockito.verify(session).setAttribute("success", "Update Cake Successfully!");
        Mockito.verify(response).sendRedirect("/admin/update-item?id=123");
    }

    @Test
    public void testDoPostCreateItemOrderSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-item");
        Mockito.when(request.getParameter("itemId")).thenReturn(null);
        Mockito.when(request.getParameter("orderId")).thenReturn("123");
        Mockito.when(request.getParameter("cakeId")).thenReturn("456");
        Mockito.when(request.getParameter("quantity")).thenReturn("3");
        Mockito.when(request.getParameter("cake_price")).thenReturn("10.50");
        Mockito.when(request.getParameter("cake_quantity")).thenReturn("5");

        Mockito.doNothing().when(orderDetailService).create(Mockito.any());
        orderServlet.doPost(request,response);

        Mockito.verify(session).setAttribute("success", "Add Cake Successfully!");
        Mockito.verify(response).sendRedirect("/admin/update-item?id=123");
    }

    @Test
    public void testUpdateItemOrder_invalidQuantity() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-item");
        Mockito.when(request.getParameter("itemId")).thenReturn("2");
        Mockito.when(request.getParameter("orderId")).thenReturn("123");
        Mockito.when(request.getParameter("cakeId")).thenReturn("456");
        Mockito.when(request.getParameter("quantity")).thenReturn("0");
        Mockito.when(request.getParameter("cake_price")).thenReturn("10.50");
        Mockito.when(request.getParameter("cake_quantity")).thenReturn("5");

        orderServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("error1", "Quantity must be greater than zero");
        Mockito.verify(response).sendRedirect("/admin/update-item?id=123");
    }

    @Test
    public void testUpdateItemOrder_quantityNotEnough() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-item");
        Mockito.when(request.getParameter("itemId")).thenReturn("2");
        Mockito.when(request.getParameter("orderId")).thenReturn("123");
        Mockito.when(request.getParameter("cakeId")).thenReturn("456");
        Mockito.when(request.getParameter("quantity")).thenReturn("10");
        Mockito.when(request.getParameter("cake_price")).thenReturn("10.50");
        Mockito.when(request.getParameter("cake_quantity")).thenReturn("5");

        orderServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("error2", "Quantity is not enough");
        Mockito.verify(response).sendRedirect("/admin/update-item?id=123");
    }

    @Test
    public void testUpdateItemOrder_validateItem() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-item");
        Mockito.when(request.getParameter("itemId")).thenReturn(null);
        Mockito.when(request.getParameter("orderId")).thenReturn("123");
        Mockito.when(request.getParameter("cakeId")).thenReturn(null);
        Mockito.when(request.getParameter("quantity")).thenReturn("10");
        Mockito.when(request.getParameter("cake_price")).thenReturn("10.50");
        Mockito.when(request.getParameter("cake_quantity")).thenReturn(null);

        orderServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("error", "Select cake");
        Mockito.verify(response).sendRedirect("/admin/update-item?id=123");
    }

    @Test
    public void testDeleteItemOrder() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-item");
        Mockito.when(request.getParameter("itemId")).thenReturn("5");
        Mockito.when(request.getParameter("orderId")).thenReturn("1");

        Mockito.doNothing().when(orderDetailService).delete(Mockito.anyInt());
        orderServlet.doGet(request, response);

        Mockito.verify(response).sendRedirect("/admin/update-item?id=1");
    }
}

