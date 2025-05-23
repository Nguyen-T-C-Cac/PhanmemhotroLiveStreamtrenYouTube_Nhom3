package com.example.applivestream.controller;

import com.example.applivestream.util.TokenExchangeUtil;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Function;

public class AuthCode {

    /**
     * Hiển thị form xác thực OAuth và xử lý token cho các nền tảng như YouTube, Facebook, Twitch
     *
     * @param platformName        Tên nền tảng ("YouTube", "Facebook", "Twitch")
     * @param tokenValidator      Hàm xác thực access token, trả về tên tài khoản nếu hợp lệ, null nếu không
     * @param statusLabel         Label hiển thị trạng thái xác thực
     * @param accountLabel        Label hiển thị tên tài khoản
     * @param streamKeys          Map lưu access token theo nền tảng
     * @param accountNames        Map lưu tên tài khoản theo nền tảng
     */
    public static void show(String platformName,
                            Function<String, String> tokenValidator,
                            Label statusLabel,
                            Label accountLabel,
                            Map<String, String> streamKeys,
                            Map<String, String> accountNames) {

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Nhập mã xác minh cho " + platformName);

        Label label = new Label("Dán mã code sau khi xác thực " + platformName + ":");
        TextField codeField = new TextField();
        Button submitBtn = new Button("Xác thực");

        Label status = new Label();

        submitBtn.setOnAction(e -> {
            String code = codeField.getText().trim();
            if (code.isEmpty()) {
                status.setText("Vui lòng nhập mã xác minh.");
                return;
            }

            dialog.close();

            new Thread(() -> {
                String accessToken = TokenExchangeUtil.exchangeCodeForAccessToken(code);
                if (accessToken != null) {
                    String accountName = tokenValidator.apply(accessToken);
                    if (accountName != null) {
                        Platform.runLater(() -> {
                            streamKeys.put(platformName, accessToken);
                            accountNames.put(platformName, accountName);
                            statusLabel.setText("Kết nối thành công");
                            accountLabel.setText("Tài khoản: " + accountName);
                            accountLabel.setVisible(true);
                            accountLabel.setManaged(true);
                        });
                    } else {
                        Platform.runLater(() -> statusLabel.setText("Xác thực thất bại: Token không hợp lệ"));
                    }
                } else {
                    Platform.runLater(() -> statusLabel.setText("Lỗi trao đổi token từ mã code"));
                }
            }).start();
        });

        VBox layout = new VBox(10, label, codeField, submitBtn, status);
        layout.setPadding(new javafx.geometry.Insets(10));
        dialog.setScene(new Scene(layout, 350, 180));
        dialog.show();
    }
}
