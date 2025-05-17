module com.example.applivestream {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.applivestream to javafx.fxml;
    exports com.example.applivestream;
}