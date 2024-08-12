package com.example.demo.client.servlet;

import com.example.demo.client.service.UserService;
import com.example.demo.client.service.impl.UserServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "AuthenServlet", value = {"/login", "/register",
        "/forgot-password", "/change-password"})
public class AuthenServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/login":
                this.login(request, response);
                break;
            case "/register":
                this.register(request, response);
                break;
            case "/forgot-password":
                this.forgotPassword(request, response);
                break;
            case "/change-password":
                this.changePassword(request, response);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/login":
                this.formLogin(request, response);
                break;
            case "/register":
                this.formRegister(request, response);
                break;
            case "/forgot-password":
                this.formForgotPassword(request, response);
                break;
        }
    }

    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otp = request.getParameter("otp");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String username = request.getParameter("username");

        request.setAttribute("username", username);
        if (!password1.equals(password2)) {
            request.setAttribute("errorsPassword", "mật khẩu không khớp");
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/change-password.jsp").forward(request, response);
            return;
        }
        boolean flag = userService.changePassword(password1, username, otp);
        if (flag) {
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorsOtp", "otp không đúng hoặc đã hết hạn");
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/change-password.jsp").forward(request, response);
        }
    }

    public void formLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constant.PATH_CLIENT + "/login.jsp").forward(request, response);
    }

    public void formRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constant.PATH_CLIENT + "/register.jsp").forward(request, response);
    }

    public void formForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constant.PATH_CLIENT + "/forgot-password.jsp").forward(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean flag = userService.login(email, password);
        if (flag) {
            User user = userService.getUserByUsername(email);
            request.getSession().setAttribute("username", user.getUsername());
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/index.jsp").forward(request, response);
        } else {
            request.setAttribute("errors", "Thông tin tài khoản không chính xác");
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/login.jsp").forward(request, response);
        }
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String> errors = userService.validateRegister(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/register.jsp").forward(request, response);
            return;
        }
        String email = request.getParameter("email");
        String password = request.getParameter("password1");
        String fullName = request.getParameter("fullName");
        User user = new User();
        user.setFullName(fullName);
        user.setPassword(password);
        user.setUsername(email);
        user.setStatus(Constant.ACTIVE);
        user.setRole(Constant.ROLE.USER);
        user.setCreatedBy("hello");
        boolean flag = userService.register(user);
        if (flag) {
            response.sendRedirect("/login");
        } else {
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/register.jsp").forward(request, response);
        }
    }

    public void forgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("email");
        if (StringUtil.stringIsNullOrEmty(userService.getUserByUsername(username))) {
            request.setAttribute("errors", "email không tồn tại hoặc đã bị xóa");
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/forgot-password.jsp").forward(request, response);
        } else {
            userService.sendOTPRestPassword(username);
            request.setAttribute("username", username);
            request.getRequestDispatcher(Constant.PATH_CLIENT + "/change-password.jsp").forward(request, response);
        }
    }

}
