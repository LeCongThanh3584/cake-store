package com.example.demo.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Map;

public class UploadUtil {

    public static String uploadImage(Part image, String code) {
        Cloudinary cloudinary = ThirdApiConfig.getCloudinary();
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getInputStream().readAllBytes(),
                    ObjectUtils.asMap("folder", Constant.ROOT_FOLDER_FILE + Constant.STORE_FOLDER_FILE + code));
            return (String) uploadResult.get("secure_url");
        } catch (IOException io){
            io.printStackTrace();
        }
        return null;
    }
}

