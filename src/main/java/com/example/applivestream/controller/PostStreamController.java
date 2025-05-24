package com.example.applivestream.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

public class PostStreamController implements Initializable {

    @FXML private HBox titleBox;
    @FXML private TextField titleField;
    @FXML private ListView<String> videoListView;

    @FXML private Label durationLabel;
    public void setDuration(int seconds) {
        durationLabel.setText("Recorded Time: " + seconds + "s");
    }

    @FXML
    public void initialize() {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ẩn input và list lúc khởi tạo
        titleBox.setVisible(false);
        titleBox.setManaged(false);
        videoListView.setVisible(false);
        videoListView.setManaged(false);

        // Thiết lập custom cell factory để mỗi item có thêm nút Xóa
        videoListView.setCellFactory(lv -> new ListCell<>() {
            private final HBox cellBox = new HBox(10);
            private final Label lbl = new Label();
            private final Button delBtn = new Button("Xóa");
            private final Button vieBtn = new Button("Xem");

            {
                // Cho nút Xóa remove chính item này khi click
                delBtn.setOnAction(e -> {
                    String item = getItem();
                    getListView().getItems().remove(item);
                });
                // cho label & button phóng to trong HBox
                HBox.setHgrow(lbl, Priority.ALWAYS);
                cellBox.getChildren().addAll(lbl, delBtn,vieBtn);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    lbl.setText(item);
                    setGraphic(cellBox);
                }
            }
        });
    }

    /** Hiện ô nhập tiêu đề khi nhấn Save Video */
    @FXML
    private void onSaveVideoClicked(ActionEvent event) {
        titleBox.setVisible(true);
        titleBox.setManaged(true);
        titleField.requestFocus();
    }

    /** Thêm tiêu đề vào ListView và ẩn ô nhập */
    @FXML
    private void onConfirmSaveClicked(ActionEvent event) {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            showAlert("Vui lòng nhập tiêu đề!");
            return;
        }
        videoListView.getItems().add(title);
        titleField.setText("");
        titleBox.setVisible(false);
        titleBox.setManaged(false);
    }

    /** Toggle show/ẩn danh sách video */
    @FXML
    private void onShowListClicked(ActionEvent event) {
        boolean showing = videoListView.isVisible();
        if (!showing && videoListView.getItems().isEmpty()) {
            // bạn có thể load từ DB ở đây nếu muốn
        }
        videoListView.setVisible(!showing);
        videoListView.setManaged(!showing);
    }

    private void showAlert(String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
