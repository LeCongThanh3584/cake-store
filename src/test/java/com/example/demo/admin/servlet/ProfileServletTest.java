package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.UserDto;
import com.example.demo.admin.service.AddressService;
import com.example.demo.admin.service.UserService;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.PasswordUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.cloudinary.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileServletTest {

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
    private AddressService addressService;

    @InjectMocks
    private ProfileServlet profileServlet;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("username")).thenReturn("admin");
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        Mockito.when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetProfile() throws ServletException, IOException {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/profile");
        UserDto user = Mockito.mock(UserDto.class);
        Mockito.when(userService.getByUsername("admin")).thenReturn(user);
        Mockito.when(addressService.getListAddressByUserId(user.getIdUser())).thenReturn(Collections.emptyList());

        profileServlet.doGet(request,response);

        Mockito.verify(request).setAttribute("profile",user);
        Mockito.verify(request).setAttribute("listAddress",Collections.emptyList());
        Mockito.verify(request).getRequestDispatcher(Constant.PATH_ADMIN+"/profile/profile.jsp");
        Mockito.verify(requestDispatcher).forward(request,response);
    }

    @Test
    public void testChangePassword_Success() throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/change-password");

        String jsonRequest = "{\"currentPassword\":\"currentPassword\",\"newPassword\":\"newPassword\",\"renewPassword\":\"newPassword\"}";
        JSONObject jsonObjectMock = Mockito.mock(JSONObject.class);
        Mockito.when(jsonObjectMock.put(Mockito.anyString(), Mockito.anyString())).thenReturn(jsonObjectMock);
        Mockito.when(jsonObjectMock.toString())
                .thenReturn(jsonRequest);

        BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReaderMock.readLine()).thenReturn(jsonRequest).thenReturn(null);
        Mockito.when(request.getReader()).thenReturn(bufferedReaderMock);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);


        User user = Mockito.mock(User.class);
        String hashPassword = PasswordUtil.hashPassword("currentPassword");
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(user);
        Mockito.when(user.getPassword()).thenReturn(hashPassword);
        assertTrue(PasswordUtil.verifyPassword("currentPassword", user.getPassword()));


        Mockito.when(userService.updatePassword(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        profileServlet.doPost(request, response);

        writer.flush();
        String jsonResponse = stringWriter.toString();
        JSONObject json = new JSONObject(jsonResponse);

        assertEquals("success", json.getString("status"));
        assertEquals("Password changed successfully.", json.getString("message"));
    }

    @Test
    public void testChangePassword_NotMatch() throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/change-password");

        String jsonRequest = "{\"currentPassword\":\"currentPassword\",\"newPassword\":\"newPassword\",\"renewPassword\":\"wrongNewPass\"}";
        JSONObject jsonObjectMock = Mockito.mock(JSONObject.class);
        Mockito.when(jsonObjectMock.put(Mockito.anyString(), Mockito.anyString())).thenReturn(jsonObjectMock);
        Mockito.when(jsonObjectMock.toString())
                .thenReturn(jsonRequest);

        BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReaderMock.readLine()).thenReturn(jsonRequest).thenReturn(null);
        Mockito.when(request.getReader()).thenReturn(bufferedReaderMock);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);

        User user = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(user);
        String hashPassword = PasswordUtil.hashPassword("currentPassword");
        Mockito.when(user.getPassword()).thenReturn(hashPassword);
        assertTrue(PasswordUtil.verifyPassword("currentPassword", user.getPassword()));

        profileServlet.doPost(request, response);

        writer.flush();
        String jsonResponse = stringWriter.toString();
        JSONObject json = new JSONObject(jsonResponse);

        assertEquals("error0", json.getString("status"));
        assertEquals("New password and confirm password do not match.", json.getString("message"));
    }

    @Test
    public void testChangePassword_NotValidateCurrent() throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/change-password");

        String jsonRequest = "{\"currentPassword\":\"currentPassword\",\"newPassword\":\"newPassword\",\"renewPassword\":\"newPassword\"}";
        JSONObject jsonObjectMock = Mockito.mock(JSONObject.class);
        Mockito.when(jsonObjectMock.put(Mockito.anyString(), Mockito.anyString())).thenReturn(jsonObjectMock);
        Mockito.when(jsonObjectMock.toString())
                .thenReturn(jsonRequest);

        BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReaderMock.readLine()).thenReturn(jsonRequest).thenReturn(null);
        Mockito.when(request.getReader()).thenReturn(bufferedReaderMock);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);

        User user = Mockito.mock(User.class);
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(user);

        profileServlet.doPost(request, response);

        writer.flush();
        String jsonResponse = stringWriter.toString();
        JSONObject json = new JSONObject(jsonResponse);

        assertEquals("error1", json.getString("status"));
        assertEquals("Current password is incorrect.", json.getString("message"));
    }

    @Test
    public void testChangePassword_Fail() throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/change-password");

        String jsonRequest = "{\"currentPassword\":\"currentPassword\",\"newPassword\":\"newPassword\",\"renewPassword\":\"newPassword\"}";
        JSONObject jsonObjectMock = Mockito.mock(JSONObject.class);
        Mockito.when(jsonObjectMock.put(Mockito.anyString(), Mockito.anyString())).thenReturn(jsonObjectMock);
        Mockito.when(jsonObjectMock.toString())
                .thenReturn(jsonRequest);

        BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReaderMock.readLine()).thenReturn(jsonRequest).thenReturn(null);
        Mockito.when(request.getReader()).thenReturn(bufferedReaderMock);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);

        profileServlet.doPost(request, response);

        writer.flush();
        String jsonResponse = stringWriter.toString();
        JSONObject json = new JSONObject(jsonResponse);

        assertEquals("error2", json.getString("status"));
        assertEquals("An error occurred while processing your request.", json.getString("message"));
    }

    @Test
    public void testChangePassword_Fail1() throws Exception {
        Mockito.when(request.getRequestURI()).thenReturn("/admin/change-password");

        String jsonRequest = "{\"currentPassword\":\"currentPassword\",\"newPassword\":\"newPassword\",\"renewPassword\":\"newPassword\"}";
        JSONObject jsonObjectMock = Mockito.mock(JSONObject.class);
        Mockito.when(jsonObjectMock.put(Mockito.anyString(), Mockito.anyString())).thenReturn(jsonObjectMock);
        Mockito.when(jsonObjectMock.toString())
                .thenReturn(jsonRequest);

        BufferedReader bufferedReaderMock = Mockito.mock(BufferedReader.class);
        Mockito.when(bufferedReaderMock.readLine()).thenReturn(jsonRequest).thenReturn(null);
        Mockito.when(request.getReader()).thenReturn(bufferedReaderMock);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        Mockito.when(response.getWriter()).thenReturn(writer);

        User user = Mockito.mock(User.class);
        String hashPassword = PasswordUtil.hashPassword("currentPassword");
        Mockito.when(userService.getUserByUsername("admin")).thenReturn(user);
        Mockito.when(user.getPassword()).thenReturn(hashPassword);
        assertTrue(PasswordUtil.verifyPassword("currentPassword", user.getPassword()));


        Mockito.when(userService.updatePassword(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        profileServlet.doPost(request, response);

        writer.flush();
        String jsonResponse = stringWriter.toString();
        JSONObject json = new JSONObject(jsonResponse);

        assertEquals("error2", json.getString("status"));
        assertEquals("Failed to change password. Please try again.", json.getString("message"));
    }

}

