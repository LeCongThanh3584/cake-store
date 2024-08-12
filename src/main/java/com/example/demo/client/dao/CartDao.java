package com.example.demo.client.dao;

import com.example.demo.client.dto.CartViewDto;
import com.example.demo.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private static final String GET_CARTS_BY_USER_ID = "select product_cart.*," +
            "cake.name,cake.image,cake_store.*,store.* from product_cart\n" +
            "     join cake_store on product_cart.id_cake_store = cake_store.id\n" +
            "     join store on cake_store.id_store = store.id\n" +
            "     join cake on cake_store.id_cake = cake.id and product_cart.id_user = ?;";
    private static final String GET_CART_BY_ID = "select product_cart.*," +
            "     cake_store.* from product_cart\n" +
            "     join cake_store on product_cart.id_cake_store = cake_store.id\n" +
            "     where product_cart.id_user = ? and product_cart.id_cake_store = ?;";
    private static final String ADD_CART = "insert into product_cart (id_user, id_cake_store," +
            " quantity, created_at, created_by)\n" +
            "values (?,?,?,?,?);";

    private static final String DELETE_BY_ID = "delete from product_cart where " +
            "product_cart.id_user = ? and product_cart.id = ?;";

    private static final String UPDATE_BY_ID = "update product_cart\n" +
            "set product_cart.quantity = ?, product_cart.updated_at = now(),product_cart.updated_by = ?\n" +
            "where product_cart.id_user = ? and product_cart.id = ?;";

    public CartViewDto getCartById(Integer userId, Integer storeId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_CART_BY_ID);
            statement.setInt(1, userId);
            statement.setInt(2, storeId);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                CartViewDto cartDto = new CartViewDto();
                cartDto.setId(result.getInt("product_cart.id"));
                cartDto.setCakeStoreId(result.getInt("product_cart.id_cake_store"));
                cartDto.setQuantity(result.getInt("product_cart.quantity"));

                cartDto.setStoreId(result.getInt("cake_store.id_store"));
                cartDto.setPrice(result.getBigDecimal("cake_store.price"));
                cartDto.setStock(result.getInt("cake_store.quantity"));
                cartDto.setExpirationDate(result.getTimestamp("cake_store.expiration_date").toLocalDateTime());
                return cartDto;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CartViewDto> getCartsByUserId(Integer userId) {
        List<CartViewDto> carts = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_CARTS_BY_USER_ID);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                CartViewDto cartDto = new CartViewDto();
                cartDto.setId(result.getInt("product_cart.id"));
                cartDto.setCakeStoreId(result.getInt("product_cart.id_cake_store"));
                cartDto.setQuantity(result.getInt("product_cart.quantity"));

                cartDto.setStoreName(result.getString("store.name"));
                cartDto.setStoreAddress(result.getString("store.address"));

                cartDto.setName(result.getString("cake.name"));
                cartDto.setImage(result.getString("cake.image"));

                cartDto.setStock(result.getInt("cake_store.quantity"));
                cartDto.setPrice(result.getBigDecimal("cake_store.price"));
                cartDto.setStoreId(result.getInt("cake_store.id_store"));
                cartDto.setExpirationDate(result.getTimestamp("cake_store.expiration_date").toLocalDateTime());

                carts.add(cartDto);
            }
            return carts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String addCart(Integer userId, String username, Integer storeId, Integer quantity) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_CART);
            statement.setInt(1, userId);
            statement.setInt(2, storeId);
            statement.setInt(3, quantity);
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(5, username);

            int result = statement.executeUpdate();
            if (!(result > 0)) {
                throw new SQLException("Add cart failed");
            }
            connection.commit();
            return null;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String updateById(Integer userId, String username, Integer cartId, Integer quantity) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID);
            statement.setInt(1, quantity);
            statement.setString(2, username);
            statement.setInt(3, userId);
            statement.setInt(4, cartId);

            int result = statement.executeUpdate();
            if (!(result > 0)) {
                throw new SQLException("Update cart failed");
            }
            connection.commit();
            return null;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String deleteById(Integer userId, Integer cartId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setInt(1, userId);
            statement.setInt(2, cartId);

            int result = statement.executeUpdate();
            if (!(result > 0)) {
                throw new SQLException("Delete product cart failed");
            }
            connection.commit();
            return null;
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
