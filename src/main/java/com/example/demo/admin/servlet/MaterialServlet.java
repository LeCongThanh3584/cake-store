package com.example.demo.admin.servlet;

import com.example.demo.admin.service.CategoryService;
import com.example.demo.admin.service.MaterialService;
import com.example.demo.admin.service.impl.CategoryServiceImpl;
import com.example.demo.admin.service.impl.MaterialServiceImpl;
import com.example.demo.entity.Material;
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

@WebServlet(name = "MaterialServlet",
        value = {"/admin/material/view", "/admin/material/update", "/admin/material/create", "/admin/material/delete"})
public class MaterialServlet extends HttpServlet {

    private MaterialService materialservice;

    public MaterialServlet(MaterialService materialService) {
        if (materialService != null) {
            this.materialservice = materialService;
        } else {
            materialservice = new MaterialServiceImpl();
        }
    }

    public MaterialServlet() {
        this.materialservice = new MaterialServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/admin/material/view":
                this.getAll(request, response);
                break;
            case "/admin/material/create":
                this.formCreate(request, response);
                break;
            case "/admin/material/update":
                this.formUpdate(request, response);
                break;
            case "/admin/material/delete":
                this.delete(request, response);
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/admin/material/create":
                this.create(request, response);
                break;
            case "/admin/material/update":
                this.update(request, response);
                break;
        }
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int page = 1;
        String search = request.getParameter("search");
        if (!StringUtil.stringIsNullOrEmty(request.getParameter("page")))
            page = Integer.parseInt(request.getParameter("page"));
        List<Material> listMaterial = materialservice.getAllMaterials((page - 1) * 10, 10, search);

        int totalRecords = materialservice.getTotalRecords(search);
        int totalPage = (int) Math.ceil(totalRecords * 1.0 / 10);

        request.setAttribute("search", search);
        request.setAttribute("totalPage", totalPage == 0 ? 1 : totalPage);
        request.setAttribute("currentPage", page);

        request.setAttribute("materials", listMaterial);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/material/view-material.jsp").forward(request, response);
    }

    public void formCreate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/material/add-material.jsp").forward(request, response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (StringUtil.stringIsNullOrEmty(request.getParameter("name"))) {
            request.setAttribute("errors", "name is required");
            this.formCreate(request, response);
            return;
        }
        Material material = new Material();
        String code = request.getParameter("code");
        if (StringUtil.stringIsNullOrEmty(code)) {
            code = StringUtil.generateString(Constant.CODE_LENGTH);
        }
        material.setName(request.getParameter("name"));
        material.setDescription(request.getParameter("description"));
        material.setCode(code);
        material.setCreatedBy((String) request.getSession().getAttribute("username"));
        boolean result = materialservice.createMaterial(material);
        request.getSession().setAttribute("response", result ? "add material succesfully!" : "add material faild!");
        response.sendRedirect("/admin/material/view");
    }

    public void formUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Material material = materialservice.getOneById(Long.valueOf(request.getParameter("id")));
        request.setAttribute("mt", material);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/material/update-material.jsp").forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<String, String> errors = materialservice.validateUpdate(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            this.formUpdate(request, response);
            return;
        }

        Material material = new Material();
        material.setId(Integer.valueOf(request.getParameter("id")));
        material.setCode(request.getParameter("code"));
        material.setName(request.getParameter("name"));
        material.setUpdatedBy((String) request.getSession().getAttribute("username"));
        material.setDescription(request.getParameter("description"));
        boolean result = materialservice.updateMaterial(material);
        request.getSession().setAttribute("response", result ? "update material succesfully!" : "update material faild!");
        response.sendRedirect("/admin/material/view");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        String responseMessage = "This material does not exist or have been delete.";

        if (!StringUtil.stringIsNullOrEmty(id)) {
            Long categoryId = Long.valueOf(id);
            Material material = materialservice.getOneById(categoryId);
            if (material != null) {
                material.setDeletedBy((String) request.getSession().getAttribute("username"));
                boolean result = materialservice.deleteById(material);
                responseMessage = result ? "Delete material successfully!" : "Delete material failed!";
            }
        }
        request.getSession().setAttribute("response", responseMessage);
        response.sendRedirect("/admin/material/view");
    }

}
