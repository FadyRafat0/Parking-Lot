package com.example.parking.gui;
import com.example.parking.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class LoginPageController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink signUpLink;
    private static Owner currentOwner = new Owner("Fady", "123456", 1, "AA" ,
            new ArrayList<>(), 5000);

    @FXML
    private TextField textField;

    @FXML
    private Button toggleButton;

    @FXML
    private ImageView eyeIcon;

    private Image eyeOpenIcon;
    private Image eyeClosedIcon;

    @FXML
    public void initialize() {
        // Load icons (update paths accordingly)
        eyeOpenIcon = new Image(getClass().getResource("/css/images/eye-opened.png").toExternalForm());
        eyeClosedIcon = new Image(getClass().getResource("/css/images/eye-closed.png").toExternalForm());

        ImageView eyeIconView = new ImageView(eyeClosedIcon);
        eyeIconView.setFitWidth(55); // Set desired width
        eyeIconView.setFitHeight(55); // Set desired height
        eyeIconView.setPreserveRatio(true); // Maintain aspect ratio

        // Attach the ImageView to the toggleButton
        toggleButton.setGraphic(eyeIconView);

        // Bind password and text fields
        textField.textProperty().bindBidirectional(passwordField.textProperty());

        // Toggle logic
        toggleButton.setOnAction(e -> {
            if (textField.isVisible()) {
                textField.setVisible(false);
                textField.setManaged(false);
                passwordField.setVisible(true);
                passwordField.setManaged(true);
                eyeIconView.setImage(eyeClosedIcon);
            } else {
                passwordField.setVisible(false);
                passwordField.setManaged(false);
                textField.setVisible(true);
                textField.setManaged(true);
                eyeIconView.setImage(eyeOpenIcon);
            }
        });
    }


    private void showAlert(String title,String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.getDialogPane().getStyleClass().add("login-alert");

        URL cssFile = getClass().getResource("/css/style.css");
        if (cssFile != null) {
            alert.getDialogPane().getStylesheets().add(cssFile.toExternalForm());
        } else {
            System.out.println("CSS file not found.");
        }
        alert.showAndWait();
    }

    @FXML
    public void goToSignUpPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/parking/SignUpPageFXML.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Message", "Error","Failed to load the sign-up page xfml.");
        }
    }
    public void loginButton(ActionEvent event) throws IOException {
        // check the User_name And Password ->
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validation: Check if username or password are empty
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Message", "Error","Please fill in both username and password.");
            return;
        }


        // Go to Admin Page
        if (username.equals("admin") && password.equals("admin")) {
            showAlert("Message", "Logged Successfully","Welcome Admin!");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/parking/AdminPageFXML.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ( ((Node) event.getSource()).getScene().getWindow());
            stage.setScene(scene);
            stage.show();
            return;
        }

        // User Page
        if (SystemManager.isOwnerExist(username, password)) {
            // Set The Current Owner
            Owner owner = SystemManager.getOwner(username);
            setCurrentOwner(owner);

            showAlert("Message", "Logged Successfully","Welcome " + owner.getUserName() + "!");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/parking/UserPageFXML.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ( ((Node) event.getSource()).getScene().getWindow());
            stage.setScene(scene);
            stage.show();
            return;
        }

        // Not Found
        showAlert("Message", "Login Failed","Wrong Username/Password");
    }
    // Current Owner
    public static Owner getCurrentOwner() {
        return currentOwner;
    }
    public static void setCurrentOwner(Owner currentOwner) {
        LoginPageController.currentOwner = currentOwner;
    }
}