package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.UserDto;
import com.example.demo.admin.service.AddressService;
import com.example.demo.admin.service.UserService;
import com.example.demo.admin.service.impl.AddressServiceImpl;
import com.example.demo.admin.service.impl.UserServiceImpl;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.cloudinary.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "profileServlet", value = {"/admin/profile","/admin/change-password"})
public class ProfileServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    private AddressService addressService = new AddressServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/admin/profile":
                this.getProfile(request,response);
                break;

        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri){
            case "/admin/change-password":
                this.changePassword(request,response);
                break;
        }
    }

    private void getProfile(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        UserDto user = userService.getByUsername(username);
        List<Address> list = addressService.getListAddressByUserId(user.getIdUser());
        request.setAttribute("profile",user);
        request.setAttribute("listAddress",list);
        request.getRequestDispatcher(Constant.PATH_ADMIN+"/profile/profile.jsp")
                .forward(request, response);
    }

    public void changePassword(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();

        try {
            JSONObject jsonRequest = readJsonRequest(request);

            String currentPassword = jsonRequest.getString("currentPassword");
            String newPassword = jsonRequest.getString("newPassword");
            String renewPassword = jsonRequest.getString("renewPassword");

            validatePasswords(newPassword, renewPassword, jsonResponse);

            String username = (String) request.getSession().getAttribute("username");
            User user = userService.getUserByUsername(username);

            validateCurrentPassword(currentPassword, user.getPassword(), jsonResponse);

            if (jsonResponse.has("status")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                processPasswordChange(username, newPassword, jsonResponse);
            }
        } catch (Exception e) {
            handleException(jsonResponse, e, response);
        }

        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
        //}
    }

    private JSONObject readJsonRequest(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        }
    }

    private void validatePasswords(String newPassword, String renewPassword, JSONObject jsonResponse) {
        if (!newPassword.equals(renewPassword)) {
            jsonResponse.put("status", "error0");
            jsonResponse.put("message", "New password and confirm password do not match.");
        }
    }

    private void validateCurrentPassword(String currentPassword, String hashedPassword, JSONObject jsonResponse) {
        if (!PasswordUtil.verifyPassword(currentPassword, hashedPassword)) {
            jsonResponse.put("status", "error1");
            jsonResponse.put("message", "Current password is incorrect.");
        }
    }

    private void processPasswordChange(String username, String newPassword, JSONObject jsonResponse) {
        boolean isChanged = userService.updatePassword(username, PasswordUtil.hashPassword(newPassword));
        if (isChanged) {
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Password changed successfully.");
        } else {
            jsonResponse.put("status", "error2");
            jsonResponse.put("message", "Failed to change password. Please try again.");
        }
    }

    private void handleException(JSONObject jsonResponse, Exception e, HttpServletResponse response) {
        jsonResponse.put("status", "error2");
        jsonResponse.put("message", "An error occurred while processing your request.");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

}