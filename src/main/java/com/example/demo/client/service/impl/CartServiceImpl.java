package com.example.demo.client.service.impl;

import com.example.demo.client.dao.CartDao;
import com.example.demo.client.dto.CartViewDto;
import com.example.demo.client.service.CartService;

import java.util.List;

public class CartServiceImpl implements CartService {
    private final CartDao cartDao = new CartDao();

    @Override
    public String addCart(Integer userId, String username, Integer storeId, Integer quantity) {
        CartViewDto productCart = cartDao.getCartById(userId, storeId);

        if (quantity < 0) {
            return "Product quantity is negative";
        } else if (productCart == null) {
            return cartDao.addCart(userId, username, storeId, quantity);
        } else if (productCart.isExpired()) {
            return "Product is expired";
        } else {
            quantity += productCart.getQuantity();
            return cartDao.updateById(userId, username, productCart.getId(), quantity);
        }
    }

    @Override
    public String updateCart(Integer userId, String username, Integer cartId, Integer quantity) {
        if (quantity < 1) {
            return "Product quantity must be positive";
        } else {
            return cartDao.updateById(userId, username, cartId, quantity);
        }
    }


    @Override
    public String removeCart(Integer userId, Integer cartId) {
        return cartDao.deleteById(userId, cartId);
    }

    @Override
    public List<CartViewDto> getCarts(Integer userId) {
        return cartDao.getCartsByUserId(userId);
    }
}
