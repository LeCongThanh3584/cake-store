package com.example.demo.admin.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.admin.dao.CakeDao;
import com.example.demo.admin.dto.CakeResponse;
import com.example.demo.admin.dto.CakeViewDto;
import com.example.demo.admin.service.CakeService;
import com.example.demo.entity.Cake;
import com.example.demo.util.Constant;
import com.example.demo.util.ThirdApiConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CakeServiceImpl implements CakeService {

    private final CakeDao cakeDao = new CakeDao();

    @Override
    public List<CakeResponse> getAllCakes(int page, int pageSize, String search) {
        return cakeDao.getAllCake(page, pageSize, search);
    }

    @Override
    public List<CakeResponse> getAllCakesForStoreAddNew() {
        return cakeDao.getAllCake();
    }

    @Override
    public int totalRecord(String search) {
        return cakeDao.totalRecord(search);
    }

    @Override
    public boolean createCake(Cake cake) {
        try {
            Cloudinary cloudinary = ThirdApiConfig.getCloudinary();
            String folder = Constant.ROOT_FOLDER_FILE + Constant.CAKE_FOLDER_FILE + cake.getCode();
            cloudinary.api().createFolder(folder, ObjectUtils.emptyMap());
            String url = uploadImage(cake.getPart(), folder);
            cake.setImage(url);
            return cakeDao.createCake(cake);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateCake(Cake cake) {
        try {
            Cloudinary cloudinary = ThirdApiConfig.getCloudinary();
            Cake currentCake = getOneById(cake.getId());
            String folder = Constant.ROOT_FOLDER_FILE + Constant.CAKE_FOLDER_FILE + cake.getCode();
            if (!cake.getCode().equals(currentCake.getCode())) {
                folder = (String) cloudinary.uploader().rename(folder, Constant.ROOT_FOLDER_FILE + Constant.CAKE_FOLDER_FILE + cake.getCode(), ObjectUtils.emptyMap()).get("url");
            }
            if (cake.getPart().getSize() > 0) {
                String url = uploadImage(cake.getPart(), folder);
                cake.setImage(url);
            } else {
                cake.setImage(currentCake.getImage());
            }
            return cakeDao.updateCake(cake);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCake(Cake cake) {
        return cakeDao.deleteCake(cake);
    }

    @Override
    public Cake getOneById(long id) {
        return cakeDao.getOneById(id);
    }

    @Override
    public Cake getCakeByCode(String code) {
        return cakeDao.getCodeByCode(code);
    }

    public String uploadImage(Part part, String folderName) throws IOException {
        Cloudinary cloudinary = ThirdApiConfig.getCloudinary();
        InputStream fileContent = part.getInputStream();
        try {
            Map uploadResult = cloudinary.uploader().upload(fileContent.readAllBytes(), ObjectUtils.asMap());
            String imageUrl = uploadResult.get("url").toString();
            return imageUrl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> validateCreate(HttpServletRequest request) throws IOException, ServletException {
        Map<String, String> errors = new HashMap<>();

        Map<String, String[]> parameterMap = request.getParameterMap();
        boolean hasValidParameter = false;

        for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
            String parameterName = param.getKey();
            if (parameterName.startsWith("materials")) {
                hasValidParameter = true;
                break;
            }
        }
        if (!hasValidParameter) {
            errors.put("material", "material cannot be null");
        }

        if (request.getPart("image") == null) {
            errors.put("image", "image cannot be null");
        }

        String code = request.getParameter("cakeCode");
        if (getCakeByCode(code) != null) {
            errors.put("code", "code is exists");
        }

        String name = request.getParameter("cakeName");
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "name cannot be null");
        }
        String weight = request.getParameter("cakeWeight");
        if (weight == null || weight.trim().isEmpty()) {
            errors.put("weight", "code cannot be null");
        } else {
            if (Integer.valueOf(weight) < 0) {
                errors.put("weight", "weight is number");
            }
        }
        String color = request.getParameter("cakeColor");
        if (color == null || color.trim().isEmpty()) {
            errors.put("color", "code cannot be null");
        }
        String size = request.getParameter("cakeSize");
        if (size == null || size.trim().isEmpty()) {
            errors.put("size", "code cannot be null");
        }
        String height = request.getParameter("cakeHeight");
        if (height == null || height.trim().isEmpty()) {
            errors.put("height", "height cannot be null");
        } else {

            if (Integer.valueOf(height) < 0) {
                errors.put("height", "height must be number");
            }
        }
        String length = request.getParameter("cakeLength");
        if (length == null || length.trim().isEmpty()) {
            errors.put("length", "length cannot be null");
        } else {
            if (Integer.valueOf(length) < 0) {
                errors.put("length", "length is number");
            }

        }
        return errors;
    }

    @Override
    public Map<String, String> validateUpdate(HttpServletRequest request) throws IOException {
        Map<String, String> errors = new HashMap<>();

        String name = request.getParameter("cakeName");
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "name cannot be null");
        }

        String code = request.getParameter("cakeCode");
        if (code == null || code.trim().isEmpty()) {
            errors.put("code", "code cannot be null");
        }

        if (getCakeByCode(code) != null) {
            errors.put("code", "code is exists");
        }

        String weight = request.getParameter("cakeWeight");
        if (weight == null || weight.trim().isEmpty()) {
            errors.put("weight", "code cannot be null");
        }

        String color = request.getParameter("cakeColor");
        if (color == null || color.trim().isEmpty()) {
            errors.put("color", "code cannot be null");
        }

        String size = request.getParameter("cakeSize");
        if (size == null || size.trim().isEmpty()) {
            errors.put("size", "code cannot be null");
        }

        String height = request.getParameter("cakeHeight");
        if (height == null || height.trim().isEmpty()) {
            errors.put("height", "height cannot be null");
        }

        String length = request.getParameter("cakeLength");
        if (length == null || length.trim().isEmpty()) {
            errors.put("length", "length cannot be null");
        }

        return errors;
    }

    @Override
    public List<CakeViewDto> getTopSelling(Integer storeId, String dateType) {
        return cakeDao.getTopSelling(storeId, dateType);
    }
}
