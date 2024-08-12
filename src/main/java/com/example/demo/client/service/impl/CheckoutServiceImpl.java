package com.example.demo.client.service.impl;

import com.example.demo.client.dao.CartDao;
import com.example.demo.client.dao.OrderDao;
import com.example.demo.client.dto.CartViewDto;
import com.example.demo.client.dto.OrderViewDto;
import com.example.demo.client.service.CheckoutService;
import com.example.demo.entity.Address;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutServiceImpl implements CheckoutService {
    CartDao cartDao = new CartDao();
    OrderDao orderDao = new OrderDao();

    @Override
    public List<OrderViewDto> CreateOrder(Integer userId, Address address, String[] cartIds) {
        List<CartViewDto> list = cartDao.getCartsByUserId(userId);
        List<OrderViewDto> orderViews = new ArrayList<>();

        if (cartIds == null) {
            return orderViews;
        }
        Arrays.stream(cartIds).forEach(cartId -> {
            CartViewDto cartViewDto = list.stream()
                    .filter(item -> item.getId().equals(Integer.parseInt(cartId)))
                    .findFirst().orElse(null);

            if (cartViewDto == null) {
                throw new RuntimeException("Product does not exist in your cart");
            } else if (cartViewDto.getStock() < cartViewDto.getQuantity()) {
                throw new RuntimeException("Product don't has enough stock");
            } else if (cartViewDto.isExpired()) {
                throw new RuntimeException("Product has expired");
            } else {
                OrderViewDto orderView = orderViews.stream()
                        .filter(item -> item.getIdStore().equals(cartViewDto.getStoreId()))
                        .findFirst().orElse(null);
                if (orderView == null) {
                    orderView = new OrderViewDto();
                    orderView.setIdStore(cartViewDto.getStoreId());
                    orderView.setStoreAddress(cartViewDto.getStoreAddress());
                    orderView.setStoreName(cartViewDto.getStoreName());

                    orderView.setAddress(address);
                    orderView.setIdStore(cartViewDto.getStoreId());
                    orderView.setIdUser(userId);
                    orderView.setPhone(address.getPhone());
                    orderView.setReceiver(address.getName());

                    orderViews.add(orderView);
                }
                orderView.addDetail(cartViewDto);
            }

        });
        return orderViews;
    }

    @Override
    public void saveOrders(Integer userId, String username, Address address, String[] cartIds) {
        List<OrderViewDto> orderViews = CreateOrder(userId, address, cartIds);

        orderViews.forEach(orderView -> {
            orderDao.CreateOrder(userId, username, orderView);
        });
        orderViews.forEach(orderView -> {
            orderView.getOrderDetails().forEach(detail -> {
                cartDao.deleteById(userId, detail.getId());
            });
        });

    }
}
