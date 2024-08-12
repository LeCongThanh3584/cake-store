package com.example.demo.client.servlet;

import com.example.demo.client.dto.CartViewDto;
import com.example.demo.client.service.AddressService;
import com.example.demo.client.service.CartService;
import com.example.demo.client.service.impl.AddressServiceImpl;
import com.example.demo.client.service.impl.CartServiceImpl;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "cartServlet", value = "/cart/*")
public class CartServlet extends HttpServlet {
    private CartService cartService;
    private AddressService addressService;

    public void init() {
        cartService = new CartServiceImpl();
        addressService = new AddressServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        switch (request.getRequestURI()) {
            case "/cart":
                this.renderPage(request, response);
                break;
            case "/cart/delete":
                this.removeCart(request, response);
                break;
            default:
                response.sendRedirect("/cart");
        }
    }

    private void renderPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        List<CartViewDto> carts = cartService.getCarts(user.getId());
        List<Address> addresses = addressService.getAddresses(user.getId());

        request.setAttribute("carts", carts);
        request.setAttribute("addresses", addresses);
        request.getRequestDispatcher("/WEB-INF/views/cart/index.jsp")
                .forward(request, response);
    }

    private void removeCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            User user = (User) request.getAttribute("user");
            String error = cartService.removeCart(user.getId(), cartId);

            request.setAttribute("error", error);
            renderPage(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid cart id");
            renderPage(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getPathInfo() == null) {
            this.addCart(request, response);
        } else if (request.getPathInfo().equals("/update")) {
            this.updateCart(request, response);
        } else {
            response.sendRedirect("/cart");
        }
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int cakeStoreId = Integer.parseInt(request.getParameter("cakeStoreId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            User user = (User) request.getAttribute("user");

            String error = cartService.addCart(user.getId(), user.getUsername(), cakeStoreId, quantity);
            request.setAttribute("error", error);
            renderPage(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Please enter a valid number");
            renderPage(request, response);
        }
    }


    private void updateCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int cartId = Integer.parseInt(request.getParameter("cartId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            User user = (User) request.getAttribute("user");

            String error = cartService.updateCart(user.getId(), user.getUsername(), cartId, quantity);
            request.setAttribute("error", error);
            renderPage(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Please enter a valid number");
            renderPage(request, response);
        }
    }

    public void destroy() {
    }
}