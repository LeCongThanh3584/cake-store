package com.example.demo.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verifyPassword(String rawPassword, String hashedPassword) {

        if (rawPassword == null || hashedPassword == null) {
            return false;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), hashedPassword);
        return result.verified;
    }
}
