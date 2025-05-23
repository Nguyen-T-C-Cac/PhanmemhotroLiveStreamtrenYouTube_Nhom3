package com.example.applivestream.util;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", Pattern.CASE_INSENSITIVE);

    public static boolean isValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}