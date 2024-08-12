package com.example.demo.util;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class StringUtil {

    public static boolean stringIsNullOrEmty(String str) {
        if (str == null)
            return true;
        else {
            if (str.trim().length() <= 0)
                return true;
        }
        return false;
    }

    public static boolean stringIsNullOrEmty(Object str) {
        if (str == null)
            return true;
        else {
            if (str.toString().trim().length() <= 0)
                return true;
        }
        return false;
    }

    public static String generateString(int length) {
        String str = UUID.randomUUID().toString();
        str = str.replaceAll("\\D", "");
        if (str.length() < length) {
            str = StringUtils.repeat("0", length - str.length()) + str;
        }
        return str.substring(0, length);
    }

}
