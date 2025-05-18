package com.example.applivestream.model;

import com.example.applivestream.database.UserRepository;
import com.example.applivestream.util.PasswordUtils;

public class UserService {

    public static boolean isEmailTaken(String email) {
        return UserRepository.isEmailTaken(email);
    }

    public static void register(User user) {
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        UserRepository.saveUser(user);
    }

    public static User authenticate(String email, String plainPassword) {
        User user = UserRepository.findUserByEmail(email);
        if (user != null && PasswordUtils.checkPassword(plainPassword, user.getPassword())) {
            return user;
        }
        return null;
    }



}
