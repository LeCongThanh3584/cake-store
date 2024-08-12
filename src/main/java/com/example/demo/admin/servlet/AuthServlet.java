package com.example.demo.admin.servlet;

import com.example.demo.admin.service.UserService;
import com.example.demo.admin.service.impl.UserServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="AuthServlet",value = {"/sign-in","/sign-out",
        "/forgot-password-admin","/reset-password"})
public class AuthServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/sign-in":
                this.formLogin(request,response);
                break;
            case "/sign-out":
                this.signOut(request,response);
                break;
            case "/forgot-password-admin":
                this.formForgotPassword(request, response);
                break;
            case "/reset-password":
                this.formResetPassword(request, response);
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/sign-in":
                this.signIn(request,response);
                break;
            case "/forgot-password-admin":
                this.forgotPassword(request, response);
                break;
            case "/reset-password":
                this.resetPassword(request, response);
                break;
        }
    }

    public void formLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/login.jsp").forward(request, response);
    }

    public void signIn(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean flag = userService.login(email, password);
        if (flag) {
            User user = userService.getUserByUsername(email);
            request.getSession().setAttribute("username", user.getUsername());
            request.getSession().setAttribute("role", user.getRole());
            request.getSession().setAttribute("storeId", user.getIdStore());
            response.sendRedirect("/admin");
        } else {
            request.setAttribute("errors", "Thông tin tài khoản không chính xác");
            request.getRequestDispatcher(Constant.PATH_ADMIN + "/login.jsp").forward(request, response);
        }
    }

    public void signOut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("/sign-in");
    }

    public void formForgotPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/forgot-password.jsp")
                .forward(request, response);
    }

    public void forgotPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("email");
        boolean flag = userService.existByUsername(username);
        if (flag) {
            userService.sendOTPRestPassword(username);
            request.setAttribute("message", "A password reset link has been sent to your email. Please check your email to reset your password.");
            request.getRequestDispatcher(Constant.PATH_ADMIN + "/forgot-password.jsp").forward(request, response);
        } else {
            request.setAttribute("errors", "email không tồn tại hoặc đã bị xóa");
            request.getRequestDispatcher(Constant.PATH_ADMIN + "/forgot-password.jsp").forward(request, response);
        }
    }

    public void formResetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        request.setAttribute("token", token);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/reset-password.jsp")
                .forward(request, response);
    }

    public void resetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String password1 = request.getParameter("password");
        String password2 = request.getParameter("password1");

        if (!password1.equals(password2)) {
            request.setAttribute("token",token);
            request.setAttribute("errorsPassword", "Passwords are not the same");
            request.getRequestDispatcher(Constant.PATH_ADMIN + "/reset-password.jsp").forward(request, response);
            return;
        }
        boolean flag = userService.changePassword(password1, token);
        if (flag) {
            request.getRequestDispatcher(Constant.PATH_ADMIN + "/login.jsp").forward(request, response);
        } else {
            request.setAttribute("errorsToken", "The link has expired. Please request a new one.");
            request.getRequestDispatcher(Constant.PATH_ADMIN + "/reset-password.jsp").forward(request, response);
        }
    }
}
