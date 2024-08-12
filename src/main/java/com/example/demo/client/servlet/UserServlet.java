package com.example.demo.client.servlet;

import com.example.demo.client.service.UserService;
import com.example.demo.client.service.impl.UserServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "UserServlet", value = {"/user-info", "/order-history", "/logout", "/update-password"})
public class UserServlet extends HttpServlet {

    private UserService userService;

    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    public UserServlet() {
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/user-info":
                this.userInfo(req, resp);
                break;
            case "/order-history":
                this.orderHistory(req, resp);
                break;
            case "/logout":
                this.logout(req, resp);
                break;
            case "/update-password":
                this.formUpdatePassword(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/update-password":
                this.updatePassword(req, resp);
                break;
        }
    }

    public void userInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String username = (String) httpSession.getAttribute("username");
        User user = userService.getUserByUsername(username);
        request.setAttribute("user", user);
        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/info.jsp").forward(request, response);
    }

    public void formUpdatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String username = (String) httpSession.getAttribute("username");
        User user = userService.getUserByUsername(username);
        request.setAttribute("user", user);
        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/change-password.jsp").forward(request, response);
    }

    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String username = (String) httpSession.getAttribute("username");

        Map<String, String> errors = userService.validateChangePassword(request, username);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/change-password.jsp")
                    .forward(request, response);
            return;
        }

        boolean result = userService.updatePassword(username, PasswordUtil.hashPassword(request.getParameter("password1")));
        if (result) {
            request.getSession().setAttribute("response", "update password successfully!");
        } else {
            request.getSession().setAttribute("response", "update password failed!");
        }

        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/change-password.jsp").forward(request, response);
    }


    public void orderHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");
//        List<Address> listAddress = addressService.getAddresses(user.getId());
//        request.setAttribute("listAddress", listAddress);
        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.USER_FOLDER_FILE + "/user-view-address.jsp").forward(request, response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("/index");
    }
}
