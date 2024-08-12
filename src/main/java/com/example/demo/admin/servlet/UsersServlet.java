package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.UserDto;
import com.example.demo.admin.service.StoreService;
import com.example.demo.admin.service.UserService;
import com.example.demo.admin.service.impl.StoreServiceImpl;
import com.example.demo.admin.service.impl.UserServiceImpl;
import com.example.demo.entity.Address;
import com.example.demo.entity.Store;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.PasswordUtil;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.params.HttpProtocolParams;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "usersServlet", value = {"/admin/users", "/admin/add-new-user",
        "/admin/update-user", "/admin/delete-user"})
public class UsersServlet extends HttpServlet {

    private UserService userService;

    private StoreService storeService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
        storeService = new StoreServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String url = request.getRequestURI();
        switch (url) {
            case "/admin/users":
                this.getPageListUser(request, response);
                break;
            case "/admin/add-new-user":
                this.getPageCreateUser(request, response);
                break;
            case "/admin/update-user":
                this.getPageUpdateUser(request, response);
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        switch (url) {
            case "/admin/add-new-user":
                this.createNewUser(request, response);
                break;
            case "/admin/update-user":
                this.updateUser(request, response);
                break;
            case "/admin/delete-user":
                this.deleteUser(request, response);
                break;
        }
    }

    public void getPageListUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer pageSize = 1;
        Integer pageNumber = null;
        String keyword = request.getParameter("keyword");

        if(StringUtil.stringIsNullOrEmty(request.getParameter("page"))) {
            pageNumber = 1;
        } else {
            pageNumber = Integer.valueOf(request.getParameter("page"));
        }

        List<User> userList = userService.getAllUserPaginationSearch(pageNumber, pageSize, keyword);

        Integer totalPage = userService.getTotalPages(keyword, pageSize);

        request.setAttribute("listUsers", userList);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.setAttribute("totalPage", totalPage);

        request.getRequestDispatcher(Constant.PATH_ADMIN + "/user/view-users.jsp").forward(request, response);
    }

    public void getPageCreateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Store> storeList = storeService.getAllStores();

        request.setAttribute("storeList", storeList);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/user/create-user.jsp").forward(request, response);
    }

    public void getPageUpdateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        UserDto userReturn = userService.getUserById(userId);

        List<Store> storeList = storeService.getAllStores();

        request.setAttribute("user", userReturn);
        request.setAttribute("storeList", storeList);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/user/update-user.jsp").forward(request, response);
    }

    public void createNewUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String storeId = request.getParameter("storeId");
        String createdBy = (String) request.getSession().getAttribute("username");

        if(request.getParameter("password").length() < 8) {
            request.getSession().setAttribute("messageResponse", "Password must have a minimum of 8 characters!");
            response.sendRedirect("/admin/add-new-user");
            return;
        }

        User newUser = new User();
        newUser.setUsername(request.getParameter("userName"));
        newUser.setPassword(request.getParameter("password"));
        newUser.setFullName(request.getParameter("fullName"));
        newUser.setRole(Integer.valueOf(request.getParameter("role")));
        newUser.setStatus(Integer.valueOf(request.getParameter("status")));
        newUser.setIdStore(
                StringUtil.stringIsNullOrEmty(storeId) ? null : Integer.valueOf(storeId)
        );
        newUser.setCreatedBy(createdBy);

        Address newAddress = new Address();
        newAddress.setName(request.getParameter("addressType"));
        newAddress.setProvince(request.getParameter("province"));
        newAddress.setDistrict(request.getParameter("district"));
        newAddress.setWard(request.getParameter("ward"));
        newAddress.setPhone(request.getParameter("phoneNumber"));
        newAddress.setCreatedBy(createdBy);

        boolean result = userService.addNewUser(newUser, newAddress);

        System.out.println(result);

        if (result) {
            request.getSession().setAttribute("messageResponse", "Add new user successfully!");
            response.sendRedirect("/admin/users");
        } else {
            request.getSession().setAttribute("messageResponse", "Add new user failed, Please try again!");
            response.sendRedirect("/admin/add-new-user");
        }
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String storeId = request.getParameter("storeId");

        User userUpdate = new User();
        int userId = Integer.parseInt(request.getParameter("id"));
        userUpdate.setId(userId);
        userUpdate.setUsername(request.getParameter("userName"));
        userUpdate.setFullName(request.getParameter("fullName"));
        userUpdate.setPassword(request.getParameter("password"));
        userUpdate.setStatus(Integer.valueOf(request.getParameter("status")));
        userUpdate.setIdStore(
                StringUtil.stringIsNullOrEmty(storeId) ? null : Integer.parseInt(storeId)
        );
        userUpdate.setUpdatedBy((String) request.getSession().getAttribute("username"));

       boolean result = userService.updateUser(userUpdate);

       if(result) {
            request.getSession().setAttribute("messageResponse", "Update user with username \"" + userUpdate.getUsername() + "\" successfully!");
            response.sendRedirect("/admin/users");
       } else {
           request.getSession().setAttribute("messageResponse", "Update user with username \"" + userUpdate.getUsername() + "\" failed, Please try again!");
           response.sendRedirect("/admin/update-user");
       }

    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("idUserDelete"));
        String deletedBy = (String) request.getSession().getAttribute("username");

        boolean result = userService.deleteUser(userId, deletedBy);

        System.out.println(result);

        if(result) {
            request.getSession().setAttribute("messageResponse", "Delete user with id " + userId + " successfully!");
            response.sendRedirect("/admin/users");
        } else {
            request.getSession().setAttribute("messageResponse", "Delete user with id " + userId + " failed, Please try again!");
            response.sendRedirect("/admin/users");
        }
    }
}