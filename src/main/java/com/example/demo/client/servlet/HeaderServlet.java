package com.example.demo.client.servlet;

import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "HeaderServlet", value = {"/client/header/view"})
public class HeaderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (!StringUtil.stringIsNullOrEmty(session)) {
            req.setAttribute("isLogin", session.getAttribute("user"));
        } else {
            req.setAttribute("isLogin", null);
        }
        req.getRequestDispatcher("/WEB-INF/common/header.jsp").forward(req, resp);
    }


}
