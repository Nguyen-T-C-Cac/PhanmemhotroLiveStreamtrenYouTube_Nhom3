package com.example.applivestream.model;

import com.example.applivestream.database.UserRepository;

public class UserService {

    public static boolean isEmailTaken(String email) {
        return UserRepository.isEmailTaken(email);
    }

    public static void register(User user) {
        UserRepository.saveUser(user);
    }

    public static User authenticate(String email, String password) {
        return UserRepository.findUser(email, password);
    }
}
