package com.example.demo.client.servlet;

import com.example.demo.client.service.AddressService;
import com.example.demo.client.service.UserService;
import com.example.demo.client.service.impl.AddressServiceImpl;
import com.example.demo.client.service.impl.UserServiceImpl;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.util.Constant;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddressServlet", value = {"/address/view", "/address/update",
        "/address/create", "/address/delete"})
public class AddressServlet extends HttpServlet {

    private UserService userService;

    private AddressService addressService;

    public AddressServlet(AddressService addressService, UserService userService) {
        if (addressService != null) {
            this.addressService = addressService;
        } else {
            addressService = new AddressServiceImpl();
        }

        if (userService != null) {
            this.userService = userService;
        } else {
            userService = new UserServiceImpl();
        }
    }

    public AddressServlet() {
        this.addressService = new AddressServiceImpl();
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/address/delete":
                this.delete(req, resp);
                break;
            case "/address/update":
                this.update(req, resp);
                break;
            case "/address/create":
                this.create(req, resp);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        switch (uri) {
            case "/address/view":
                this.view(req, resp);
                break;
            case "/address/update":
                this.formUpdate(req, resp);
                break;
            case "/address/create":
                this.formCreate(req, resp);
                break;
        }
    }

    public void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.getUserByUsername(username);

        String search = request.getParameter("search");

        int page = 1;
        if (!StringUtil.stringIsNullOrEmty(request.getParameter("page")))
            page = Integer.parseInt(request.getParameter("page"));
        int totalRecord = addressService.getTotalRecord(user.getId(), search == null ? "" : search);

        int totalPage = (int) Math.ceil(totalRecord == 0 ? 1 : totalRecord * 1.0 / 10);
        List<Address> listAddress = addressService.
                getAddressByUserId((page - 1) * 10, 10, user.getId(), search == null ? "" : search);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("search", search);
        request.setAttribute("listAddress", listAddress);
        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.ADDRESS_FOLDER_FILE + "/view-address.jsp").forward(request, response);
    }

    public void formUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Address address = addressService.getAddressById(id);
        List<Map<String, Object>> provinces = this.getProvinces();
        Map<String, Object> pro = provinces.stream().filter(s -> s.get("full_name").equals(address.getProvince()))
                .findFirst().orElse(null);

        List<Map<String, Object>> districts = this.getDistricts((String) pro.get("id"));
        Map<String, Object> dis = districts.stream().filter(s -> s.get("full_name").equals(address.getDistrict()))
                .findFirst().orElse(null);
        List<Map<String, Object>> wards = this.getWards((String) dis.get("id"));

        request.setAttribute("districts", districts);
        request.setAttribute("provinces", provinces);
        request.setAttribute("wards", wards);
        request.setAttribute("address", address);
        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.ADDRESS_FOLDER_FILE + "/update-address.jsp").forward(request, response);
    }

    public List<Map<String, Object>> getProvinces() {
        List<Map<String, Object>> province = (List<Map<String, Object>>) addressService.getProvince().get("data");
        return province;
    }

    public List<Map<String, Object>> getDistricts(String idProvince) {
        List<Map<String, Object>> province = (List<Map<String, Object>>) addressService.getDistrict(idProvince).get("data");
        return province;
    }

    public List<Map<String, Object>> getWards(String idDistrict) {
        List<Map<String, Object>> province = (List<Map<String, Object>>) addressService.getWards(idDistrict).get("data");
        return province;
    }

    public void formCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.ADDRESS_FOLDER_FILE + "/create-address.jsp").forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = addressService.validateCreate(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            this.formUpdate(request, response);
            return;
        }

        String username = request.getSession().getAttribute("username").toString();
        User user = userService.getUserByUsername(username);
        Address address = new Address();
        address.setIdUser(user.getId());
        address.setId(Integer.valueOf(request.getParameter("id")));
        address.setName(request.getParameter("name"));
        address.setPhone(request.getParameter("phone"));
        address.setProvince(request.getParameter("province"));
        address.setWard(request.getParameter("ward"));
        address.setDistrict(request.getParameter("district"));
        address.setUpdatedBy(user.getUsername());
        boolean result = addressService.updateAddress(address);
        if (result) {
            request.getSession().setAttribute("response", "update address successfully!");
        } else {
            request.getSession().setAttribute("response", "update address failed!");
        }
        response.sendRedirect("/address/view");
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = addressService.validateCreate(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(Constant.PATH_CLIENT + Constant.ADDRESS_FOLDER_FILE + "/create-address.jsp").forward(request, response);
            return;
        }

        String username = request.getSession().getAttribute("username").toString();
        User user = userService.getUserByUsername(username);
        Address address = new Address();
        address.setIdUser(user.getId());
        address.setName(request.getParameter("name"));
        address.setPhone(request.getParameter("phone"));
        address.setProvince(request.getParameter("province"));
        address.setWard(request.getParameter("ward"));
        address.setDistrict(request.getParameter("district"));
        address.setCreatedBy(user.getUsername());
        boolean result = addressService.createAddress(address);
        if (result) {
            request.getSession().setAttribute("response", "create new address successfully!");
        } else {
            request.getSession().setAttribute("response", "create new address failed!");
        }
        response.sendRedirect("/address/view");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
