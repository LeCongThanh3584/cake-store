package com.example.demo.client.dao;

import com.example.demo.entity.User;
import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserDao {

    public boolean createUser(User user) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO user (username, full_name, password, role, status, created_at, created_by) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setInt(4, user.getRole());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(5, user.getStatus());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, user.getCreatedBy());
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                connection.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserByUsername(String username) {

        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM user WHERE username = ? AND deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setIdStore(rs.getInt("id_store"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getInt("role"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getInt("status"));
                user.setCreatedBy(rs.getString("created_by"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean resetPassword(String password, String username) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "UPDATE user SET password = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                connection.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
