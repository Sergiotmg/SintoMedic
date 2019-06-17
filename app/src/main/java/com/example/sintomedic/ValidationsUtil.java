package com.example.sintomedic;

import java.util.regex.Pattern;

public class ValidationsUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    public static boolean validateEmail(String email) {
        if (email != null) {
            return EMAIL_PATTERN.matcher(email).matches();
        }

        return false;
    }

    public static boolean notEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }


}