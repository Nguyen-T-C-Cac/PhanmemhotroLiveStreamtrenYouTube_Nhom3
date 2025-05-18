package com.example.applivestream.controller;

import com.example.applivestream.model.User;
import com.example.applivestream.model.UserService;
import com.example.applivestream.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            loadMainScene();
        } else {
            showAlert("Sai email hoặc mật khẩu", Alert.AlertType.ERROR);
        }

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ thông tin.", Alert.AlertType.WARNING);
            return;
        }

        if (user != null) {
            SessionManager.createSession(user);
            loadMainScene();
        } else {
            showAlert("Sai email hoặc mật khẩu", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onRegisterRedirect(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/applivestream/views/register.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMainScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/applivestream/views/main.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(msg);
        alert.show();
    }
}