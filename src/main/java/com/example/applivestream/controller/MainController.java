package com.example.applivestream.controller;

import com.example.applivestream.model.User;
import com.example.applivestream.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML private Label welcomeLabel;

    @FXML
    public void initialize() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Chào mừng, " + currentUser.getEmail());
        }
    }
}