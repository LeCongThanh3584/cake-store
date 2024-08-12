package com.example.demo.client.dao;

import com.example.demo.client.dto.CakeDetailDto;
import com.example.demo.client.dto.CakePage;
import com.example.demo.client.dto.CakeStoreDto;
import com.example.demo.client.dto.CakeViewDto;
import com.example.demo.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CakeDao {
    private static final String GET_RECOMMENDATION = "select MIN(cake_store.price) as \"min_price\",MAX(cake_store.price) as \"max_price\", category.name,cake.*\n" +
            "from cake_store join cake on cake_store.id_cake = cake.id\n" +
            "join category on cake.id_category = category.id\n" +
            "where cake_store.deleted_at is null and cake_store.quantity > 0 and  cake_store.expiration_date > ?" +
            "group by cake_store.id_cake limit 8\n";

    private static final String GET_BY_ID = "select cake_store.*,category.name,cake.* " +
            "from  cake_store join cake\n" +
            "on  cake_store.id_cake = cake.id\n" +
            "join sweet_cake.category on cake.id_category = category.id\n" +
            "where cake_store.deleted_at is null and cake_store.quantity > 0 and cake_store.expiration_date > ? " +
            "and cake_store.id_store = ? and cake.id = ?;";

    private static final String COUNT_SEARCH = "select count(distinct(cake_store.id_cake))\n" +
            "            from cake_store\n" +
            "            left join cake on cake_store.id_cake = cake.id\n" +
            "            left join category on cake.id_category = category.id\n" +
            "            left join material_cake on material_cake.id_cake = cake_store.id_cake\n" +
            "            left join material on material_cake.id_material = material.id\n" +
            "            where cake_store.deleted_at is null and cake_store.quantity > 0 and cake_store.expiration_date > ?\n" +
            "            and cake.name like ?";

    private static final String SEARCH_CAKE = "select MIN(cake_store.price) as min_price,MAX(cake_store.price) as max_price,\n" +
            "       max(cake_store.production_date) as production_date, max(expiration_date) as expiration_date,\n" +
            "       category.name, cake.* from cake_store\n" +
            "        left join cake on cake_store.id_cake = cake.id\n" +
            "        left join category on cake.id_category = category.id\n" +
            "        left join material_cake on material_cake.id_cake = cake_store.id_cake\n" +
            "        left join material on material_cake.id_material = material.id\n" +
            "        where cake_store.deleted_at is null and  cake_store.quantity > 0 and cake_store.expiration_date > ?\n" +
            "        and cake.name like ? ";

    public CakeDetailDto GetById(Integer storeId, Integer cakeId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(2, storeId);
            statement.setInt(3, cakeId);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            CakeDetailDto cake = new CakeDetailDto();
            cake.setId(resultSet.getInt("cake.id"));
            cake.setIdCategory(resultSet.getLong("cake.id_category"));
            cake.setCategory(resultSet.getString("category.name"));

            cake.setName(resultSet.getString("cake.name"));
            cake.setCode(resultSet.getString("cake.code"));
            cake.setImage(resultSet.getString("cake.image"));

            cake.setColor(resultSet.getString("cake.color"));
            cake.setWeight(resultSet.getInt("cake.weight"));
            cake.setLength(resultSet.getInt("cake.length"));
            cake.setHeight(resultSet.getInt("cake.height"));
            cake.setSize(resultSet.getString("cake.size"));

            cake.setDescription(resultSet.getString("cake.description"));
            cake.setStatus(resultSet.getInt("cake.status"));

            do {
                CakeStoreDto cakeStore = new CakeStoreDto();
                cakeStore.setId(resultSet.getInt("cake_store.id"));
                cakeStore.setIdStore(resultSet.getInt("cake_store.id_store"));
                cakeStore.setIdCake(resultSet.getInt("cake_store.id_cake"));
                cakeStore.setPrice(resultSet.getBigDecimal("cake_store.price"));
                cakeStore.setQuantity(resultSet.getInt("cake_store.quantity"));
                cakeStore.setStatus(resultSet.getInt("cake_store.status"));

                cakeStore.setExpirationDate(resultSet.getTimestamp("cake_store.expiration_date").toLocalDateTime());

                cake.addStore(cakeStore);
            } while (resultSet.next());
            return cake;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CakeViewDto> getCakes() {
        List<CakeViewDto> cakes = new ArrayList();

        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_RECOMMENDATION);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CakeViewDto cakeDto = new CakeViewDto();
                cakeDto.setId(resultSet.getInt("cake.id"));
                cakeDto.setName(resultSet.getString("cake.name"));
                cakeDto.setImage(resultSet.getString("cake.image"));
                cakeDto.setCategory(resultSet.getString("category.name"));
                cakeDto.setMinPrice(resultSet.getString("min_price"));
                cakeDto.setMaxPrice(resultSet.getString("max_price"));
                cakes.add(cakeDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cakes;
    }

    private String getSearchStr(CakePage cakePage, String string) {
        String sqlStr = string;
        if (cakePage.getStoreId() != null) {
            sqlStr += " and cake_store.id_store = " + cakePage.getStoreId();
        }
        if (cakePage.getCategoryId() != null) {
            sqlStr += " and category.id = " + cakePage.getCategoryId();
        }
        if (cakePage.getMaterialId() != null) {
            sqlStr += " and  material.id = " + cakePage.getMaterialId();
        }
        if (cakePage.getFrom() != null) {
            sqlStr += " and cake_store.price > " + cakePage.getFrom();
        }
        if (cakePage.getTo() != null) {
            sqlStr += " and cake_store.price <  " + cakePage.getTo();
        }
        return sqlStr;
    }

    public Integer CountSearch(CakePage cakePage) {
        String sqlStr = getSearchStr(cakePage, COUNT_SEARCH);

        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStr);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(2, "%" + cakePage.getQuery() + "%");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CakeViewDto> searchCakes(CakePage cakePage, Integer pageSize, Integer offset) {
        List<CakeViewDto> cakes = new ArrayList<>();
        String sqlStr = getSearchStr(cakePage, SEARCH_CAKE);
        sqlStr += " group by cake_store.id_cake order by " + cakePage.getSortBy() + " " + cakePage.getDirection();
        sqlStr += " limit " + pageSize + " offset " + offset;

        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sqlStr);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(2, "%" + cakePage.getQuery() + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CakeViewDto cakeDto = new CakeViewDto();
                cakeDto.setId(resultSet.getInt("cake.id"));
                cakeDto.setName(resultSet.getString("cake.name"));
                cakeDto.setImage(resultSet.getString("cake.image"));
                cakeDto.setCategory(resultSet.getString("category.name"));
                cakeDto.setMinPrice(resultSet.getString("min_price"));
                cakeDto.setMaxPrice(resultSet.getString("max_price"));

                cakeDto.setExpirationDate(resultSet.getTimestamp("expiration_date").toLocalDateTime());
                cakeDto.setProductionDate(resultSet.getTimestamp("production_date").toLocalDateTime());
                cakes.add(cakeDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cakes;
    }
}
