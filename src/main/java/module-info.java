module com.example.applivestream {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    opens com.example.applivestream.controller to javafx.fxml;

    opens com.example.applivestream to javafx.fxml;
    exports com.example.applivestream;
}