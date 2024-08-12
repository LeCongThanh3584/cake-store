package com.example.demo.client.servlet;

import com.example.demo.client.service.CakeService;
import com.example.demo.client.service.CategoryService;
import com.example.demo.client.service.MaterialService;
import com.example.demo.client.service.StoreService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.GreaterThan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShopServletTest {
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private CakeService cakeService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private StoreService storeService;
    @Mock
    private MaterialService materialService;
    @InjectMocks
    private ShopServlet shopServlet;


    public static Stream<Arguments> fakeData() {
        return Stream.of(
                Arguments.of("1", "", "", "", "", "", "", "", ""),
                Arguments.of("null", "", "", "expy", "-1", "", "", "", ""),
                Arguments.of("4", "", "", "", "a", "", "", "", "-1"),
                Arguments.of("", "", "", "", "0", "", "", "-1", "-1000")
        );
    }

    @BeforeEach
    public void setup() throws ServletException {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        when(cakeService.CountSearch(any())).thenReturn(5);
        when(storeService.GetAll()).thenReturn(new ArrayList<>());
        when(materialService.getAll()).thenReturn(new ArrayList<>());
        when(cakeService.Search(any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
    }

    @ParameterizedTest
    @MethodSource("fakeData")
    public void test(String page, String query, String sortBy, String direction, String categoryId, String storeId, String materialId, String from, String to) throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn(page);
        when(request.getParameter("query")).thenReturn(query);
        when(request.getParameter("sortBy")).thenReturn(sortBy);
        when(request.getParameter("direction")).thenReturn(direction);

        when(request.getParameter("categoryId")).thenReturn(categoryId);
        when(request.getParameter("storeId")).thenReturn(storeId);
        when(request.getParameter("materialId")).thenReturn(materialId);
        when(request.getParameter("from")).thenReturn(from);
        when(request.getParameter("to")).thenReturn(to);

        when(request.getRequestDispatcher("/WEB-INF/views/shop/index.jsp"))
                .thenReturn(dispatcher);
        shopServlet.doGet(request, response);

        verify(request).setAttribute(eq("cakes"), anyList());
        verify(request).setAttribute(eq("materials"), anyList());
        verify(request).setAttribute(eq("categories"), anyList());
        verify(request).setAttribute(eq("stores"), anyList());

        verify(request).setAttribute(eq("pageSize"), intThat(new GreaterThan<>(-1)));
        verify(request).setAttribute(eq("pageCount"), intThat(new GreaterThan<>(-1)));
        verify(request).setAttribute(eq("page"), intThat(new GreaterThan<>(0)));
        verify(request).setAttribute(eq("startPage"), intThat(new GreaterThan<>(0)));
        verify(request).setAttribute(eq("endPage"), intThat(new GreaterThan<>(0)));

        verify(request).getRequestDispatcher("/WEB-INF/views/shop/index.jsp");
        verify(dispatcher).forward(request, response);
    }
}
