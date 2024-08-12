package com.example.demo.client.servlet;

import com.example.demo.client.service.UserService;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class UserServletTest {

    @InjectMocks
    private UserServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private StringWriter responseWriter;

    @Mock
    private UserService userService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new UserServlet(userService);

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        Mockito.when(request.getRequestDispatcher(ArgumentMatchers.anyString())).thenReturn(requestDispatcher);
    }


    @Test
    public void testDoGetUserInfo() throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/user-info");
        Mockito.when(session.getAttribute("username")).thenReturn("sonpt");

        servlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/info.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }


    @Test
    public void testDoGetPassword() throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/update-password");
        Mockito.when(session.getAttribute("username")).thenReturn("sonpt");

        servlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/change-password.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostPasswordSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/update-password");
        Mockito.when(session.getAttribute("username")).thenReturn("sonptt");
        Mockito.when(request.getParameter("password1")).thenReturn(ArgumentMatchers.anyString());
        Mockito.when(request.getParameter("password2")).thenReturn(ArgumentMatchers.anyString());
        Mockito.when(userService.updatePassword("sonptt", ArgumentMatchers.anyString())).thenReturn(true);
        servlet.doPost(request, response);

        Mockito.verify(session).setAttribute("response", "update password successfully!");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/change-password.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({
            "123123", "124124", "125125"
    })
    public void testDoPostPasswordFail(String password) throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/update-password");
        Mockito.when(session.getAttribute("username")).thenReturn("sonptt");
        Mockito.when(request.getParameter("password1")).thenReturn(password);
        Mockito.when(userService.updatePassword("sonptt", password)).thenReturn(false);
        servlet.doPost(request, response);

        Mockito.verify(session).setAttribute("response", "update password failed!");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/change-password.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetOrderHistory() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/order-history");
        Mockito.when(session.getAttribute("user")).thenReturn(new User());
        servlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/user-view-address.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetLogout() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/logout");
        servlet.doGet(request, response);
        Mockito.verify(response).sendRedirect("/index");
    }


}
