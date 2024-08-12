package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.CakeResponse;
import com.example.demo.admin.dto.CakeStoreResponse;
import com.example.demo.admin.service.CakeService;
import com.example.demo.admin.service.CakeStoreService;
import com.example.demo.admin.service.impl.CakeServiceImpl;
import com.example.demo.admin.service.impl.CakeStoreServiceImpl;
import com.example.demo.entity.CakeStore;
import com.example.demo.util.Constant;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.client.methods.HttpPatch;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "cakeStoreServlet", value = {"/admin/store/*", "/admin/add-new-cake-to-store",
        "/admin/update-store-cake", "/admin/delete-cake-from-store"})
public class CakeStoreServlet extends HttpServlet {
    private CakeStoreService cakeStoreService;
    private CakeService cakeService;

    @Override
    public void init() throws ServletException {
        cakeStoreService = new CakeStoreServiceImpl();
        cakeService = new CakeServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if(pathInfo != null && pathInfo.endsWith("/list-cake")) {
            this.getPageCakeForSaleOfStore(request, response, pathInfo);
        } else if(pathInfo != null && pathInfo.endsWith("/add-new-cake")) {
            this.getPageAddNewCakeToStore(request, response, pathInfo);
        }  else if(pathInfo != null && pathInfo.contains("/update-store-cake")) {
            this.getPageUpdateCakeStore(request, response, pathInfo);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/blank.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getRequestURI();
        switch (url) {
            case "/admin/add-new-cake-to-store":
                this.handleAddNewCakeToStore(request, response);
                break;
            case "/admin/update-store-cake":
                this.handleUpdateCakeStore(request, response);
                break;
            case "/admin/delete-cake-from-store":
                this.handleDeleteCakeForSale(request, response);
                break;
        }
    }

    public void getPageAddNewCakeToStore(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws ServletException, IOException {
        String[] parts = pathInfo.split("/");
        String storeId = parts[1];

        List<CakeResponse> cakeResponseList = cakeService.getAllCakesForStoreAddNew();

        request.setAttribute("storeId", storeId);
        request.setAttribute("listCakes", cakeResponseList);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/CakeStore/add-cakestore.jsp").forward(request, response);
    }

    public void getPageUpdateCakeStore(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws ServletException, IOException {
        String[] parts = pathInfo.split("/");
        Integer storeId = Integer.valueOf(parts[1]);
        Integer cakeStoreId = Integer.valueOf(parts[parts.length - 1]);

        CakeStoreResponse cakeStoreResponse = cakeStoreService.getCakeStoreById(cakeStoreId);

        request.setAttribute("cakeStore", cakeStoreResponse);
        request.setAttribute("storeId", storeId);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/CakeStore/update-cakestore.jsp").forward(request, response);
    }

    public void getPageCakeForSaleOfStore(HttpServletRequest request, HttpServletResponse response, String pathInfo) throws ServletException, IOException {
        String[] parts = pathInfo.split("/");
        Integer storeId = Integer.valueOf(parts[1]);
        String keyword = request.getParameter("keyword");
        Integer pageNumber = null;
        Integer pageSize = 1;

        if(StringUtil.stringIsNullOrEmty(request.getParameter("page"))) {
            pageNumber = 1;  //Nếu không có tham số page trên đường link thì nhận giá trị mặc định là 1
        } else {
            pageNumber = Integer.valueOf(request.getParameter("page"));  //Nếu có thì nhận giá trị đó
        }

        List<CakeStoreResponse> cakeStoreList = cakeStoreService.getCakeForSaleOfStore(storeId, keyword, pageNumber, pageSize);
        Integer totalPage = cakeStoreService.getTotalPages(keyword, pageSize, storeId);

        request.setAttribute("cakeStoreList", cakeStoreList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("storeId", storeId);

        request.getRequestDispatcher(Constant.PATH_ADMIN + "/CakeStore/view-cakestore.jsp").forward(request, response);
    }

    public void handleAddNewCakeToStore(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer storeId = Integer.valueOf(request.getParameter("storeId"));
        Integer quantity = Integer.valueOf(request.getParameter("quantity"));
        String cakeId = request.getParameter("cakeId");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        LocalDateTime manufactureDate = LocalDateTime.parse(request.getParameter("manufactureDate") + "T00:00:00");
        LocalDateTime expirationDate = LocalDateTime.parse(request.getParameter("expirationDate") + "T00:00:00");

        if(StringUtil.stringIsNullOrEmty(cakeId)) {
            request.getSession().setAttribute("messageResponse", "Error: Please select cake!");
            response.sendRedirect("/admin/store/" + storeId + "/add-new-cake");
            return;
        }

        if(expirationDate.isBefore(manufactureDate)) {
            request.getSession().setAttribute("messageResponse", "Error: Expiration date must be after the date of manufacture!");
            response.sendRedirect("/admin/store/" + storeId + "/add-new-cake");
            return;
        }


        if(price.compareTo(BigDecimal.valueOf(0)) < 0) {
            request.getSession().setAttribute("messageResponse", "Error: Price cannot be less than zero!");
            response.sendRedirect("/admin/store/" + storeId + "/add-new-cake");
            return;
        }

        if(quantity < 0) {
            request.getSession().setAttribute("messageResponse", "Error: Quantity cannot be less than zero!");
            response.sendRedirect("/admin/store/" + storeId + "/add-new-cake");
            return;
        }

        CakeStore newCakeStore = new CakeStore();
        newCakeStore.setIdCake(Integer.valueOf(cakeId));
        newCakeStore.setIdStore(storeId);
        newCakeStore.setPrice(price);
        newCakeStore.setQuantity(quantity);
        newCakeStore.setProductionDate(manufactureDate);
        newCakeStore.setExpirationDate(expirationDate);
        newCakeStore.setStatus(Integer.valueOf(request.getParameter("status")));
        newCakeStore.setCreatedBy((String) request.getSession().getAttribute("username"));

        boolean result = cakeStoreService.addNewCakeToStore(newCakeStore);

        if(result) {
            request.getSession().setAttribute("messageResponse", "Add new cake to store successfully!");
            response.sendRedirect("/admin/store/" + storeId + "/list-cake");
        } else {
            request.getSession().setAttribute("messageResponse", "Error: Add new cake to store failed, Please try again!");
            response.sendRedirect("/admin/store/" + storeId + "/add-new-cake");
        }
    }

    public void handleUpdateCakeStore(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer cakeStoreId = Integer.valueOf(request.getParameter("cakeStoreId"));
        Integer cakeId = Integer.valueOf(request.getParameter("cakeId"));
        Integer storeId = Integer.valueOf(request.getParameter("storeId"));
        LocalDateTime manufactureDate = LocalDateTime.parse(request.getParameter("manufactureDate") + "T00:00:00");
        LocalDateTime expirationDate = LocalDateTime.parse(request.getParameter("expirationDate") + "T00:00:00");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        Integer quantity = Integer.valueOf(request.getParameter("quantity"));

        if(expirationDate.isBefore(manufactureDate)) {
            request.getSession().setAttribute("messageResponse", "Error: Expiration date must be after the date of manufacture!");
            response.sendRedirect("/admin/store/" + storeId + "/update-store-cake/" + cakeStoreId);
            return;
        }

        if(price.compareTo(BigDecimal.valueOf(0)) < 0) {
            request.getSession().setAttribute("messageResponse", "Error: Price cannot be less than zero!");
            response.sendRedirect("/admin/store/" + storeId + "/update-store-cake/" + cakeStoreId);
            return;
        }

        if(quantity < 0) {
            request.getSession().setAttribute("messageResponse", "Error: Quantity cannot be less than zero!");
            response.sendRedirect("/admin/store/" + storeId + "/update-store-cake/" + cakeStoreId);
            return;
        }

        CakeStore cakeStoreUpdate = new CakeStore();
        cakeStoreUpdate.setId(cakeStoreId);
        cakeStoreUpdate.setIdCake(cakeId);
        cakeStoreUpdate.setIdStore(storeId);
        cakeStoreUpdate.setPrice(price);
        cakeStoreUpdate.setQuantity(quantity);
        cakeStoreUpdate.setProductionDate(manufactureDate);
        cakeStoreUpdate.setExpirationDate(expirationDate);

        cakeStoreUpdate.setStatus(Integer.valueOf(request.getParameter("status")));
        cakeStoreUpdate.setUpdatedBy((String) request.getSession().getAttribute("username"));

        boolean result = cakeStoreService.updateCakeStore(cakeStoreUpdate);

        if(result) {
            request.getSession().setAttribute("messageResponse", "Update cake for sales successfully!");
            response.sendRedirect("/admin/store/" + storeId + "/list-cake");
        } else {
            request.getSession().setAttribute("messageResponse", "Update cake for sales failed, Please try again!");
            response.sendRedirect("/admin/store/" + storeId + "/update-store-cake/" + cakeStoreId);
        }
    }

    public void handleDeleteCakeForSale(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer cakeStoreId = Integer.valueOf(request.getParameter("cakeStoreId"));
        Integer storeId = Integer.valueOf(request.getParameter("storeId"));
        String deletedBy = (String) request.getSession().getAttribute("username");
        boolean result = cakeStoreService.deleteCakeStore(cakeStoreId, deletedBy);

        if(result) {
            request.getSession().setAttribute("messageResponse", "Stop selling successfully!");
            response.sendRedirect("/admin/store/" + storeId + "/list-cake");
        } else {
            request.getSession().setAttribute("messageResponse", "Stop selling failed, Please try again!");
            response.sendRedirect("/admin/store/" + storeId + "/list-cake");
        }
    }
}
