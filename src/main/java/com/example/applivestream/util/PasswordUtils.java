package com.example.applivestream.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static boolean isStrong(String password) {
        return password.length() >= 6; // Bạn có thể thêm điều kiện mạnh hơn như chứa số, chữ hoa...
    }
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}