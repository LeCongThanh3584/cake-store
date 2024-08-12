package com.example.demo.client.servlet;

import com.example.demo.client.dto.CakeDetailDto;
import com.example.demo.client.service.CakeService;
import com.example.demo.client.service.StoreService;
import com.example.demo.client.service.impl.CakeServiceImpl;
import com.example.demo.client.service.impl.StoreServiceImpl;
import com.example.demo.entity.Store;
import com.example.demo.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "detailServlet", urlPatterns = "/detail/*")
public class DetailsServlet extends HttpServlet {
    private CakeService cakeService;
    private StoreService storeService;

    public void init() {
        cakeService = new CakeServiceImpl();
        storeService = new StoreServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cakeId = ParamUtil.parseInt(request.getPathInfo().substring(1));
        int storeId = ParamUtil.getInt(request, "storeId");

        List<Store> stores = storeService.GetStores(cakeId);
        if (stores.isEmpty()) {
            response.sendRedirect("/shop");
            return;
        }
        Store store = stores.stream()
                .filter(item -> item.getId().equals(storeId))
                .findFirst().orElse(stores.get(0));

        CakeDetailDto cake = cakeService.GetCakeById(store.getId(), cakeId);
        request.setAttribute("stores", stores);
        request.setAttribute("cake", cake);
        request.getRequestDispatcher("/WEB-INF/views/detail.jsp")
                .forward(request, response);
    }

    public void destroy() {
    }
}
