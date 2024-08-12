package com.example.demo.client.servlet;

import com.example.demo.client.dto.OrderViewDto;
import com.example.demo.client.service.CheckoutService;
import com.example.demo.client.service.impl.CheckoutServiceImpl;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "checkoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {
    CheckoutService checkoutService;

    public void init() {
        checkoutService = new CheckoutServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Address address = getAddress(request);
        User user = (User) request.getAttribute("user");
        String[] cartIds = request.getParameterValues("cartId");
        List<OrderViewDto> orderViews = checkoutService.CreateOrder(user.getId(), address, cartIds);

        if (orderViews.isEmpty()) {
            response.sendRedirect("/cart");
            return;
        }
        request.setAttribute("orderViews", orderViews);
        request.setAttribute("address", address);
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            Address address = getAddress(request);
            User user = (User) request.getAttribute("user");
            String[] cartIds = request.getParameterValues("cartId");

            validateAddress(request, address);
            checkoutService.saveOrders(user.getId(), user.getUsername(), address, cartIds);
            response.sendRedirect("/index");
        } catch (RuntimeException e) {
            doGet(request, response);
        }
    }

    private void validateAddress(HttpServletRequest request, Address address) {
        boolean isError = false;
        if (address.getWard() == "") {
            request.setAttribute("wardError", "Please enter a valid ward");
            isError = true;
        }
        if (address.getProvince() == "") {
            request.setAttribute("provinceError", "Please enter a valid province");
            isError = true;
        }
        if (address.getName() == "") {
            request.setAttribute("receiverError", "Please enter a valid name");
            isError = true;
        }
        if (address.getPhone() == "") {
            request.setAttribute("phoneError", "Please enter a valid phone");
            isError = true;
        }
        if (address.getDistrict() == "") {
            request.setAttribute("districtError", "Please enter a valid district");
            isError = true;
        }
        if (isError) {
            throw new RuntimeException();
        }
    }

    private Address getAddress(HttpServletRequest request) {
        Address address = new Address();
        address.setProvince(ParamUtil.get(request, "province"));
        address.setWard(ParamUtil.get(request, "ward"));
        address.setDistrict(ParamUtil.get(request, "district"));
        address.setPhone(ParamUtil.get(request, "phone"));
        address.setName(ParamUtil.get(request, "receiver"));

        return address;
    }

    public void destroy() {
    }
}