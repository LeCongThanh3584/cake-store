package com.example.demo.client.servlet;

import com.example.demo.client.dto.OrderViewDto;
import com.example.demo.client.service.CheckoutService;
import com.example.demo.entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;

public class CheckoutServletTest {
    @Mock
    private CheckoutService checkoutService;
    @InjectMocks
    private CheckoutServlet checkoutServlet;

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher dispatcher;

    public static Stream<Arguments> fakeData() {
        return Stream.of(
                Arguments.of(false, 1, "a", "a", "a", "a", "a"),
                Arguments.of(false, 1, "", "", "", "", ""),
                Arguments.of(true, 2, "", "", "", "", "")
        );
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        List<OrderViewDto> list = List.of(new OrderViewDto());
        when(checkoutService.CreateOrder(eq(1), any(), any())).thenReturn(list);
        when(checkoutService.CreateOrder(AdditionalMatchers.not(eq(1)), any(), any())).thenReturn(new ArrayList<>());
    }

    @ParameterizedTest
    @MethodSource("fakeData")
    public void testGet(Boolean error, Integer userId, String receiver, String phone, String district, String ward, String province) throws ServletException, IOException {
        User user = new User(userId);
        when(request.getAttribute("user")).thenReturn(user);
        checkoutServlet.doGet(request, response);

        if (error) {
            verify(response).sendRedirect("/cart");
        } else {
            verify(request).setAttribute(eq("orderViews"), anyList());
            verify(request).setAttribute(eq("address"), any());
            verify(request).getRequestDispatcher("/WEB-INF/views/checkout.jsp");
            verify(dispatcher).forward(request, response);
        }
    }

    @ParameterizedTest
    @MethodSource("fakeData")
    public void testPost(Boolean error, Integer userId, String receiver, String phone, String district, String ward, String province) throws ServletException, IOException {
        when(request.getParameter("receiver")).thenReturn(receiver);
        when(request.getParameter("phone")).thenReturn(phone);
        when(request.getParameter("district")).thenReturn(district);
        when(request.getParameter("ward")).thenReturn(ward);
        when(request.getParameter("province")).thenReturn(province);

        User user = new User(userId);
        when(request.getAttribute("user")).thenReturn(user);
        checkoutServlet.doPost(request, response);

        if (receiver != "" && phone != "" && district != "" && ward != "" && province != "") {
            verify(response).sendRedirect("/index");
        } else if (error) {
            verify(response).sendRedirect("/cart");
        } else {
            verify(request).setAttribute(eq("orderViews"), anyList());
            verify(request).setAttribute(eq("address"), any());
            verify(request).getRequestDispatcher("/WEB-INF/views/checkout.jsp");
            verify(dispatcher).forward(request, response);
        }
    }
}