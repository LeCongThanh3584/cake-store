package com.example.demo.filter;

import com.example.demo.client.service.UserService;
import com.example.demo.client.service.impl.UserServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.util.Constant.ROLE;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "userFilter", urlPatterns = {"/admin/*", "/shipper/*", "/cart/*", "/checkout/*", "/contact/*"})
public class UserFilter implements Filter {
    List<String> superAdmins = List.of("/admin/cake", "/admin/category", "/admin/material",
            "/admin/add-store", "/admin/update-store", "/admin/delete-store", "/admin/users",
            "/admin/add-new-user", "/admin/update-user", "/admin/delete-user", "/admin/user",
            "/admin/add-new-address", "/admin/update-address", "/admin/delete-address");
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        userService = new UserServiceImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        String username = (String) session.getAttribute("username");
        User user = userService.getUserByUsername(username);
//        User user = userService.getUserByUsername("chinh@gmail.com");

        if (user == null) {
            session.invalidate();
            resp.sendRedirect("/login");
        } else {
            String requestURI = req.getRequestURI();
            Integer role = user.getRole();
            req.setAttribute("user", user);
            session.setAttribute("role", role);

            boolean isSuperAdminPath = superAdmins.stream().anyMatch(requestURI::startsWith);
            boolean isAdminPath = !isSuperAdminPath && requestURI.startsWith("/admin");
            boolean isShipperPath = requestURI.startsWith("/shipper");

            if (isSuperAdminPath && role.equals(ROLE.SUPER_ADMIN)) {
                filterChain.doFilter(request, servletResponse);
            } else if (isAdminPath && (role.equals(ROLE.ADMIN) || role.equals(ROLE.SUPER_ADMIN))) {
                filterChain.doFilter(request, servletResponse);
            } else if (isShipperPath && role.equals(ROLE.SHIPPER)) {
                filterChain.doFilter(request, servletResponse);
            } else if (!isSuperAdminPath && !isAdminPath && !isShipperPath) {
                filterChain.doFilter(request, servletResponse);
            } else {
                if (role.equals(ROLE.ADMIN)) {
                    resp.sendRedirect("/admin");
                } else if (role.equals(ROLE.SHIPPER)) {
                    resp.sendRedirect("/shipper");
                } else {
                    resp.sendRedirect("/index");
                }
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
