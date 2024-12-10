package com.example.parking;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
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
        try {
            window = primaryStage;
            Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/parking/AdminPageFXML.fxml")));
            Scene scene = new Scene(root);

            window.setScene(scene);
            window.show();
        }
        catch (Exception e) {
            System.out.println(e);
        }


    }
}