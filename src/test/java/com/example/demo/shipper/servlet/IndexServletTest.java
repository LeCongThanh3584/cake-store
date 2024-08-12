package com.example.demo.shipper.servlet;

import com.example.demo.entity.User;
import com.example.demo.shipper.dto.OrderViewDto;
import com.example.demo.shipper.service.OrderService;
import com.example.demo.shipper.service.StoreService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IndexServletTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private OrderService orderService;
    @Mock
    private StoreService storeService;
    @InjectMocks
    private IndexServlet indexServlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User user = new User(1);
        user.setUsername("username@gmail.com");
        when(request.getAttribute("user")).thenReturn(user);
        when(request.getAttribute("orderId")).thenReturn("1");

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(orderService.Search(anyInt(), any(), anyInt())).thenReturn(new ArrayList<>());
        when(orderService.CountOrders(nullable(Integer.class), nullable(Integer.class), anyInt(), nullable(String.class))).thenReturn(10);
    }

    @ParameterizedTest
    @ValueSource(strings = {"POST", "GET"})
    public void getDefaultPath(String method) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/shipper/random");
        if (method.equals("POST")) {
            indexServlet.doPost(request, response);
        } else {
            indexServlet.doGet(request, response);
        }
        response.sendRedirect("/shipper/view");
    }

    @ParameterizedTest
    @CsvSource({
            "/shipper/view, /WEB-INF/shipper/view.jsp, -1",
            "/shipper/history, /WEB-INF/shipper/history.jsp, 4",
            "/shipper/active, /WEB-INF/shipper/active.jsp, 10"
    })
    public void getOrderViews(String requestUri, String pageUrl, String page) throws Exception {
        when(request.getRequestURI()).thenReturn(requestUri);
        when(request.getParameter("page")).thenReturn(page);
        indexServlet.doGet(request, response);

        verify(request).setAttribute(eq("orders"), any());
        verify(request).getRequestDispatcher(pageUrl);
        verify(dispatcher).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({"-a, false", "-1, false", "1, true"})
    public void getOrderDetail(String orderId, Boolean found) throws Exception {
        OrderViewDto viewDto = found ? new OrderViewDto() : null;
        when(orderService.getOrderView(anyInt(), anyInt())).thenReturn(viewDto);
        when(request.getRequestURI()).thenReturn("/shipper/order/" + orderId);

        indexServlet.doGet(request, response);
        if (found) {
            verify(request).setAttribute(eq("orderView"), any());
            verify(request).getRequestDispatcher("/WEB-INF/shipper/detail.jsp");
            verify(dispatcher).forward(request, response);
        } else {
            verify(response).sendRedirect("/shipper/view");
        }
    }

    @ParameterizedTest
    @CsvSource({"TRUE", "FALSE"})
    public void postSelectOrder(Boolean error) throws Exception {
        String msg = error ? "error" : null;
        when(orderService.selectOrder(anyInt(), anyString(), anyInt(), nullable(String.class))).thenReturn(msg);
        when(request.getRequestURI()).thenReturn("/shipper/select");
        indexServlet.doPost(request, response);

        if (error) {
            response.sendRedirect("/shipper/view?error=" + msg);
        } else {
            response.sendRedirect("/shipper/active");
        }
    }

    @ParameterizedTest
    @CsvSource({"TRUE", "FALSE"})
    public void postDeliverOrder(Boolean error) throws Exception {
        String msg = error ? "error" : null;
        when(orderService.deliverOrder(anyString(), anyInt(), nullable(String.class))).thenReturn(msg);
        when(request.getRequestURI()).thenReturn("/shipper/deliver");
        indexServlet.doPost(request, response);

        if (error) {
            response.sendRedirect("/shipper/active?error=" + msg);
        } else {
            response.sendRedirect("/shipper/history");
        }
    }
}
