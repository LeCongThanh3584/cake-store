package com.example.demo.client.service;

import com.example.demo.client.dto.CartViewDto;

import java.util.List;

public interface CartService {
    String addCart(Integer userId, String username, Integer storeId, Integer quantity);

    String updateCart(Integer userId, String username, Integer cartId, Integer quantity);

    String removeCart(Integer userId, Integer cartId);

    List<CartViewDto> getCarts(Integer userId);
}
