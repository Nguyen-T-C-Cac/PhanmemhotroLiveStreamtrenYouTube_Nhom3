package com.example.applivestream.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.Node;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {
    @FXML public Button startRecordButton;
    @FXML private Button endRecordButton;
    @FXML private ListView<String> sceneList;
    @FXML private ListView<String> sourceList;
    @FXML
    private ImageView previewImage;

    private Timer screenTimer;
    @FXML
    private Label welcomeLabel;
    private String userName;

    @FXML private Label timerLabel;
    private Timeline timeline;
    private int seconds = 0;

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
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            seconds++;
            timerLabel.setText(seconds + "s");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        startScreenCapture();
    }
    @FXML
    private void handleStartRecording() {
        seconds = 0;
        timerLabel.setText("0s");
        timeline.playFromStart();
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

    @FXML
    private void onEndStreamingClicked(ActionEvent event) {
        timeline.stop();

        try {
            // Load fxml của popup
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/applivestream/views/poststream.fxml")
            );
            Parent root = loader.load();

            // Tạo stage mới
            Stage dialog = new Stage();
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setTitle("Post-Stream Actions");
            dialog.setScene(new Scene(root));

            dialog.show();  // show() thay cho setScene() trên cửa sổ chính
        } catch (IOException e) {
            e.printStackTrace();
            showError("Không thể mở cửa sổ thao tác sau khi kết thúc stream.");
        }
    }



    @FXML
    private void handleEndRecording(ActionEvent event) {
        if (timeline != null) timeline.stop();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/applivestream/poststream.fxml"));
            Parent root = loader.load();

            PostStreamController controller = loader.getController();
            controller.setDuration(seconds);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public Button getEndRecordButton() {
        return endRecordButton;
    }

    public void setEndRecordButton(Button endRecordButton) {
        this.endRecordButton = endRecordButton;
    }
}
