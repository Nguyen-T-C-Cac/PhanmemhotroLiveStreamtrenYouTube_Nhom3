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

    @FXML
    private HBox titleBox;
    @FXML
    private TextField titleField;
    @FXML
    private ListView<String> videoListView;

    @FXML
    private Label durationLabel;

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
            //            7.2.1	User nhấn nút xóa video.
            private final Button delBtn = new Button("Xóa");
            //            7.3.1	Chọn video cần xem.
            private final Button vieBtn = new Button("Xem");

            {
//                7.2.2	Poststream.fxml gửi yêu cầu xóa đến PostStreamController
                delBtn.setOnAction(e -> {
                    String item = getItem();
                    getListView().getItems().remove(item);
                });
                // cho label & button phóng to trong HBox
                HBox.setHgrow(lbl, Priority.ALWAYS);
                cellBox.getChildren().addAll(lbl, delBtn, vieBtn);
            }

            //7.2.6	Poststream trả về listVideo đã xóa đi 1.
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

    @FXML
    private void onSaveVideoClicked(ActionEvent event) {
        titleBox.setVisible(true);
        titleBox.setManaged(true);
        titleField.requestFocus();
    }

    /**
     * Thêm tiêu đề vào ListView và ẩn ô nhập
     */
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

    //7.1.10	Poststream gửi về listVideo.
//    7.3.2	Poststream.fxml yêu cầu xử lí đến PoststreamController.
//    7.3.6	PoststreamController gửi video lên.
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
