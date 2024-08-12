package com.example.demo.admin.dao;

import com.example.demo.admin.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.util.DatabaseUtil;
import com.example.demo.util.PasswordUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM user WHERE deleted_at IS NULL ORDER BY created_at DESC";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setFullName(resultSet.getString("full_name"));
                user.setRole(resultSet.getInt("role"));
                user.setStatus(resultSet.getInt("status"));
                user.setIdStore(resultSet.getInt("id_store"));
                userList.add(user);
            }

            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            }catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return userList;
    }

    public List<User> getAllUserPaginationSearch(Integer pageNumber, Integer pageSize, String keyword) {
        List<User> userList = new ArrayList<>();

        Connection connection= DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM user " +
                "WHERE deleted_at IS NULL " +
                "AND CASE WHEN ? IS NOT NULL THEN (username LIKE ? OR full_name LIKE ?) ELSE 1=1 END " +
                "ORDER BY created_at DESC LIMIT ?, ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setInt(4, (pageNumber - 1) * pageSize);
            preparedStatement.setInt(5, pageSize);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setFullName(resultSet.getString("full_name"));
                user.setStatus(resultSet.getInt("status"));
                user.setRole(resultSet.getInt("role"));

                userList.add(user);
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return userList;
    }

    public UserDto getUserById(int userId) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT * FROM user u " +
                "LEFT JOIN store s ON u.id_store = s.id " +
                "WHERE u.deleted_at IS NULL AND u.id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UserDto userDto = new UserDto();
                userDto.setIdUser(resultSet.getInt("id"));
                userDto.setUsername(resultSet.getString("username"));
                userDto.setFullName(resultSet.getString("full_name"));
                userDto.setPassword(resultSet.getString("password"));
                userDto.setRole(resultSet.getInt("role"));
                userDto.setStatus(resultSet.getInt("status"));
                userDto.setIdStore(resultSet.getInt("id_store"));
                userDto.setCodeStore(resultSet.getString("code"));
                userDto.setStoreName(resultSet.getString("name"));
                userDto.setAddress(resultSet.getString("address"));
                userDto.setPhone(resultSet.getString("phone"));

                return userDto;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public boolean addNewUser(User newUser) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "INSERT INTO user(username, full_name, password, role, status, id_store, created_at, created_by) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, newUser.getUsername());
            preparedStatement.setString(2, newUser.getFullName());
            preparedStatement.setString(3, PasswordUtil.hashPassword(newUser.getPassword()));
            preparedStatement.setInt(4, newUser.getRole());
            preparedStatement.setInt(5, newUser.getStatus());

            if (newUser.getIdStore() == null) {
                preparedStatement.setNull(6, java.sql.Types.INTEGER);  //set giá trị là null
            } else {
                preparedStatement.setInt(6, newUser.getIdStore());
            }

            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, newUser.getCreatedBy());

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

    public boolean updateUser(User userUpdate) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "UPDATE user SET username = ?, full_name = ?, password = ?, " +
                "status = ?, id_store = ?, updated_at = ?, updated_by = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, userUpdate.getUsername());
            preparedStatement.setString(2, userUpdate.getFullName());
            preparedStatement.setString(3, userUpdate.getPassword());
            preparedStatement.setInt(4, userUpdate.getStatus());

            if(userUpdate.getIdStore() == null) {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);  //set giá trị là null
            } else {
                preparedStatement.setInt(5, userUpdate.getIdStore());
            }

            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, userUpdate.getUpdatedBy());
            preparedStatement.setInt(8, userUpdate.getId());

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
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return false;
    }

    public boolean deleteUser(int userId, String deletedBy) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "UPDATE user SET deleted_at = ?, deleted_by = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(2, deletedBy);
            preparedStatement.setInt(3, userId);

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
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return false;
    }

    public Integer countUser(String keyword) {
        Connection connection = DatabaseUtil.getConnection();
        String sqlQuery = "SELECT COUNT(id) AS quantity_user FROM user " +
                "WHERE deleted_at IS NULL " +
                "AND CASE WHEN ? IS NOT NULL THEN (username LIKE ? OR full_name LIKE ?) ELSE 1=1 END";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt("quantity_user");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return 0;

    }

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
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getInt("role"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getInt("status"));
                user.setCreatedBy(rs.getString("created_by"));
                user.setIdStore(rs.getInt("id_store"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDto getByUsername(String username) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT u.*, s.name AS store_name FROM user u LEFT JOIN store s ON u.id_store = s.id " +
                 "WHERE u.username = ? AND u.deleted_at IS NULL";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                UserDto user = new UserDto();
                user.setIdUser(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getInt("role"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getInt("status"));
                user.setIdStore(rs.getInt("id_store"));
                user.setStoreName(rs.getString("store_name"));
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
