package com.example.applivestream.util;

public class PasswordUtils {
    public static boolean isStrong(String password) {
        return password.length() >= 6; // Bạn có thể thêm điều kiện mạnh hơn như chứa số, chữ hoa...
    }
}