package com.example.demo.admin.servlet;

import com.example.demo.admin.dto.CakeResponse;
import com.example.demo.admin.dto.MaterialCakeResponse;
import com.example.demo.admin.service.CakeService;
import com.example.demo.admin.service.CategoryService;
import com.example.demo.admin.service.MaterialCakeService;
import com.example.demo.admin.service.MaterialService;
import com.example.demo.admin.service.impl.CakeServiceImpl;
import com.example.demo.admin.service.impl.CategoryServiceImpl;
import com.example.demo.admin.service.impl.MaterialCakeServiceImpl;
import com.example.demo.admin.service.impl.MaterialServiceImpl;
import com.example.demo.entity.Cake;
import com.example.demo.entity.Material;
import com.example.demo.entity.MaterialCake;
import com.example.demo.util.Constant;
import com.example.demo.util.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CakeServlet",
        value = {"/admin/cake/view", "/admin/cake/update", "/admin/cake/create", "/admin/cake/delete"})
@MultipartConfig
public class CakeServlet extends HttpServlet {

    private CakeService cakeService = new CakeServiceImpl();

    private MaterialService materialservice = new MaterialServiceImpl();

    private MaterialCakeService materialCakeService = new MaterialCakeServiceImpl();

    private CategoryService categoryService = new CategoryServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/admin/cake/view":
                this.getAll(request, response);
                break;
            case "/admin/cake/create":
                this.formCreate(request, response);
                break;
            case "/admin/cake/update":
                this.formUpdate(request, response);
                break;
            case "/admin/cake/delete":
                this.delete(request, response);
                break;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case "/admin/cake/create":
                this.create(request, response);
                break;
            case "/admin/cake/update":
                this.update(request, response);
                break;
        }
    }

    public void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int page = 1;
        if (!StringUtil.stringIsNullOrEmty(request.getParameter("page")))
            page = Integer.parseInt(request.getParameter("page"));
        String search = request.getParameter("search");
        int totalRecord = cakeService.totalRecord(search);
        if (totalRecord <= 0) {
            totalRecord = 1;
        }
        int totalPage = (int) Math.ceil(totalRecord * 1.0 / 10);
        List<CakeResponse> listCake = cakeService.getAllCakes((page - 1) * 10, 10, search);

        request.setAttribute("search", search);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);

        request.setAttribute("cakes", listCake);
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/cake/view-cake.jsp").forward(request, response);
    }

    public void formCreate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("materials", materialservice.getAllMaterials(Constant.MIN_PAGE, Constant.MAX_PAGE, ""));
        request.setAttribute("categories", categoryService.getAllCategories(Constant.MIN_PAGE, Constant.MAX_PAGE, ""));
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/cake/add-cake.jsp").forward(request, response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = cakeService.validateCreate(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            this.formCreate(request, response);
            return;
        }
        Cake cake = new Cake();
        cake.setName(request.getParameter("cakeName"));
        String code = request.getParameter("cakeCode");
        if (StringUtil.stringIsNullOrEmty(code)) {
            code = StringUtil.generateString(Constant.CODE_LENGTH);
        }
        cake.setCode(code);
        cake.setWeight(Integer.valueOf(request.getParameter("cakeWeight")));
        cake.setColor(request.getParameter("cakeColor"));
        cake.setSize(request.getParameter("cakeSize"));
        cake.setHeight(Integer.valueOf(request.getParameter("cakeHeight")));
        cake.setLength(Integer.valueOf(request.getParameter("cakeLength")));
        cake.setIdCategory(Long.valueOf(request.getParameter("idCategory")));
        cake.setPart(request.getPart("image"));
        cake.setStatus(Constant.ACTIVE);
        cake.setDescription(request.getParameter("descripiton"));
        cake.setCreatedBy((String) request.getSession().getAttribute("username"));
        boolean result = cakeService.createCake(cake);
        if (result) {
            Cake cakeResult = cakeService.getCakeByCode(code);
            Map<String, MaterialCake> materialMap = new HashMap<>();
            Map<String, String[]> parameterMap = request.getParameterMap();

            for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                String parameterName = param.getKey();
                String[] parameterValues = param.getValue();

                if (parameterName.startsWith("materials")) {
                    String[] parts = parameterName.split("\\[|\\]");
                    if (parts.length == 4) {
                        String id = parts[1];
                        String value = parameterValues[0];

                        MaterialCake material = materialMap.getOrDefault(id, new MaterialCake(Integer.valueOf(id), cakeResult.getId(), "", 0));
                        if ("input1".equals(parts[3])) {
                            material.setWeight(Integer.valueOf(value));
                        } else if ("input2".equals(parts[3])) {
                            material.setDescription(value);
                        }
                        material.setCreatedBy((String) request.getSession().getAttribute("username"));
                        materialMap.put(id, material);
                    }
                }
            }
            List<MaterialCake> materials = new ArrayList<>(materialMap.values());
            // logic: clear data cu roi add data moi
            boolean materialResult = materialCakeService.createBatchMaterCake(materials);
            request.getSession().setAttribute("response", "Add new cake successfully!");
        } else {
            request.getSession().setAttribute("response", "Add new cake faild!");
        }
        response.sendRedirect("/admin/cake/view");

    }

    public void formUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        Cake cake = cakeService.getOneById(id);

        // list khong nam trong san pham
        List<Material> listMaterail = materialservice.getMaterialNotInCake(cake.getId());
        // list material cake
        List<MaterialCakeResponse> listMaterialCake = materialCakeService.getMaterialCakeByCake(cake.getId());

        request.setAttribute("materialCakes", listMaterialCake);
        request.setAttribute("cake", cake);
        request.setAttribute("materials", listMaterail);
        request.setAttribute("categories", categoryService.getAllCategories(Constant.MIN_PAGE, Constant.MAX_PAGE, ""));
        request.getRequestDispatcher(Constant.PATH_ADMIN + "/cake/update-cake.jsp").forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> errors = cakeService.validateUpdate(request);
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            this.formUpdate(request, response);
            return;
        }
        Cake cake = new Cake();
        cake.setName(request.getParameter("cakeName"));
        String code = request.getParameter("cakeCode");
        cake.setCode(code);
        cake.setWeight(Integer.valueOf(request.getParameter("cakeWeight")));
        cake.setColor(request.getParameter("cakeColor"));
        cake.setSize(request.getParameter("cakeSize"));
        cake.setStatus(Integer.valueOf(request.getParameter("status")));
        cake.setHeight(Integer.valueOf(request.getParameter("cakeHeight")));
        cake.setLength(Integer.valueOf(request.getParameter("cakeLength")));
        cake.setIdCategory(Long.valueOf(request.getParameter("idCategory")));
        cake.setUpdatedBy((String) request.getSession().getAttribute("username"));

        if (request.getPart("image") != null) {
            cake.setPart(request.getPart("image"));
        }
        cake.setDescription(request.getParameter("descripiton"));
        cake.setId(Integer.valueOf(request.getParameter("id")));
        boolean result = cakeService.updateCake(cake);

        if (result) {
            Cake cakeResult = cakeService.getCakeByCode(code);
            Map<String, MaterialCake> materialMap = new HashMap<>();
            Map<String, String[]> parameterMap = request.getParameterMap();

            for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                String parameterName = param.getKey();
                String[] parameterValues = param.getValue();

                if (parameterName.startsWith("materials")) {
                    String[] parts = parameterName.split("\\[|\\]");
                    if (parts.length == 4) {
                        String id = parts[1];
                        String value = parameterValues[0];

                        MaterialCake material = materialMap.getOrDefault(id, new MaterialCake(Integer.valueOf(id), cakeResult.getId(), "", 0));
                        if ("input1".equals(parts[3])) {
                            material.setWeight(Integer.valueOf(value));
                        } else if ("input2".equals(parts[3])) {
                            material.setDescription(value);
                        }
                        material.setCreatedBy((String) request.getSession().getAttribute("username"));
                        materialMap.put(id, material);
                    }
                }
            }
            List<MaterialCake> materials = new ArrayList<>(materialMap.values());
            // logic: clear data cu roi add data moi
            boolean materialResult = materialCakeService.createBatchMaterCake(materials);
        }
        request.getSession().setAttribute("response", result ? "update cake successfully!" : "update cake faild!");
        response.sendRedirect("/admin/cake/view");
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cake cake = cakeService.getOneById(Long.parseLong(request.getParameter("id")));
        cake.setDeletedBy((String) request.getSession().getAttribute("username"));
        boolean result = cakeService.deleteCake(cake);
        request.getSession().setAttribute("response", result ? "delete cake succesfully!" : "delete new cake faild!");
        response.sendRedirect("/admin/cake/view");
    }


}
