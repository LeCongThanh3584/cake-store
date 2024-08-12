package com.example.demo.admin.service.impl;

import com.example.demo.admin.dao.AddressDao;
import com.example.demo.admin.dao.StoreDao;
import com.example.demo.admin.dao.UserDao;
import com.example.demo.admin.dto.UserDto;
import com.example.demo.admin.service.MailService;
import com.example.demo.admin.service.StoreService;
import com.example.demo.admin.service.UserService;
import com.example.demo.entity.Address;
import com.example.demo.entity.Store;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.PasswordUtil;
import com.example.demo.util.StringUtil;
import com.example.demo.util.ThirdApiConfig;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private AddressDao addressDao;
    private MailService mailService;
    private StoreDao storeDao;

    public UserServiceImpl() {
        userDao = new UserDao();
        addressDao = new AddressDao();
        mailService = new MailServiceImpl();
        storeDao = new StoreDao();
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getAllUserPaginationSearch(Integer pageNumber, Integer pageSize, String keyword) {
        return userDao.getAllUserPaginationSearch(pageNumber, pageSize, keyword);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public boolean addNewUser(User newUser, Address newAddress) {
        User existUser = userDao.getUserByUsername(newUser.getUsername());
        if(existUser != null) {  //userName đã tồn tại
            return false;
        }

        if(newUser.getIdStore() != null) {
            Store existStore = storeDao.getStoreById(newUser.getIdStore());
            if(existStore == null) {  //Store không tồn tại
                return false;
            }
        }

        boolean resultAddNewUser = userDao.addNewUser(newUser); //Thêm mới user

        if(!resultAddNewUser) {  //Thêm mới user thất bại
            return false;
        }

        boolean existAddress = !StringUtil.stringIsNullOrEmty(newAddress.getName()) || !StringUtil.stringIsNullOrEmty(newAddress.getProvince())
                || !StringUtil.stringIsNullOrEmty(newAddress.getDistrict()) || !StringUtil.stringIsNullOrEmty(newAddress.getWard())
                || !StringUtil.stringIsNullOrEmty(newAddress.getPhone());

        if(existAddress) { //Người dùng có nhập địa chỉ
            User userForAddress = userDao.getUserByUsername(newUser.getUsername());  //Lấy ra user rồi lấy id để lưu giá trị địa chỉ
            newAddress.setIdUser(userForAddress.getId());
            addressDao.addNewAddress(newAddress);
        }

        return true;
    }

    @Override
    public boolean updateUser(User userUpdate) {
        UserDto existUser = userDao.getUserById(userUpdate.getId());
        if(existUser == null) {  //User không tồn tại để cập nhật
            return false;
        }

        if(StringUtil.stringIsNullOrEmty(userUpdate.getPassword())) {  //Người dùng không nhập mật khẩu
            userUpdate.setPassword(existUser.getPassword());  //Lấy mật khẩu cũ
        } else {   //Có nhập mật khẩu
            userUpdate.setPassword(PasswordUtil.hashPassword(userUpdate.getPassword()));  //lấy mật khẩu mới
        }

        if(userUpdate.getIdStore() != null) {
            Store existStore = storeDao.getStoreById(userUpdate.getIdStore());
            if(existStore == null) { //Store không tồn tại
                return false;
            }
        }

        return userDao.updateUser(userUpdate);
    }

    @Override
    public boolean deleteUser(Integer userId, String deletedBy) {
        UserDto existUser = userDao.getUserById(userId);
        System.out.println(userId);
        System.out.println(deletedBy);
        if(existUser == null) {  //User không tồn tại để xoá
            return false;
        }

        return userDao.deleteUser(userId, deletedBy);
    }

    @Override
    public Integer getTotalPages(String keyword, Integer pageSize) {
        Integer quantityUser = userDao.countUser(keyword);
        return (int) Math.ceil(quantityUser * 1.0 / pageSize);
    }

    @Override
    public boolean login(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (!StringUtil.stringIsNullOrEmty(user)) {
            if (PasswordUtil.verifyPassword(password, user.getPassword())
                    && (user.getRole().equals(Constant.ROLE.SUPER_ADMIN) || user.getRole().equals(Constant.ROLE.ADMIN))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public boolean existByUsername(String username) {
        User user = userDao.getUserByUsername(username);
        if ((user != null) && (user.getRole().equals(Constant.ROLE.SUPER_ADMIN) || user.getRole().equals(Constant.ROLE.ADMIN))) {
            return true;
        }
        return false;
    }

    @Override
    public void sendOTPRestPassword(String email) {
        try (Jedis jedis = ThirdApiConfig.getConnection()) {
            String token = UUID.randomUUID().toString();
            jedis.setex(token, Constant.OTP_TTL, email);
            mailService.sendEmail(email, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean changePassword(String password, String token) {
         try(Jedis jedis = ThirdApiConfig.getConnection()) {
             String email = jedis.get(token);
             if (email != null && !email.isEmpty()) {
                userDao.resetPassword(PasswordUtil.hashPassword(password), email);
                jedis.del(token);
                return true;
            }
         } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserDto getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public boolean updatePassword(String username, String password) {
        return userDao.resetPassword(password,username);
    }
}
