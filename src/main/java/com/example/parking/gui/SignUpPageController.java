package com.example.parking.gui;
import com.example.parking.*;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SignUpPageController {

    @FXML
    private TextField usernameField, licenseNumberField, licensePlateField, balanceField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> vehicleTypeComboBox;
    @FXML
    private ListView<String> vehicleListView;
    @FXML
    private Button signUpButton;
    @FXML
    private Hyperlink loginLink;
    @FXML
    private TextField textField;

    @FXML
    private Button toggleButton;

    @FXML
    private ImageView eyeIcon;

    private Image eyeOpenIcon;
    private Image eyeClosedIcon;

    // ObservableList To Store Vehicles To Print Them
    private ObservableList<String> vehiclesString = FXCollections.observableArrayList();
    // To Store Vehicles Into The Owner
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    @FXML
    public void initialize() {
        // Initialize the ListView with the vehicles list
        vehicleListView.setItems(vehiclesString);

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

    @FXML
    public void handleAddVehicle() {
        // Get input values
        String vehicleType = vehicleTypeComboBox.getValue();
        String licensePlate = licensePlateField.getText().trim();

        // Validate inputs
        if (vehicleType == null || vehicleType.isEmpty()) {
            showAlert("Message","Error" ,"Please select a vehicle type.");
            return;
        }
        if (licensePlate.isEmpty()) {
            showAlert("Message", "Error" ,"Please enter a license plate.");
            return;
        }

        if (vehiclesString.size() >= 3) {
            showAlert("Message", "Error" ,"You can only add up to 3 vehicles.");
            return;
        }

        if (!licensePlateValid(licensePlate)) {
            showAlert("Message","Error", "Please enter a valid license plate.");
            return;
        }

        // Add the vehicle to the list
        String vehicleInfo = "Vehicle: " + vehicleType + ", Plate: " + licensePlate;

        Vehicle vehicle = new Vehicle(VehicleType.valueOf(vehicleType), licensePlate);

        vehicles.add(vehicle);
        vehiclesString.add(vehicleInfo);

        // Clear the input fields
        licensePlateField.clear();
        vehicleTypeComboBox.setValue(null);
    }

    @FXML
    public void handleRemoveVehicle() {
        // Get the selected vehicle
        int selectedIndex = vehicleListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            return;
        }

        // Remove the selected vehicle
        vehiclesString.remove(selectedIndex);
        vehicles.remove(selectedIndex);
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

    // Validations
    private boolean licensePlateValid(String licensePlate) {
        return Vehicle.isValidLicensePlate(licensePlate);
    }

    private boolean userNameValid(String userName) {
        return !(SystemManager.isOwnerExist(userName));
    }
    private boolean passwordValid(String password) {
        return (password.length() >= 6);
    }
    private boolean balanceValid(String balanceText) {
        try {
            double balanceDouble = Double.parseDouble(balanceText);
            // Check Greater Than 0
            return balanceDouble >= 0;
        } catch (NumberFormatException e) {
            // If Not Number
            return false;
        }
    }
    // Sign Up Button
    public void signUpButton(ActionEvent event) {
        // Get input values
        String userName = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String licenseNumber = licenseNumberField.getText().trim();
        String balanceText = balanceField.getText().trim();

        if (userName.isEmpty() || password.isEmpty() || licenseNumber.isEmpty() || balanceText.isEmpty()) {
            showAlert("Message", "Error", "All fields must be filled");
            return;
        }

        // if the Username Taken Error
        if (!userNameValid(userName)) {
            showAlert("Message","Error", "This Username Is Taken");
            return;
        }

        if(!passwordValid(password)) {
            showAlert("Message","Error", "Password must be at least 6 characters long!");
            return;
        }

        if (!balanceValid(balanceText)) {
            showAlert("Message","Error", "Enter A Valid Balance");
            return;
        }

        double balanceDouble = Double.parseDouble(balanceText);

        // Register Successfully
        showAlert("Message","SignUp Successfully!", "Welcome to our garage <3");
        SystemManager.addOwner(userName, password, licenseNumber, vehicles, balanceDouble);

        // Go LoginPage
        goToLoginPage(event);
    }


    // Return To Login Page
    @FXML
    public void goToLoginPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/parking/LoginPageFXML.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Message","Error", "Failed to load the login page xfml.");
        }
    }
}