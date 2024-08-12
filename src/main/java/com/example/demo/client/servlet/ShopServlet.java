package com.example.demo.client.servlet;

import com.example.demo.client.dto.CakePage;
import com.example.demo.client.dto.CakeViewDto;
import com.example.demo.client.service.CakeService;
import com.example.demo.client.service.CategoryService;
import com.example.demo.client.service.MaterialService;
import com.example.demo.client.service.StoreService;
import com.example.demo.client.service.impl.CakeServiceImpl;
import com.example.demo.client.service.impl.CategoryServiceImpl;
import com.example.demo.client.service.impl.MaterialServiceImpl;
import com.example.demo.client.service.impl.StoreServiceImpl;
import com.example.demo.entity.Category;
import com.example.demo.entity.Material;
import com.example.demo.entity.Store;
import com.example.demo.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "shopServlet", value = "/shop")
public class ShopServlet extends HttpServlet {
    Integer pageSize = 4;
    private CakeService cakeService;
    private CategoryService categoryService;
    private StoreService storeService;
    private MaterialService materialService;

    public void init() {
        cakeService = new CakeServiceImpl();
        categoryService = new CategoryServiceImpl();
        storeService = new StoreServiceImpl();
        materialService = new MaterialServiceImpl();
    }


    private CakePage getCakePage(HttpServletRequest request) {
        String query = ParamUtil.get(request, "query");
        String sortBy = ParamUtil.get(request, "sortBy");
        String direction = ParamUtil.get(request, "direction");

        Integer categoryId = ParamUtil.getNullInt(request, "categoryId");
        Integer storeId = ParamUtil.getNullInt(request, "storeId");
        Integer materialId = ParamUtil.getNullInt(request, "materialId");

        Integer from = ParamUtil.getNullInt(request, "from");
        Integer to = ParamUtil.getNullInt(request, "to");

        CakePage cakePage = new CakePage();
        cakePage.setCategoryId(categoryId);
        cakePage.setStoreId(storeId);
        cakePage.setMaterialId(materialId);

        cakePage.setFrom(from);
        cakePage.setTo(to);

        cakePage.setQuery(query);
        cakePage.setSortBy(sortBy);
        cakePage.setDirection(direction);

        return cakePage;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        CakePage cakePage = getCakePage(request);

        Integer pageCount = cakeService.CountSearch(cakePage);
        Integer page = ParamUtil.getNullInt(request, "page");
        page = page == null ? 1 : Math.max(Math.min(page, pageCount), 1);
        Integer offset = (page - 1) * pageSize;

        //OPTIONS
        List<Category> categories = categoryService.GetCategories();
        List<Store> stores = storeService.GetAll();
        List<Material> materials = materialService.getAll();
        List<CakeViewDto> cakes = cakeService.Search(cakePage, pageSize, offset);

        request.setAttribute("cakes", cakes);
        request.setAttribute("materials", materials);
        request.setAttribute("categories", categories);
        request.setAttribute("stores", stores);

        //PAGING ATTRIBUTES
        int maxPage = (int) Math.ceil((double) pageCount / pageSize);
        Integer startPage = page > 3 ? page - 3 : 1;
        Integer endPage = Math.min(maxPage <= page ? page : page + 3, maxPage);

        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);

        request.getRequestDispatcher("/WEB-INF/views/shop/index.jsp")
                .forward(request, response);
    }


    public void destroy() {
    }
}