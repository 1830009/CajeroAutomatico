package com.mycompany.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage = FXMLLoader.load(getClass().getResource("/V_LogIn.fxml"));
        stage.setTitle("LogIn");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}