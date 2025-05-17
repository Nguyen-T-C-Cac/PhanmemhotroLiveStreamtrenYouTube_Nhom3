package com.example.applivestream.controller;

import com.example.applivestream.model.User;
import com.example.applivestream.model.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void onRegister(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!email.contains("@") || password.length() < 6) {
            showAlert("Email không hợp lệ hoặc mật khẩu quá yếu", Alert.AlertType.ERROR);
            return;
        }

        if (UserService.isEmailTaken(email)) {
            showAlert("Email đã được sử dụng", Alert.AlertType.ERROR);
        } else {
            UserService.register(new User(email, password));
            showAlert("Đăng ký thành công", Alert.AlertType.INFORMATION);
            loadLoginScene();
        }
    }

    private void loadLoginScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/applivestream/views/login.fxml"));
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
