package com.example.applivestream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlPath = App.class.getResource("/com/example/applivestream/views/login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("LiveStream");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

