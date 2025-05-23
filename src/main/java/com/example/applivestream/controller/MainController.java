package com.example.applivestream.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelFormat;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Initializable {
    @FXML private ListView<String> sceneList;
    @FXML private ListView<String> sourceList;
    @FXML
    private ImageView previewImage;

    private Timer screenTimer;
    @FXML
    private Label welcomeLabel;
    private String userName;

    public void setUserName(String name) {
        this.userName = name;
        // Nếu giao diện đã được tải xong, cập nhật ngay
        if (welcomeLabel != null) {
            welcomeLabel.setText("Chào mừng, " + userName + "!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (userName != null) {
            welcomeLabel.setText("Chào mừng, " + userName + "!");
        }
        startScreenCapture();
    }
    private void startScreenCapture() {
        screenTimer = new Timer();
        screenTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Robot robot = new Robot();
                    Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    BufferedImage screen = robot.createScreenCapture(screenRect);
                    javafx.application.Platform.runLater(() -> previewImage.setImage(convertToFxImage(screen)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 100); // update every 100ms
    }

    private Image convertToFxImage(BufferedImage bf) {
        WritableImage wr = new WritableImage(bf.getWidth(), bf.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        pw.setPixels(0, 0, bf.getWidth(), bf.getHeight(),
                PixelFormat.getIntArgbInstance(), bf.getRGB(0, 0, bf.getWidth(), bf.getHeight(), null, 0, bf.getWidth()),
                0, bf.getWidth());
        return wr;
    }
//Xử lý nút Startstreamming
@FXML
    private void handleStartStreaming(ActionEvent event) {
    Set<String> selected = SettingsController.getSelectedPlatforms();
    if (selected == null || selected.isEmpty()) {
        System.out.println("Bạn cần chọn ít nhất một nền tảng để phát.");
        return;
    }

    StreamingController streamingController = new StreamingController();
    streamingController.startMultiPlatformStream(selected);
}

    //Xử lý Settings
    @FXML
    private void handleSettings(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("/com/example/applivestream/views/settings.fxml");
            //System.out.println("FXML URL: " + fxmlUrl);
            FXMLLoader loader = new FXMLLoader(fxmlUrl);

            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait(); // Đợi người dùng nhấn “Đồng ý”
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
