package com.example.demo.client.dao;

import com.example.demo.entity.Address;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDao {
    private static final String GET_ADDRESS = "select * from address " +
            "where address.deleted_at is null and id_user = ? and id = ?;";
    private static final String GET_ADDRESSES = "select * from address " +
            "where address.deleted_at is null and address.id_user = ?;";

    private static final String GET_ALL_ADDRESS_BY_USER = "SELECT * FROM address WHERE id_user = ? AND" +
            " name like CONCAT('%', ? ,'%') AND deleted_at IS NULL ORDER BY created_at DESC LIMIT ?,?";

    private static final String GET_TOTAL_ADDRESS_BY_USER = "SELECT COUNT(*) FROM address WHERE id_user = ? AND" +
            " name like CONCAT('%', ? ,'%') AND deleted_at IS NULL ORDER BY created_at DESC";

    private static final String GET_ALL_ADDRESS_BY_ID = "SELECT * FROM address WHERE id_user = ? AND deleted_at IS NULL ORDER BY created_at DESC";


    private static final String GET_ADDRESS_BY_ID = "SELECT ad.* FROM address ad WHERE ad.id = ?";

    private static final String INSERT_ADDRESS = "INSERT INTO address (id_user, name, province, district, ward, phone, created_at, created_by) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";

    private static final String UPDATE_ADDRESS = "UPDATE address SET name = ?, province = ?, district = ?, ward = ?, phone = ?, updated_at = NOW(), updated_by = ? WHERE id = ?";

    public Boolean updateAddress(Address address) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS);
            preparedStatement.setString(1, address.getName());
            preparedStatement.setString(2, address.getProvince());
            preparedStatement.setString(3, address.getDistrict());
            preparedStatement.setString(4, address.getWard());
            preparedStatement.setString(5, address.getPhone());
            preparedStatement.setString(6, address.getUpdatedBy());
            preparedStatement.setLong(7, address.getId());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                connection.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean createAddress(Address address) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADDRESS);
            preparedStatement.setLong(1, address.getIdUser());
            preparedStatement.setString(2, address.getName());
            preparedStatement.setString(3, address.getProvince());
            preparedStatement.setString(4, address.getDistrict());
            preparedStatement.setString(5, address.getWard());
            preparedStatement.setString(6, address.getPhone());
            preparedStatement.setString(7, address.getCreatedBy());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                connection.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Address getAddress(int userId, int addressId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_ADDRESS);
            statement.setInt(1, userId);
            statement.setInt(2, addressId);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Address address = new Address();
            address.setId(resultSet.getInt("id"));
            address.setIdUser(resultSet.getInt("id_user"));
            address.setName(resultSet.getString("name"));
            address.setProvince(resultSet.getString("province"));
            address.setDistrict(resultSet.getString("district"));
            address.setPhone(resultSet.getString("phone"));
            address.setWard(resultSet.getString("ward"));

            return address;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Address> getAddresses(int userId) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            List<Address> addresses = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(GET_ADDRESSES);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setIdUser(resultSet.getInt("id_user"));
                address.setName(resultSet.getString("name"));
                address.setProvince(resultSet.getString("province"));
                address.setDistrict(resultSet.getString("district"));
                address.setPhone(resultSet.getString("phone"));
                address.setWard(resultSet.getString("ward"));

                addresses.add(address);
            }
            return addresses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Address> getAllAddressByUserId(int page, int pageSize, int userId, String search) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ADDRESS_BY_USER);
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, search);
            preparedStatement.setInt(3, page);
            preparedStatement.setInt(4, pageSize);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Address> listAddress = new ArrayList<>();
            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setIdUser(resultSet.getInt("id_user"));
                address.setName(resultSet.getString("name"));
                address.setProvince(resultSet.getString("province"));
                address.setDistrict(resultSet.getString("district"));
                address.setPhone(resultSet.getString("phone"));
                address.setWard(resultSet.getString("ward"));
                listAddress.add(address);
            }
            return listAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotalRecords(int userId, String search) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_TOTAL_ADDRESS_BY_USER);
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, search);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Address getAddressById(long id) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setIdUser(resultSet.getInt("id_user"));
                address.setName(resultSet.getString("name"));
                address.setProvince(resultSet.getString("province"));
                address.setDistrict(resultSet.getString("district"));
                address.setPhone(resultSet.getString("phone"));
                address.setWard(resultSet.getString("ward"));
                return address;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
