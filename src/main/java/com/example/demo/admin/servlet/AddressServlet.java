package com.example.demo.admin.servlet;

import com.example.demo.admin.service.AddressService;
import com.example.demo.admin.service.impl.AddressServiceImpl;
import com.example.demo.entity.Address;
import com.example.demo.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "addressServlet", value = {"/admin/user/*", "/admin/add-new-address",
        "/admin/update-address" ,"/admin/delete-address"})
public class AddressServlet extends HttpServlet {

    private AddressService addressService;

    @Override
    public void init() throws ServletException {
        addressService = new AddressServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if(pathInfo != null && pathInfo.endsWith("/view-address")) {
            this.getPageListAddressOfUser(request, response, pathInfo);
        } else if(pathInfo != null && pathInfo.endsWith("/add-new-address")){
            this.getPageAddNewAddressOfUser(request, response, pathInfo);
        } else if(pathInfo != null && pathInfo.contains("/update-address")) {
            this.getPageUpdateAddressOfUser(request, response, pathInfo);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/blank.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String url = request.getRequestURI();

       switch (url) {
           case "/admin/add-new-address":
               this.handleAddNewAddress(request, response);
               break;
           case "/admin/update-address":
               this.handleUpdateAddress(request, response);
               break;
           case "/admin/delete-address":
               this.handleDeleteAddress(request, response);
               break;
       }
    }

    public void getPageListAddressOfUser(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws ServletException, IOException {
        String[] part = pathInfo.split("/");
        Integer userId = Integer.valueOf(part[1]);

        List<Address> addressList = addressService.getListAddressByUserId(userId);
        request.setAttribute("listAddress", addressList);
        request.setAttribute("userId", userId);

        request.getRequestDispatcher(Constant.PATH_ADMIN + "/address/view-address.jsp").forward(request, response);
    }

    public void getPageAddNewAddressOfUser(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws ServletException, IOException {
        String[] part = pathInfo.split("/");
        Integer userId = Integer.valueOf(part[1]);

        request.setAttribute("userId", userId);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/address/add-address.jsp").forward(request, response);
    }

    public void getPageUpdateAddressOfUser(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws ServletException, IOException {
        String[] part = pathInfo.split("/");
        Integer userId = Integer.valueOf(part[1]);  //Lấy ra idUser
        Integer addressId = Integer.valueOf(part[part.length - 1]);  //Lấy ra idAddress

        Address addressUpdate = addressService.getAddressByAddressIdAndUserId(addressId, userId);

        List<Map<String, Object>> listProvince = (List<Map<String, Object>>) addressService.getDataFromAPIProvince().get("data");
        Map<String, Object> provinceOfUser = listProvince.stream().
                filter(item -> item.get("full_name").equals(addressUpdate.getProvince())).findFirst().orElse(null);


        List<Map<String, Object>> listDistrict = (List<Map<String, Object>>) addressService.getDataFromAPIDistrict((String) provinceOfUser.get("id")).get("data");
        Map<String, Object> districtOfUser = listDistrict.stream().
                filter(item -> item.get("full_name").equals(addressUpdate.getDistrict())).findFirst().orElse(null);


        List<Map<String, Object>> listWard = (List<Map<String, Object>>) addressService.getDataFromAPIWard((String) districtOfUser.get("id")).get("data");

        request.setAttribute("addressUpdate", addressUpdate);
        request.setAttribute("listProvince", listProvince);
        request.setAttribute("listDistrict", listDistrict);
        request.setAttribute("listWard", listWard);
        request.setAttribute("userId", userId);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/address/update-address.jsp").forward(request, response);
    }

    public void handleAddNewAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Address newAddress = new Address();

        Integer userId = Integer.valueOf(request.getParameter("userId"));
        newAddress.setIdUser(userId);
        newAddress.setName(request.getParameter("addressType"));
        newAddress.setProvince(request.getParameter("province"));
        newAddress.setDistrict(request.getParameter("district"));
        newAddress.setWard(request.getParameter("ward"));
        newAddress.setPhone(request.getParameter("phoneNumber"));
        newAddress.setCreatedBy((String) request.getSession().getAttribute("username"));

        boolean result = addressService.addNewAddress(newAddress);

        if(result) {
            request.getSession().setAttribute("messageResponse", "Add new address successfully!");
            response.sendRedirect("/admin/user/" + userId + "/view-address");
        } else {
            request.getSession().setAttribute("messageResponse", "Add new address failed, Please try again!");
            response.sendRedirect("/admin/user/" + userId + "/add-new-address");
        }

    }

    public void handleDeleteAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idAddress = Integer.valueOf(request.getParameter("idAddressDelete"));
        Integer idUser = Integer.valueOf(request.getParameter("idUser"));
        String deletedBy = (String) request.getSession().getAttribute("username");

        boolean result = addressService.deleteAddress(idAddress, deletedBy);

        if(result) {
            request.getSession().setAttribute("messageResponse", "Delete address with id " + idAddress + " successfully!");
            response.sendRedirect("/admin/user/" + idUser + "/view-address");
        } else {
            request.getSession().setAttribute("messageResponse", "Delete address with id " + idAddress + " failed, Please try again!");
            response.sendRedirect("/admin/user/" + idUser + "/view-address");
        }
    }

    public void handleUpdateAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Address addressUpdate = new Address();

        Integer addressId = Integer.valueOf(request.getParameter("addressId"));
        Integer userId = Integer.valueOf(request.getParameter("userId"));

        addressUpdate.setId(addressId);
        addressUpdate.setName(request.getParameter("addressType"));
        addressUpdate.setProvince(request.getParameter("province"));
        addressUpdate.setDistrict(request.getParameter("district"));
        addressUpdate.setWard(request.getParameter("ward"));
        addressUpdate.setPhone(request.getParameter("phoneNumber"));
        addressUpdate.setUpdatedBy((String) request.getSession().getAttribute("username"));

        boolean result = addressService.updateAddress(addressUpdate);

        if(result) {
            request.getSession().setAttribute("messageResponse", "Update address with id " + addressId + " successfully!");
            response.sendRedirect("/admin/user/" + userId + "/view-address");
        } else {
            request.getSession().setAttribute("messageResponse", "Update address with id " + addressId +" failed, Please try again!");
            response.sendRedirect("/admin/user/" + userId + "/update-address/" + addressId);
        }
    }
}
