package com.example.demo.admin.servlet;

import com.example.demo.admin.service.CategoryService;
import com.example.demo.admin.service.impl.CategoryServiceImpl;
import com.example.demo.entity.Category;
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

@WebServlet(name = "CategoryServlet",
        value = {"/admin/category/view", "/admin/category/update", "/admin/category/create", "/admin/category/delete"})
public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService;

    public CategoryServlet(CategoryService categoryservice) {
        if (categoryservice != null) {
            this.categoryService = categoryservice;
        } else {
            categoryService = new CategoryServiceImpl();
        }
    }

    public CategoryServlet() {
        this.categoryService = new CategoryServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/admin/category/view":
                this.getAllCategories(request, response);
                break;
            case "/admin/category/create":
                this.formCreate(request, response);
                break;
            case "/admin/category/update":
                this.formUpdate(request, response);
                break;
            case "/admin/category/delete":
                this.delete(request, response);
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/admin/category/create":
                this.create(request, response);
                break;
            case "/admin/category/update":
                this.update(request, response);
                break;
        }
    }

    public void getAllCategories(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int page = 1;
        String search = request.getParameter("search");
        if (!StringUtil.stringIsNullOrEmty(request.getParameter("page")))
            page = Integer.parseInt(request.getParameter("page"));
        List<Category> listCategory = categoryService.getAllCategories((page - 1) * 10, 10, search);

        int totalRecord = categoryService.totalRecord(search);
        int totalPage = (int) Math.ceil(totalRecord * 1.0 / 10);

        request.setAttribute("search", search);
        request.setAttribute("totalPage", totalPage == 0 ? 1 : totalPage);
        request.setAttribute("currentPage", page);

        request.setAttribute("categories", listCategory);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/category/view-category.jsp").forward(request, response);
    }

    public void formCreate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/category/add-category.jsp").forward(request, response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (StringUtil.stringIsNullOrEmty(request.getParameter("name"))) {
            request.setAttribute("errors", "name is required");
            this.formCreate(request, response);
            return;
        }
        Category category = new Category();
        String code = request.getParameter("code");
        if (StringUtil.stringIsNullOrEmty(code)) {
            code = StringUtil.generateString(Constant.CODE_LENGTH);
        }
        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));
        category.setCode(code);
        category.setCreatedBy((String) request.getSession().getAttribute("username"));
        boolean result = categoryService.createCategory(category);

        request.getSession().setAttribute("response", result ? "create category succesfully!" : "create categorye faild!");
        response.sendRedirect("/admin/category/view");
    }

    public void formUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Category category = categoryService.getOneById(Long.valueOf(request.getParameter("id")));
        request.setAttribute("ct", category);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/category/update-category.jsp").forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> errors = categoryService.validateUpdate(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            this.formUpdate(request, response);
            return;
        }

        Category category = new Category();
        category.setId(Integer.valueOf(request.getParameter("id")));
        category.setCode(request.getParameter("code"));
        category.setName(request.getParameter("name"));
        category.setUpdatedBy((String) request.getSession().getAttribute("username"));
        category.setDescription(request.getParameter("description"));
        boolean result = categoryService.updateCategory(category);
        request.getSession().setAttribute("response", result ? "update category succesfully!" : "update categorye faild!");
        response.sendRedirect("/admin/category/view");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String responseMessage = "This category does not exist or have been delete.";

        if (!StringUtil.stringIsNullOrEmty(id)) {
            Long categoryId = Long.valueOf(id);
            Category category = categoryService.getOneById(categoryId);
            if (category != null) {
                category.setDeletedBy((String) request.getSession().getAttribute("username"));
                boolean result = categoryService.deleteCategory(category);
                responseMessage = result ? "Delete category successfully!" : "Delete category failed!";
            }
        }
        request.getSession().setAttribute("response", responseMessage);
        response.sendRedirect("/admin/category/view");
    }


}
