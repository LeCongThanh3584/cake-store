package com.example.demo.admin.dao;

import com.example.demo.entity.Address;
import com.example.demo.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddressDao {

    public Address getAddressById(Integer idAddress) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM address WHERE deleted_at IS NULL AND id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, idAddress);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Address address = new Address();

                address.setId(resultSet.getInt("id"));
                address.setIdUser(resultSet.getInt("id_user"));
                address.setName(resultSet.getString("name"));
                address.setProvince(resultSet.getString("province"));
                address.setDistrict(resultSet.getString("district"));
                address.setWard(resultSet.getString("ward"));
                address.setPhone(resultSet.getString("phone"));

                return address;
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public Address getAddressByAddressIdAndUserId(Integer addressId, Integer userId) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM address WHERE deleted_at IS NULL AND id = ? AND id_user = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, addressId);
            preparedStatement.setInt(2, userId);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Address address = new Address();

                address.setId(resultSet.getInt("id"));
                address.setIdUser(resultSet.getInt("id_user"));
                address.setName(resultSet.getString("name"));
                address.setProvince(resultSet.getString("province"));
                address.setDistrict(resultSet.getString("district"));
                address.setWard(resultSet.getString("ward"));
                address.setPhone(resultSet.getString("phone"));

                return address;
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public boolean addNewAddress(Address newAddress) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "INSERT INTO address(name, province, district, ward, phone, id_user, created_at, created_by) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, newAddress.getName());
            preparedStatement.setString(2, newAddress.getProvince());
            preparedStatement.setString(3, newAddress.getDistrict());
            preparedStatement.setString(4, newAddress.getWard());
            preparedStatement.setString(5, newAddress.getPhone());
            preparedStatement.setInt(6, newAddress.getIdUser());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, newAddress.getCreatedBy());

            int result = preparedStatement.executeUpdate();

            if(result > 0) {
                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return false;
    }

    public boolean updateAddress(Address newAddress) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "UPDATE address SET name = ?, province = ?, " +
                "district = ?, ward = ?, phone = ?, updated_at = ?, updated_by = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, newAddress.getName());
            preparedStatement.setString(2, newAddress.getProvince());
            preparedStatement.setString(3, newAddress.getDistrict());
            preparedStatement.setString(4, newAddress.getWard());
            preparedStatement.setString(5, newAddress.getPhone());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, newAddress.getUpdatedBy());
            preparedStatement.setInt(8, newAddress.getId());

            int result = preparedStatement.executeUpdate();

            if(result > 0) {
                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public List<Address> getListAddressByUserId(Integer userId) {
        List<Address> addressList = new ArrayList<>();

        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM address WHERE deleted_at IS NULL AND id_user = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setIdUser(resultSet.getInt("id_user"));
                address.setName(resultSet.getString("name"));
                address.setProvince(resultSet.getString("province"));
                address.setDistrict(resultSet.getString("district"));
                address.setWard(resultSet.getString("ward"));
                address.setPhone(resultSet.getString("phone"));

                addressList.add(address);
            }

            return addressList;
        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return addressList;
    }

    public boolean deleteAddress(Integer idAddress, String deletedBy) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "UPDATE address SET deleted_at = ?, deleted_by = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(2, deletedBy);
            preparedStatement.setInt(3, idAddress);

            int result = preparedStatement.executeUpdate();

            if(result > 0) {
                connection.commit();
                return true;
            }

            connection.rollback();
            return false;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }
}
