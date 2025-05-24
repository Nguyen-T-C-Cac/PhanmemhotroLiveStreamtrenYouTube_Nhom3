package com.example.applivestream.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelFormat;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.stage.Stage;

public class MainController implements Initializable {
    @FXML private ListView<String> sceneList;
    @FXML private ListView<String> sourceList;
    @FXML private ImageView previewImage;
    @FXML private Label welcomeLabel;
    @FXML private Button startStreamingButton;

    private Timer screenTimer;
    private String userName;

    public void setUserName(String name) {
        this.userName = name;
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
        if (startStreamingButton != null) {
            startStreamingButton.setOnAction(e -> openLivestreamSetupForm());
        }
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
        }, 0, 100);
    }

    private Image convertToFxImage(BufferedImage bf) {
        WritableImage wr = new WritableImage(bf.getWidth(), bf.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        pw.setPixels(0, 0, bf.getWidth(), bf.getHeight(),
                PixelFormat.getIntArgbInstance(), bf.getRGB(0, 0, bf.getWidth(), bf.getHeight(), null, 0, bf.getWidth()),
                0, bf.getWidth());
        return wr;
    }

    @FXML
    private void openLivestreamSetupForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applivestream/views/livestreamSetupForm.fxml"));
            Scene scene = new Scene(loader.load(), 600, 800);
            Stage stage = new Stage();
            stage.setTitle("Thiết lập phiên livestream");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}