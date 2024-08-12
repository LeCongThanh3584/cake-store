package com.example.demo.client.servlet;

import com.example.demo.client.service.AddressService;
import com.example.demo.client.service.CartService;
import com.example.demo.entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServletTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private CartService cartService;
    @Mock
    private AddressService addressService;
    @InjectMocks
    private CartServlet cartServlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User user = new User();
        user.setId(1);
        user.setUsername("ducnbk7a1@gmail.com");

        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getAttribute("user")).thenReturn(user);

        when(addressService.getAddresses(anyInt())).thenReturn(new ArrayList<>());
        when(cartService.getCarts(anyInt())).thenReturn(new ArrayList<>());
    }

    @ParameterizedTest
    @CsvSource({"/cart, FALSE", "/cart/random, TRUE"})
    public void indexTest(String path, boolean redirect) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(path);
        cartServlet.doGet(request, response);

        if (redirect) {
            verify(response).sendRedirect("/cart");
        } else {
            verify(request).setAttribute(eq("carts"), anyList());
            verify(request).setAttribute(eq("addresses"), anyList());
            verify(request).getRequestDispatcher("/WEB-INF/views/cart/index.jsp");
            verify(dispatcher).forward(request, response);
        }
    }


    @ParameterizedTest
    @CsvSource({"a,TRUE", "1,FALSE"})
    public void deleteTest(String cartId, boolean formatError) throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/cart/delete");
        when(request.getParameter("cartId")).thenReturn(cartId);
        when(cartService.removeCart(any(), any())).thenReturn(null);
        cartServlet.doGet(request, response);

        if (formatError) {
            verify(request).setAttribute("error", "Invalid cart id");
        } else {
            verify(request).setAttribute(eq("error"), any());
        }
        verify(request).setAttribute(eq("carts"), anyList());
        verify(request).setAttribute(eq("addresses"), anyList());
        verify(request).getRequestDispatcher("/WEB-INF/views/cart/index.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void postRedirectTest() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/cart/random");
        cartServlet.doPost(request, response);
        verify(response).sendRedirect("/cart");
    }

    @ParameterizedTest
    @CsvSource({"1,12,FALSE", "a,1,TRUE", "1,b,TRUE"})
    public void addTest(String cakeStoreId, String quantity, boolean formatError) throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn(quantity);
        when(request.getParameter("cakeStoreId")).thenReturn(cakeStoreId);
        when(cartService.addCart(any(), any(), any(), any())).thenReturn(null);
        cartServlet.doPost(request, response);

        if (formatError) {
            verify(request).setAttribute("error", "Please enter a valid number");
        } else {
            verify(request).setAttribute(eq("error"), any());
        }
        verify(request).setAttribute(eq("carts"), anyList());
        verify(request).setAttribute(eq("addresses"), anyList());
        verify(request).getRequestDispatcher("/WEB-INF/views/cart/index.jsp");
        verify(dispatcher).forward(request, response);
    }


    @ParameterizedTest
    @CsvSource({"1,12,FALSE", "a,1,TRUE", "1,b,TRUE"})
    public void updateTest(String cartId, String quantity, boolean formatError) throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/update");
        when(request.getParameter("quantity")).thenReturn(quantity);
        when(request.getParameter("cartId")).thenReturn(cartId);
        when(cartService.updateCart(any(), any(), any(), any())).thenReturn(null);
        cartServlet.doPost(request, response);

        if (formatError) {
            verify(request).setAttribute("error", "Please enter a valid number");
        } else {
            verify(request).setAttribute(eq("error"), any());
        }
        verify(request).setAttribute(eq("carts"), anyList());
        verify(request).setAttribute(eq("addresses"), anyList());
        verify(request).getRequestDispatcher("/WEB-INF/views/cart/index.jsp");
        verify(dispatcher).forward(request, response);
    }
}