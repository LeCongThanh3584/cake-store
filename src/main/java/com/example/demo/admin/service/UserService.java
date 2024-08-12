package com.example.demo.admin.service;

import com.example.demo.admin.dto.UserDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<User> getAllUserPaginationSearch(Integer pageNumber, Integer pageSize, String keyword);
    UserDto getUserById(Integer userId);
    boolean addNewUser(User newUser, Address newAddress);
    boolean updateUser(User userUpdate);
    boolean deleteUser(Integer userId, String deletedBy);
    Integer getTotalPages(String keyword, Integer pageSize);

    boolean login(String username, String password);

    User getUserByUsername(String username);

    boolean existByUsername(String username);

    void sendOTPRestPassword(String email);

    boolean changePassword(String password, String token);

    UserDto getByUsername(String username);

    boolean updatePassword(String username, String password);
}
