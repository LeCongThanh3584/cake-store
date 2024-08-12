package com.example.demo.client.servlet;

import com.example.demo.client.service.CakeService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class IndexServletTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private CakeService cakeService;
    @InjectMocks
    private IndexServlet indexServlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void test() throws ServletException, IOException {
        when(cakeService.GetRecommendation()).thenReturn(new ArrayList<>());
        indexServlet.doGet(request, response);

        verify(request).setAttribute(eq("cakes"), anyList());
        verify(request).getRequestDispatcher("/WEB-INF/views/index.jsp");
        verify(dispatcher).forward(request, response);
    }
}