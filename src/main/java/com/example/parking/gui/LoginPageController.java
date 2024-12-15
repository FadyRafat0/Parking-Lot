package com.example.parking.gui;
import com.example.parking.*;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
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
    private static Owner currentOwner;

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