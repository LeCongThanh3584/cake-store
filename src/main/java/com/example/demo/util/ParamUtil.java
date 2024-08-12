package com.example.demo.util;

import jakarta.servlet.http.HttpServletRequest;

public class ParamUtil {

    public static Integer getNullInt(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        int value = parseInt(param);
        return value < 1 ? null : value;
    }

    public static int getInt(HttpServletRequest request, String name) {
        String param = request.getParameter(name);
        return parseInt(param);
    }

    public static int parseInt(String param) {
        int value = 0;
        try {
            value = param == null || param.isEmpty()
                    ? 0 : Integer.parseInt(param);
        } catch (NumberFormatException ignored) {
        }
        return Math.max(value, 0);
    }

    public static String get(HttpServletRequest request, String name) {
        return request.getParameter(name) == null ? "" : request.getParameter(name);
    }
}
