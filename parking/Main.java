package com.example.parking;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.Map;

public class Main extends Application {

    Stage window;
    Scene LogInScene, SignInScene;
    public static void main(String[] args) {
//        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        // LogIn Scene
        Label loginLable = new Label("Welcome To The Login Scene");
        Button toSignInButton = new Button("Go To Sign In Page");
        toSignInButton.setOnAction(e -> window.setScene(SignInScene));
        VBox logingLayout = new VBox(20);
        logingLayout.getChildren().addAll(loginLable, toSignInButton);
        logingLayout.setAlignment(Pos.CENTER);
        LogInScene = new Scene(logingLayout, 600, 500);

        // Sign In Scene
        Label signingLabel = new Label("Welcome To The Sign In Scene");
        Button toLogInButton = new Button("Go To Log In Page");
        toLogInButton.setOnAction(e -> window.setScene(LogInScene));
        VBox signinLayout = new VBox(20);
        signinLayout.getChildren().addAll(signingLabel, toLogInButton);
        signinLayout.setAlignment(Pos.CENTER);
        SignInScene = new Scene(signinLayout, 600, 500);

        window.setScene(LogInScene);
        window.setTitle("Parking System");
        window.show();
    }
}