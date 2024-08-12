package com.example.demo.client;

import com.example.demo.client.servlet.ShopServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShopServletTest {
    private ShopServlet shopServlet;

    @BeforeEach
    public void setup() throws ServletException {
        shopServlet = new ShopServlet();
        shopServlet.init(mock(ServletConfig.class));
    }


    @Test
    public void testOriginalVersion() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("query")).thenReturn("");
        when(request.getParameter("sortBy")).thenReturn("");
        when(request.getParameter("direction")).thenReturn("");

        when(request.getParameter("categoryId")).thenReturn("");
        when(request.getParameter("storeId")).thenReturn("");
        when(request.getParameter("materialId")).thenReturn("");
        when(request.getParameter("from")).thenReturn("");
        when(request.getParameter("to")).thenReturn("");

        when(request.getRequestDispatcher("/WEB-INF/views/shop/index.jsp"))
                .thenReturn(dispatcher);

    }
}
