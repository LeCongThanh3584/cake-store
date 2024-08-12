package com.example.demo.client.servlet;

import com.example.demo.client.dto.CakeViewDto;
import com.example.demo.client.service.CakeService;
import com.example.demo.client.service.impl.CakeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "indexServlet", value = "/index")
public class IndexServlet extends HttpServlet {
    private CakeService cakeService;

    public void init() {
        cakeService = new CakeServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List<CakeViewDto> cakes = cakeService.GetRecommendation();
        request.setAttribute("cakes", cakes);
        request.getRequestDispatcher("/WEB-INF/views/index.jsp")
                .forward(request, response);
    }

    public void destroy() {
    }
}