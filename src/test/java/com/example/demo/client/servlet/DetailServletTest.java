package com.example.demo.client.servlet;

import com.example.demo.client.dto.CakeDetailDto;
import com.example.demo.client.service.CakeService;
import com.example.demo.client.service.StoreService;
import com.example.demo.entity.Store;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailServletTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private CakeService cakeService;
    @Mock
    private StoreService storeService;
    @InjectMocks
    private DetailsServlet detailsServlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        List<Store> stores = List.of(new Store(1));
        when(storeService.GetStores(1)).thenReturn(stores);
        when(storeService.GetStores(AdditionalMatchers.not(eq(1)))).thenReturn(new ArrayList<>());
        when(cakeService.GetCakeById(any(), any())).thenReturn(new CakeDetailDto());
    }

    @ParameterizedTest
    @CsvSource({"1, 1, FALSE", "var, var, TRUE"})
    public void test(String cakeId, String storeId, Boolean redirect) throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + cakeId);
        when(request.getParameter("storeId")).thenReturn(storeId);
        when(request.getRequestDispatcher("/WEB-INF/views/detail.jsp"))
                .thenReturn(dispatcher);
        detailsServlet.doGet(request, response);
        if (redirect) {
            verify(response).sendRedirect("/shop");
        } else {
            verify(request).setAttribute(eq("stores"), anyList());
            verify(request).setAttribute(eq("cake"), any());
            verify(request).getRequestDispatcher("/WEB-INF/views/detail.jsp");
            verify(dispatcher).forward(request, response);
        }
    }
}
