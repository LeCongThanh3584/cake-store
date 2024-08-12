package com.example.demo.client.service.impl;


import com.example.demo.client.dao.UserDao;
import com.example.demo.client.service.MailService;
import com.example.demo.client.service.UserService;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.PasswordUtil;
import com.example.demo.util.StringUtil;
import com.example.demo.util.ThirdApiConfig;
import jakarta.servlet.http.HttpServletRequest;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDao();

    private MailService mailService = new MailServiceImpl();

    @Override
    public boolean login(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (!StringUtil.stringIsNullOrEmty(user)) {
            if (PasswordUtil.verifyPassword(password, user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean register(User user) {
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        return userDao.createUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public void sendOTPRestPassword(String email) {
        try (Jedis jedis = ThirdApiConfig.getConnection()) {
            String otp = StringUtil.generateString(Constant.OTP_LENGTH);
            jedis.setex(Constant.OTP + email, Constant.OTP_TTL, otp);
            mailService.sendOtpMail(email, otp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean changePassword(String password, String email, String otp) {
        String key = Constant.OTP + email;
        try (Jedis jedis = ThirdApiConfig.getConnection()) {
            if (jedis.get(key).equals(otp)) {
                userDao.resetPassword(PasswordUtil.hashPassword(password), email);
                jedis.del(key);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<String, String> validateRegister(HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        User user = getUserByUsername(email);
        if (user != null) {
            errors.put("email", "email đã được sử dụng");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "email is required");
        }

        String name = request.getParameter("fullName");
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "fullName is required");
        }

        if (!password1.matches("[a-zA-Z0-9]{8}]")) {
            errors.put("password1", "Password must be at least 8 characters and must not contain spaces.");
            errors.put("password2", "Password must be at least 8 characters and must not contain spaces.");
        }

        if (!password1.equals(password2)) {
            errors.put("password1", "mật khẩu không trùng khớp");
            errors.put("password2", "mật khẩu không trùng khớp");
        }

        if (password1 == null || password1.trim().isEmpty()) {
            errors.put("password1", "password is required");
        }


        if (password2 == null || password2.trim().isEmpty()) {
            errors.put("password2", "password is required");
        }

        return errors;
    }

    @Override
    public Map<String, String> validateChangePassword(HttpServletRequest request, String username) {
        Map<String, String> errors = new HashMap<>();

        User user = getUserByUsername(username);


        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String currentPassword = request.getParameter("currentPassword");
        if (!password1.equals(password2)) {
            errors.put("password1", "mật khẩu không trùng khớp");
            errors.put("password2", "mật khẩu không trùng khớp");
        }

        if (password1 == null || password1.trim().isEmpty()) {
            errors.put("password1", "password is required");
        }

        if (password2 == null || password2.trim().isEmpty()) {
            errors.put("password2", "password is required");
        }

        if (StringUtil.stringIsNullOrEmty(currentPassword)) {
            errors.put("currentPassword", "currentPassword is required");
        }

        if (!PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
            errors.put("currentPassword", "mật khẩu không hiện tại không chinh xác");
        }

        return errors;
    }

    @Override
    public boolean updatePassword(String email, String password) {
        return userDao.resetPassword(password, email);
    }
}
