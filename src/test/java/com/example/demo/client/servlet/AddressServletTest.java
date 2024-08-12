package com.example.demo.client.servlet;

import com.example.demo.client.service.AddressService;
import com.example.demo.client.service.UserService;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressServletTest {

    @InjectMocks
    private AddressServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private StringWriter responseWriter;

    @Mock
    private AddressService addressService;

    @Mock
    private UserService userService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new AddressServlet(addressService, userService);

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        Mockito.when(request.getRequestDispatcher(ArgumentMatchers.anyString())).thenReturn(requestDispatcher);
    }

    @ParameterizedTest
    @CsvSource({
            ",1", "hello,1", ","
    })
    public void testDoGetView(String search, String page) throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/address/view");

        Mockito.when(request.getParameter("search")).thenReturn(search);
        Mockito.when(session.getAttribute("username")).thenReturn("sonpt");
        Mockito.when(request.getParameter("page")).thenReturn(page);
        User user = new User();
        user.setId(1);
        Mockito.when(userService.getUserByUsername("sonpt")).thenReturn(user);

        servlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_CLIENT + Constant.ADDRESS_FOLDER_FILE + "/view-address.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({
            "1", "2", "3", "4"
    })
    public void testDoGetUpdate(String id) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/address/update");
        Mockito.when(request.getParameter("id")).thenReturn(id);

        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(Map.of("full_name", "Hà Nội"));
        mapList.add(Map.of("full_name", "Hòa bình"));
//
//        Mockito.when(addressService.getProvince()).thenReturn();
//        Mockito.when(addressService.getDistrict("1")).thenReturn(Map.of("full_name", "Hà Nội"));
//        Mockito.when(addressService.getWards("1")).thenReturn(new HashMap<>());

//        Mockito.when(servlet.getProvinces()).thenReturn(null);
//        Mockito.when(servlet.getDistricts("id")).thenReturn(null);
//        Mockito.when(servlet.getWards("id")).thenReturn(null);
        Mockito.when(addressService.getAddressById(Long.parseLong(id))).thenReturn(null);

        servlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_CLIENT + Constant.ADDRESS_FOLDER_FILE + "/view-address.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);

    }

}
