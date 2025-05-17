package com.example.applivestream.model;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final List<User> users = new ArrayList<>();

    static {
        // Thêm người dùng mẫu
        users.add(new User("test@example.com", "123456"));
    }

    public static User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    public static boolean isEmailTaken(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    public static void register(User user) {
        users.add(user);
    }
}