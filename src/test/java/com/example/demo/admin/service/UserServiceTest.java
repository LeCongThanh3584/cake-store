package com.example.demo.admin.service;

import com.example.demo.admin.dao.UserDao;

import com.example.demo.admin.dto.UserDto;
import com.example.demo.admin.service.impl.MailServiceImpl;
import com.example.demo.admin.service.impl.UserServiceImpl;
import com.example.demo.entity.Address;
import com.example.demo.entity.Store;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.PasswordUtil;

import com.example.demo.util.ThirdApiConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private StoreDao storeDao;
    @Mock
    private AddressDao addressDao;
    @Mock
    private MailServiceImpl mailService;
    @InjectMocks
    private UserServiceImpl userService;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUserPaginationSearch() {
        Integer pageNumber = 1;
        Integer pageSize = 6;
        String keyword = "abc";

        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);

        List<User> expectedUsers = Arrays.asList(user1, user2);

        Mockito.when(userDao.getAllUserPaginationSearch(pageNumber, pageSize, keyword)).thenReturn(expectedUsers);

        List<User> actualUser = userService.getAllUserPaginationSearch(pageNumber, pageSize, keyword);

        assertEquals(expectedUsers, actualUser);
        Mockito.verify(userDao, times(1)).getAllUserPaginationSearch(pageNumber, pageSize, keyword);
    }

    @Test
    public void testGetUserById() {
        Integer userId = 7;

        UserDto expectedUserDto = Mockito.mock(UserDto.class);
        Mockito.when(userDao.getUserById(userId)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userService.getUserById(userId);

        assertEquals(expectedUserDto, actualUserDto);
        Mockito.verify(userDao, times(1)).getUserById(userId);
    }

    @Test
    public void testAddNewUser_UsernameExists() {
        User newUser = Mockito.mock(User.class);
        newUser.setUsername("existinguser");
        Address newAddress = Mockito.mock(Address.class);

        Mockito.when(userDao.getUserByUsername(newUser.getUsername())).thenReturn(new User());

        boolean result = userService.addNewUser(newUser, newAddress);

        // Assert
        assertFalse(result);
        Mockito.verify(userDao, times(1)).getUserByUsername(newUser.getUsername());
        Mockito.verify(storeDao, never()).getStoreById(anyInt());
        Mockito.verify(userDao, never()).addNewUser(any(User.class));
        Mockito.verify(addressDao, never()).addNewAddress(any());
    }

    @Test
    public void testAddNewUser_StoreNotFound() {
        User newUser = Mockito.mock(User.class);
        newUser.setUsername("newuser");
        newUser.setIdStore(123);
        Address newAddress = Mockito.mock(Address.class);

        when(userDao.getUserByUsername(newUser.getUsername())).thenReturn(null);
        when(storeDao.getStoreById(newUser.getIdStore())).thenReturn(null);

        // Act
        boolean result = userService.addNewUser(newUser, newAddress);

        // Assert
        assertFalse(result);
        verify(userDao, times(1)).getUserByUsername(newUser.getUsername());
        verify(storeDao, times(1)).getStoreById(newUser.getIdStore());
        verify(userDao, never()).addNewUser(any());
        verify(addressDao, never()).addNewAddress(any());

    }

    @Test
    public void testAddNewUserSuccess() {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setIdStore(123);

        Address newAddress = new Address();
        newAddress.setName("New Address");

        when(userDao.getUserByUsername(newUser.getUsername())).thenReturn(null).thenReturn(new User());
        when(storeDao.getStoreById(newUser.getIdStore())).thenReturn(new Store());
        when(userDao.addNewUser(newUser)).thenReturn(true);

        boolean result = userService.addNewUser(newUser, newAddress);

        assertTrue(result);

        verify(userDao, times(2)).getUserByUsername(newUser.getUsername());
        verify(storeDao, times(1)).getStoreById(newUser.getIdStore());
        verify(userDao, times(1)).addNewUser(newUser);
        verify(addressDao, times(1)).addNewAddress(newAddress);

    }

    @Test
    public void testGetTotalPages() {
        String keyword = "abc";
        Integer pageSize = 5;
        Integer quantityUser = 30;

        Mockito.when(userDao.countUser(keyword)).thenReturn(quantityUser);
        Integer totalPage = userService.getTotalPages(keyword, pageSize);

        assertEquals(Integer.valueOf(6), totalPage);
        Mockito.verify(userDao,times(1)).countUser(keyword);
    }

    @Test
    public void testGetAllUsers(){
        User user = Mockito.mock(User.class);
        User user1 = Mockito.mock(User.class);
        List<User> userList = Arrays.asList(user1,user);
        Mockito.when(userDao.getAllUsers()).thenReturn(userList);
        List<User> result = userService.getAllUsers();
        Assert.assertEquals(userList, result);
        Mockito.verify(userDao, Mockito.times(1)).getAllUsers();
    }

    @Test
    public void testGetUserByUsername(){
        String username = "eklgaklegg";
        User user = Mockito.mock(User.class);
        Mockito.when(userDao.getUserByUsername(username)).thenReturn(user);

        User result = userService.getUserByUsername(username);
        Assert.assertEquals(user,result);
        Mockito.verify(userDao,Mockito.times(1)).getUserByUsername(username);
    }

    @Test
    public void testGetByUsername(){
        String username = "eklgaklegg";
        UserDto user = Mockito.mock(UserDto.class);
        Mockito.when(userDao.getByUsername(username)).thenReturn(user);

        UserDto result = userService.getByUsername(username);
        Assert.assertEquals(user,result);
        Mockito.verify(userDao,Mockito.times(1)).getByUsername(username);
    }

    @Test
    public void testUpdatePassword(){
        String username = "eklgaklegg";
        String password = "935583926irojwe";
        Mockito.when(userDao.resetPassword(password, username)).thenReturn(true);

        boolean result = userService.updatePassword(username, password);

        Assert.assertTrue(result);
        Mockito.verify(userDao).resetPassword(password, username);
    }

    @Test
    public void testLoginAdmin_SuccessfulLogin() {
        User user = new User();
        String hashedPassword = PasswordUtil.hashPassword("password");
        user.setUsername("admin");
        user.setPassword(hashedPassword);
        user.setRole(Constant.ROLE.ADMIN);
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(user);

        boolean result = userService.login("admin", "password");

        Assert.assertTrue(result);
    }

    @Test
    public void testLoginSuperAdmin_SuccessfulLogin() {
        User user = new User();
        String hashedPassword = PasswordUtil.hashPassword("password");
        user.setUsername("admin");
        user.setPassword(hashedPassword);
        user.setRole(Constant.ROLE.SUPER_ADMIN);
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(user);

        boolean result = userService.login("admin", "password");

        Assert.assertTrue(result);
    }

    @Test
    public void testLogin_FailedNotUser() {
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(null);

        boolean result = userService.login("admin", "password");

        Assert.assertFalse(result);
    }

    @Test
    public void testLogin_FailedPasswordWrong() {
        User user = Mockito.mock(User.class);
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(user);

        boolean result = userService.login("admin", "password");

        Assert.assertFalse(result);
    }

    @Test
    public void testLogin_FailedRole() {
        User user = new User();
        String hashedPassword = PasswordUtil.hashPassword("password");
        user.setUsername("admin");
        user.setPassword(hashedPassword);
        user.setRole(Constant.ROLE.USER);
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(user);

        boolean result = userService.login("admin", "password");

        Assert.assertFalse(result);
    }

    @Test
    public void testExistUsername_AdminSuccessfull(){
        User user = new User();
        user.setRole(Constant.ROLE.ADMIN);
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(user);
        boolean result = userService.existByUsername("admin");
        Assert.assertTrue(result);
    }

    @Test
    public void testExistUsername_SuperAdminSuccessfull(){
        User user = Mockito.mock(User.class);
        user.setRole(Constant.ROLE.SUPER_ADMIN);
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(user);
        boolean result = userService.existByUsername("admin");
        Assert.assertTrue(result);
    }

    @Test
    public void testExistUsername_FailedNotUser(){
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(null);
        boolean result = userService.existByUsername("admin");
        Assert.assertFalse(result);
    }

    @Test
    public void testExistUsername_FailedNotRole(){
        User user = new User();
        user.setRole(Constant.ROLE.USER);
        Mockito.when(userDao.getUserByUsername("admin")).thenReturn(user);
        boolean result = userService.existByUsername("admin");
        Assert.assertFalse(result);
    }

    @Test
    public void testSendOTPRestPassword() throws Exception {
        String email = "minhquy3107@gmail.com";


        userService.sendOTPRestPassword(email);

        Mockito.verify(mailService, Mockito.times(1)).sendEmail(Mockito.eq(email), Mockito.anyString());
    }

    @Test
    public void testChangePassword_Success() {
        String token = "token123";
        String email = "test@example.com";
        String newPassword = "newPassword";

        Jedis jedis = ThirdApiConfig.getConnection();
        jedis.setex(token,Constant.OTP_TTL,email);

        boolean result = userService.changePassword(newPassword, token);

        Assert.assertTrue(result);

        Mockito.verify(userDao, Mockito.times(1)).resetPassword(Mockito.anyString(), Mockito.eq(email));
    }

     @Test
    public void testChangePassword_Failed() {
        String token = "token123";
        String newPassword = "newPassword";

        boolean result = userService.changePassword(newPassword, token);
        Assert.assertFalse(result);

    }
}
