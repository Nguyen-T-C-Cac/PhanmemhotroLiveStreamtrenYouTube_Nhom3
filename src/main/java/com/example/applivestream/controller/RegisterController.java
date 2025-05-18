package com.example.applivestream.controller;

import com.example.applivestream.model.User;
import com.example.applivestream.model.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.util.regex.Pattern;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    // 1.1.1.1 Hiển thị biểu mẫu: Nhập tên, email, mật khẩu
    @FXML
    private void onRegister(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // 1.1.2 Người dùng nhấn xác nhận
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // 1.1.3.1 Kiểm tra định dạng email, độ mạnh mật khẩu, trùng email
        if (!isValidEmail(email)) {
            showAlert("Email không hợp lệ!");
            return;
        }

        if (password.length() < 6) {
            showAlert("Mật khẩu phải ít nhất 6 ký tự!");
            return;
        }

        if (UserService.isEmailTaken(email)) {
            showAlert("Email đã được sử dụng!");
            return;
        }
        // 1.1.4.1 Tạo tài khoản mới
        User user = new User(name, email, password);
        UserService.register(user);
        // 1.1.5 Chuyển hướng lại giao diện đăng nhập
        showAlert("Đăng ký thành công! Chuyển đến đăng nhập...");

        // Quay lại login.fxml
        try {
            Stage stage = (Stage) nameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/applivestream/views/login.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        return Pattern.matches("^(.+)@(.+)$", email);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void onBackToLogin() {
        try {
            Stage stage = (Stage) nameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/applivestream/views/login.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
