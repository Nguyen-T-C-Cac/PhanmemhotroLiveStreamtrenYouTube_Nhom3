package com.example.applivestream.util;
import com.example.applivestream.model.User;

public class SessionManager {
    private static User currentUser;

    public static void createSession(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clearSession() {
        currentUser = null;
    }
}