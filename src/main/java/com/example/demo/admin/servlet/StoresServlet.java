package com.example.demo.admin.servlet;

import com.example.demo.admin.service.StoreService;
import com.example.demo.admin.service.impl.StoreServiceImpl;
import com.example.demo.entity.Store;
import com.example.demo.util.Constant;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "storesServlet", value = {"/admin/stores", "/admin/add-store",
        "/admin/update-store", "/admin/delete-store"})
@MultipartConfig
public class StoresServlet extends HttpServlet {

    private StoreService storeService;

    @Override
    public void init() throws ServletException {
        storeService = new StoreServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String url = request.getRequestURI();
        switch (url) {
            case "/admin/stores":
                this.getPageListStore(request, response);
                break;
            case "/admin/add-store":
                this.getPageAddNewStore(request, response);
                break;
            case "/admin/update-store":
                this.getPageUpdateStore(request, response);
                break;

        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        switch (url) {
            case "/admin/add-store":
                this.handleCreateStore(request, response);
                break;
            case "/admin/update-store":
                this.handleUpdateStore(request, response);
                break;
            case "/admin/delete-store":
                this.handleDeleteStore(request, response);
                break;
        }
    }

    public void getPageListStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer pageNumber = null;
        int pageSize = 1;
        String keyword = request.getParameter("keyword");

        if(StringUtil.stringIsNullOrEmty(request.getParameter("page"))) {
            pageNumber = 1;  //Nếu tham số page không có trên đường link thì mặc định là 1
        } else {
            pageNumber = Integer.valueOf(request.getParameter("page"));  //Nếu có thì nhận giá trị đó
        }

        List<Store> storeList = storeService.getAllStoresPaginationSearch(pageNumber, pageSize, keyword);

        int totalPage = storeService.getTotalPages(keyword, pageSize);

        request.setAttribute("storesList", storeList);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/store/view-stores.jsp").forward(request, response);
    }

    public void getPageAddNewStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/store/add-store.jsp").forward(request, response);
    }

    public void getPageUpdateStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int storeId = Integer.parseInt(request.getParameter("id"));

        Store storeReturn = storeService.getStoreById(storeId);

        request.setAttribute("store", storeReturn);

        request.getRequestDispatcher(Constant.PATH_ADMIN + "/store/update-store.jsp").forward(request, response);
    }

    public void handleCreateStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Store newStore = new Store();
        newStore.setName(request.getParameter("nameStore"));
        newStore.setCode(StringUtil.generateString(Constant.CODE_LENGTH));
        newStore.setAddress(request.getParameter("address"));
        newStore.setStatus(Integer.valueOf(request.getParameter("status")));
        newStore.setPhone(request.getParameter("phoneNumber"));
        newStore.setCreatedBy((String) request.getSession().getAttribute("username"));

        boolean result = storeService.addNewStore(newStore, request.getPart("image"));

        if(result) {
            request.getSession().setAttribute("messageResponse", "Add new store successfully!");
            response.sendRedirect("/admin/stores");
        } else {
            request.getSession().setAttribute("messageResponse", "Add new store failed, please try again!");
            response.sendRedirect("/admin/add-store");
        }

    }

    public void handleUpdateStore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        Store storeUpdate = new Store();
        String idStore = request.getParameter("id");
        storeUpdate.setId(Integer.valueOf(idStore));
        storeUpdate.setName(request.getParameter("name"));
        storeUpdate.setAddress(request.getParameter("address"));
        storeUpdate.setCode(code);
        storeUpdate.setPhone(request.getParameter("phoneNumber"));
        storeUpdate.setStatus(Integer.valueOf(request.getParameter("status")));
        storeUpdate.setUpdatedBy((String) request.getSession().getAttribute("username"));
        boolean result = storeService.updateStore(storeUpdate, request.getPart("image"));

        if(result) {
            request.getSession().setAttribute("messageResponse", "Update store with code " + code + " successfully!");
            response.sendRedirect("/admin/stores");
        } else {
            request.getSession().setAttribute("messageResponse", "Update store with code " + code + " failed, Please try again!");
            response.sendRedirect("/admin/update-store?id=" + idStore);
        }

    }

    public void handleDeleteStore(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int storeId = Integer.parseInt(request.getParameter("idStore"));
        String deleted_by = (String) request.getSession().getAttribute("username");

        boolean result = storeService.deleteStore(storeId, deleted_by);

        if(result) {
            request.getSession().setAttribute("messageResponse", "Delete store with id " + storeId + " successfully!");
            response.sendRedirect("/admin/stores");
        } else {
            request.getSession().setAttribute("messageResponse", "Delete store with id " + storeId + " failed, Please try again!");
            response.sendRedirect("/admin/stores");
        }
    }
}