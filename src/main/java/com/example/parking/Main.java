package com.example.parking;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    Stage window;
    public static void main(String[] args) {
        SystemManager.initialize();

        launch(args);

        SystemManager.save_data_to_file();
    }
    // Admin
    @Override
    public void start(Stage primaryStage) {
            window = primaryStage;
        try
        {
            Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/parking/LoginPageFXML.fxml")));
            Scene scene = new Scene(root);

            window.setScene(scene);
            window.show();
        }
        catch (IOException e) {
            System.out.print("Failed To Open Initial File");
        }
    }
}