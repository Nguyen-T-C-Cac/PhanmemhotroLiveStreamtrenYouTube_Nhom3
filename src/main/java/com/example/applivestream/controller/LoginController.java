package com.example.applivestream.controller;

import com.example.applivestream.model.User;
import com.example.applivestream.model.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.applivestream.database.UserRepository.logAction;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    // 1.1.0 Người dùng chọn “Đăng nhập”
    @FXML
    private void onLogin(ActionEvent event) {
        // 1.1.1.2 Hệ thống hiển thị biểu mẫu đăng nhập: Nhập email và mật khẩu
        String email = emailField.getText();
        String password = passwordField.getText();

        // 1.1.2 Người dùng điền thông tin và nhấn “Xác nhận”
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ thông tin!", Alert.AlertType.WARNING);
            return;
        }
        // 1.1.3.2 Hệ thống kiểm tra email và mật khẩu có khớp hay không
        User user = UserService.authenticate(email, password);
        logAction(email, "Đăng nhập");
        if (user != null) {
            // 1.1.4.2 Tạo session (ở đây là lưu tên user tạm thời)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applivestream/views/main.fxml"));
                Parent root = loader.load();

                // Truyền tên người dùng sang main
                MainController controller = loader.getController();
                controller.setUserName(user.getName());

            // 1.1.5 Chuyển hướng người dùng đến trang chính
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("LiveStream Studio");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Lỗi khi chuyển sang giao diện chính!", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Email hoặc mật khẩu không đúng!", Alert.AlertType.ERROR);
        }
    }
    // 1.1.0 Người dùng chọn “Đăng ký”
    @FXML
    private void onRegisterRedirect(ActionEvent event) {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/applivestream/views/register.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Đăng ký");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Không thể mở giao diện đăng ký!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }
}
