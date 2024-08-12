package com.example.demo.client.service;

import com.example.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface UserService {

    boolean login(String username, String password);

    boolean register(User user);

    User getUserByUsername(String username);

    void sendOTPRestPassword(String email);

    boolean changePassword(String password, String email, String otp);

    Map<String, String> validateRegister(HttpServletRequest request);

    Map<String, String> validateChangePassword(HttpServletRequest request, String username);

    boolean updatePassword(String email, String password);

}
