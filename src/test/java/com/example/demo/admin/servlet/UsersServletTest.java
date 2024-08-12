package com.example.demo.admin.servlet;

import com.example.demo.admin.service.StoreService;
import com.example.demo.admin.service.UserService;
import com.example.demo.admin.servlet.UsersServlet;
import com.example.demo.client.servlet.UserServlet;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UsersServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private UserService userService;
    @Mock
    private StoreService storeService;
    @InjectMocks
    private UsersServlet usersServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Mockito.when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("username")).thenReturn("thanh.lc");

    }

    @Test
    public void viewGetUsers() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/users");

        usersServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/user/view-users.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void viewUSerCreate() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/admin/add-new-user");

        usersServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/user/create-user.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void viewUserUpdate() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-user");
        Mockito.when(request.getParameter("id")).thenReturn("1");

        usersServlet.doGet(request, response);

        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN + "/user/update-user.jsp");
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void CreateUserSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-user");

        Mockito.when(request.getParameter("storeId")).thenReturn("3");
        String createdBy = (String) request.getSession().getAttribute("username");

        Mockito.when(request.getParameter("password")).thenReturn("12345678");

        User newUser = Mockito.mock(User.class);

        Mockito.when(request.getParameter("role")).thenReturn("1");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("userName")).thenReturn("test@gmail.com");
        Mockito.when(request.getParameter("fullName")).thenReturn("nguoidung1");

        newUser.setUsername("test@gmail.com"); newUser.setPassword("12345678"); newUser.setFullName("nguoidung1");
        newUser.setRole(1); newUser.setStatus(1); newUser.setIdStore(3); newUser.setCreatedBy(createdBy);

        Address newAddress = Mockito.mock(Address.class);
        Mockito.when(request.getParameter("addressType")).thenReturn("Home");
        Mockito.when(request.getParameter("province")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("district")).thenReturn("HaiBaTrung");
        Mockito.when(request.getParameter("ward")).thenReturn("MinhKhai");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");

        newAddress.setName("Home"); newAddress.setProvince("HaNoi"); newAddress.setDistrict("HaiBaTrung");
        newAddress.setWard("MinhKhai"); newAddress.setPhone("0123456789"); newAddress.setCreatedBy(createdBy);

        when(userService.addNewUser(any(User.class), any(Address.class))).thenReturn(true);

        usersServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Add new user successfully!");
        Mockito.verify(response).sendRedirect("/admin/users");

    }

    @Test
    public void CreateUserFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-user");

        Mockito.when(request.getParameter("storeId")).thenReturn("3");
        String createdBy = (String) request.getSession().getAttribute("username");

        Mockito.when(request.getParameter("password")).thenReturn("12345678");

        User newUser = new User();

        Mockito.when(request.getParameter("role")).thenReturn("1");
        Mockito.when(request.getParameter("status")).thenReturn("1");
        Mockito.when(request.getParameter("userName")).thenReturn("test@gmail.com");
        Mockito.when(request.getParameter("fullName")).thenReturn("nguoidung1");

        newUser.setUsername("test@gmail.com"); newUser.setPassword("12345678"); newUser.setFullName("nguoidung1");
        newUser.setRole(1); newUser.setStatus(1); newUser.setIdStore(3); newUser.setCreatedBy(createdBy);

        Address newAddress = new Address();
        Mockito.when(request.getParameter("addressType")).thenReturn("Home");
        Mockito.when(request.getParameter("province")).thenReturn("HaNoi");
        Mockito.when(request.getParameter("district")).thenReturn("HaiBaTrung");
        Mockito.when(request.getParameter("ward")).thenReturn("MinhKhai");
        Mockito.when(request.getParameter("phoneNumber")).thenReturn("0123456789");

        newAddress.setName("Home"); newAddress.setProvince("HaNoi"); newAddress.setDistrict("HaiBaTrung");
        newAddress.setWard("MinhKhai"); newAddress.setPhone("0123456789"); newAddress.setCreatedBy(createdBy);

        Mockito.when(userService.addNewUser(any(User.class), any(Address.class))).thenReturn(false);

        usersServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Add new user failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/add-new-user");

    }

    @Test
    public void CreateUserWithPassLessThan8() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/add-new-user");

        Mockito.when(request.getParameter("password")).thenReturn("1234567");

        usersServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Password must have a minimum of 8 characters!");
        Mockito.verify(response).sendRedirect("/admin/add-new-user");

    }

    @Test
    public void UpdateUserSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-user");

        Mockito.when(request.getParameter("storeId")).thenReturn("3");
        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("userName")).thenReturn("test@gmail.com");
        Mockito.when(request.getParameter("fullName")).thenReturn("nguoidung");
        Mockito.when(request.getParameter("password")).thenReturn("12345678");
        Mockito.when(request.getParameter("status")).thenReturn("1");

        User userUpdate = new User();
        userUpdate.setId(Integer.valueOf("1"));
        userUpdate.setUsername("test@gmail.com");
        userUpdate.setFullName("nguoidung");
        userUpdate.setPassword("12345678");
        userUpdate.setStatus(1);
        userUpdate.setIdStore(3);
        userUpdate.setUpdatedBy((String) request.getSession().getAttribute("username"));

        Mockito.when(userService.updateUser(any())).thenReturn(true);

        usersServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Update user with username \"" + userUpdate.getUsername() + "\" successfully!");
        Mockito.verify(response).sendRedirect("/admin/users");
    }

    @Test
    public void UpdateUserFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/update-user");

        Mockito.when(request.getParameter("storeId")).thenReturn("3");
        Mockito.when(request.getParameter("id")).thenReturn("99");
        Mockito.when(request.getParameter("userName")).thenReturn("test@gmail.com");
        Mockito.when(request.getParameter("fullName")).thenReturn("nguoidung");
        Mockito.when(request.getParameter("password")).thenReturn("12345678");
        Mockito.when(request.getParameter("status")).thenReturn("1");

        User userUpdate = new User();
        userUpdate.setId(Integer.valueOf("1"));
        userUpdate.setUsername("test@gmail.com");
        userUpdate.setFullName("nguoidung");
        userUpdate.setPassword("12345678");
        userUpdate.setStatus(1);
        userUpdate.setIdStore(3);
        userUpdate.setUpdatedBy((String) request.getSession().getAttribute("username"));

        Mockito.when(userService.updateUser(any(User.class))).thenReturn(false);

        usersServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Update user with username \"" + userUpdate.getUsername() + "\" failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/update-user");
    }

    @Test
    public void DeleteUserSuccess() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-user");
        String userId = "5";
        Mockito.when(request.getParameter("idUserDelete")).thenReturn(userId);
        String createdBy = (String) request.getSession().getAttribute("username");

        Mockito.when(userService.deleteUser(Integer.parseInt(userId), createdBy)).thenReturn(true);
        usersServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Delete user with id " + userId + " successfully!");
        Mockito.verify(response).sendRedirect("/admin/users");

    }

    @Test
    public void DeleteUserFailed() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/delete-user");
        String idUserDelete = "99";
        Mockito.when(request.getParameter("idUserDelete")).thenReturn(idUserDelete);

        String createdBy = (String) request.getSession().getAttribute("username");

        Mockito.when(userService.deleteUser(Integer.parseInt(idUserDelete), createdBy)).thenReturn(false);
        usersServlet.doPost(request, response);

        Mockito.verify(session).setAttribute("messageResponse", "Delete user with id " + idUserDelete + " failed, Please try again!");
        Mockito.verify(response).sendRedirect("/admin/users");
    }
}
