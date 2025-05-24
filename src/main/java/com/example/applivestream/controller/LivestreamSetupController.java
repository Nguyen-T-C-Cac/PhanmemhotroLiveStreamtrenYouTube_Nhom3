package com.example.applivestream.controller;

// Nhập các thư viện cần thiết từ JavaFX để làm việc với giao diện người dùng
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import java.time.LocalDate;

// Lớp điều khiển cho giao diện thiết lập livestream
public class LivestreamSetupController {

    @FXML private TextField titleField; // Ô nhập tiêu đề livestream
    @FXML private TextArea descriptionField; // Ô nhập mô tả livestream
    @FXML private Button thumbnailButton; // Nút để chọn ảnh đại diện
    @FXML private Label thumbnailPath; // Nhãn hiển thị đường dẫn ảnh đã chọn
    @FXML private DatePicker datePicker; // Bộ chọn ngày để lên lịch livestream
    @FXML private ComboBox<String> platformComboBox; // Hộp chọn nền tảng phát sóng (YouTube, Facebook, Twitch)
    @FXML private TextField streamkeyField; // Ô nhập stream key
    @FXML private RadioButton liveRadio; // Nút chọn hình thức phát trực tiếp
    @FXML private RadioButton preRecordedRadio; // Nút chọn hình thức công chiếu (video đã ghi sẵn)
    @FXML private ToggleGroup streamTypeGroup; // Nhóm để quản lý lựa chọn giữa phát trực tiếp và công chiếu
    @FXML private HBox videoBox; // Hộp chứa các thành phần để chọn video (ẩn nếu chọn phát trực tiếp)
    @FXML private Button videoButton; // Nút để chọn file video
    @FXML private Label videoPath; // Nhãn hiển thị đường dẫn video đã chọn
    @FXML private Button saveButton; // Nút lưu thông tin thiết lập livestream

    // Phương thức khởi tạo, được gọi tự động khi giao diện được tải
    @FXML
    private void initialize() {
        // Thiết lập ngày mặc định cho bộ chọn ngày là ngày hiện tại
        datePicker.setValue(LocalDate.now());

        // Xử lý sự kiện khi nhấn nút chọn ảnh đại diện
        thumbnailButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser(); // Tạo bộ chọn file
            // Thêm bộ lọc để chỉ chọn file ảnh (png, jpg, jpeg)
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            // Hiển thị cửa sổ chọn file và lấy file được chọn
            java.io.File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) { // Nếu người dùng đã chọn file
                thumbnailPath.setText(file.getName()); // Hiển thị tên file trên nhãn
            }
        });

        // Xử lý sự kiện khi nhấn nút chọn video (cho hình thức công chiếu)
        videoButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser(); // Tạo bộ chọn file
            // Thêm bộ lọc để chỉ chọn file video (mp4, mkv, avi)
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mkv", "*.avi"));
            // Hiển thị cửa sổ chọn file và lấy file được chọn
            java.io.File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) { // Nếu người dùng đã chọn file
                videoPath.setText(file.getName()); // Hiển thị tên file trên nhãn
            }
        });

        // Lắng nghe sự thay đổi lựa chọn giữa phát trực tiếp và công chiếu
        streamTypeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            // Hiển thị hoặc ẩn phần chọn video dựa trên lựa chọn (chỉ hiển thị nếu chọn "Công chiếu")
            videoBox.setVisible(newToggle == preRecordedRadio);
            videoBox.setManaged(newToggle == preRecordedRadio);
        });

        // Xử lý sự kiện khi nhấn nút "Lưu"
        saveButton.setOnAction(e -> System.out.println("Form submitted!")); // In thông báo khi lưu thành công
    }

    // Xử lý hiệu ứng khi di chuột vào nút "Lưu"
    @FXML
    private void onSaveButtonMouseEntered() {
        // Đổi màu nền nút thành màu xanh đậm hơn khi di chuột vào
        saveButton.setStyle("-fx-background-color: #1d4ed8; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
    }

    // Xử lý hiệu ứng khi di chuột ra khỏi nút "Lưu"
    @FXML
    private void onSaveButtonMouseExited() {
        // Đặt lại màu nền nút về màu xanh ban đầu khi di chuột ra
        saveButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px;");
    }
}