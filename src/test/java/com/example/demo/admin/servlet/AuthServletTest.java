package com.example.demo.admin.servlet;

import com.example.demo.admin.service.UserService;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class AuthServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthServlet authServlet;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        Mockito.when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetSignIn() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/sign-in");
        authServlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/login.jsp");
    }

    @Test
    public void testDoPostSignInSuccess() throws ServletException, IOException {
        String email = "tranminh@gmail.com";
        String password = "@mgfmg38219898";
        Mockito.when(request.getRequestURI()).thenReturn("/sign-in");
        Mockito.when(request.getParameter("email")).thenReturn(email);
        Mockito.when(request.getParameter("password")).thenReturn(password);
        Mockito.when(userService.login(email, password)).thenReturn(true);
        User user = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername(email)).thenReturn(user);

        authServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("username", user.getUsername());
        Mockito.verify(session).setAttribute("role", user.getRole());
        Mockito.verify(session).setAttribute("storeId", user.getIdStore());

        Mockito.verify(response).sendRedirect("/admin");
    }

    @Test
    public void testDoPostSignInFail() throws ServletException, IOException {
        String email = "tranminh@gmail.com";
        String password = "@mgfmg38219898";
        Mockito.when(request.getRequestURI()).thenReturn("/sign-in");
        Mockito.when(request.getParameter("email")).thenReturn(email);
        Mockito.when(request.getParameter("password")).thenReturn(password);
        Mockito.when(userService.login(email, password)).thenReturn(false);

        authServlet.doPost(request, response);

        Mockito.verify(request).setAttribute("errors", "Thông tin tài khoản không chính xác");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/login.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSignOut() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/sign-out");
        Mockito.when(session.getAttribute("username")).thenReturn("admin");
        Mockito.when(session.getAttribute("role")).thenReturn("0");
        Mockito.when(session.getAttribute("storeId")).thenReturn(null);

        authServlet.doGet(request, response);

        Mockito.verify(response).sendRedirect("/sign-in");
    }

    @Test
    public void testDoGetForgotPassword() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/forgot-password-admin");
        authServlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/forgot-password.jsp");
    }

    @Test
    public void testDoPostForgotPasswordSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/forgot-password-admin");
        Mockito.when(request.getParameter("email")).thenReturn("minhwhewr@gmail.com");
        Mockito.when(userService.existByUsername("minhwhewr@gmail.com")).thenReturn(true);

        Mockito.doNothing().when(userService).sendOTPRestPassword("minhwhewr@gmail.com");
        authServlet.doPost(request, response);
        Mockito.verify(request).setAttribute("message", "A password reset link has been sent to your email. Please check your email to reset your password.");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/forgot-password.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostForgotPasswordFail() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/forgot-password-admin");
        Mockito.when(request.getParameter("email")).thenReturn("minhwhewr@gmail.com");
        Mockito.when(userService.existByUsername("minhwhewr@gmail.com")).thenReturn(false);

        authServlet.doPost(request, response);

        Mockito.verify(request).setAttribute("errors", "email không tồn tại hoặc đã bị xóa");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/forgot-password.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetResetPassword() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/reset-password");
        Mockito.when(request.getParameter("token")).thenReturn("ahajgnadfafklafwefhefk@09035510zoekl");

        authServlet.doGet(request, response);
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/reset-password.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostResetPasswordSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/reset-password");
        Mockito.when(request.getParameter("token")).thenReturn("ahajgnadfafklafwefhefk@09035510zoekl");
        Mockito.when(request.getParameter("password")).thenReturn("31072001");
        Mockito.when(request.getParameter("password1")).thenReturn("31072001");

        Mockito.when(userService.changePassword("31072001", "ahajgnadfafklafwefhefk@09035510zoekl")).thenReturn(true);

        authServlet.doPost(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/login.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostResetPassword_invalidPassword() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/reset-password");
        Mockito.when(request.getParameter("token")).thenReturn("ahajgnadfafklafwefhefk@09035510zoekl");
        Mockito.when(request.getParameter("password")).thenReturn("31072001");
        Mockito.when(request.getParameter("password1")).thenReturn("31072001kl");

        authServlet.doPost(request, response);

        Mockito.verify(request).setAttribute("token", "ahajgnadfafklafwefhefk@09035510zoekl");
        Mockito.verify(request).setAttribute("errorsPassword", "Passwords are not the same");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/reset-password.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostResetPasswordFail() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/reset-password");
        Mockito.when(request.getParameter("token")).thenReturn("ahajgnadfafklafwefhefk@09035510zoekl");
        Mockito.when(request.getParameter("password")).thenReturn("31072001");
        Mockito.when(request.getParameter("password1")).thenReturn("31072001");

        Mockito.when(userService.changePassword("31072001", "ahajgnadfafklafwefhefk@09035510zoekl")).thenReturn(false);
        authServlet.doPost(request, response);

        Mockito.verify(request).setAttribute("errorsToken", "The link has expired. Please request a new one.");
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/reset-password.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }
}
