package com.example.applivestream.controller;

import com.example.applivestream.model.User;
import com.example.applivestream.model.UserService;
import com.example.applivestream.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void onLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = UserService.authenticate(email, password);
        if (user != null) {
            SessionManager.createSession(user);
            showAlert("Đăng nhập thành công", Alert.AlertType.INFORMATION);
            // Chuyển sang main.fxml nếu muốn
        } else {
            showAlert("Sai email hoặc mật khẩu", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.show();
    }
}