package com.example.parking.gui;
import com.example.parking.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
    // ObservableList To Store Vehicles To Print Them
    private ObservableList<String> vehiclesString = FXCollections.observableArrayList();
    // To Store Vehicles Into The Owner
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    @FXML
    public void initialize() {
        // Initialize the ListView with the vehicles list
        vehicleListView.setItems(vehiclesString);
    }

    @FXML
    public void handleAddVehicle() {
        // Get input values
        String vehicleType = vehicleTypeComboBox.getValue();
        String licensePlate = licensePlateField.getText().trim();

        // Validate inputs
        if (vehicleType == null || vehicleType.isEmpty()) {
            showAlert("Error", "Please select a vehicle type.");
            return;
        }
        if (licensePlate.isEmpty()) {
            showAlert("Error", "Please enter a license plate.");
            return;
        }

        if (vehiclesString.size() >= 3) {
            showAlert("Error", "You can only add up to 3 vehicles.");
            return;
        }

        if (!licensePlateValid(licensePlate)) {
            showAlert("Error", "Please enter a valid license plate.");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Validations
    private boolean licensePlateValid(String licensePlate) {
        return Vehicle.isValidLicensePlate(licensePlate);
    }

    private boolean userNameValid(String userName) {
        return !(SystemManager.isUserNameExist(userName));
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
            showAlert("Error", "All fields must be filled");
            return;
        }

        // if the Username Taken Error
        if (!userNameValid(userName)) {
            showAlert("Error", "This Username Is Taken");
            return;
        }

        if(!passwordValid(password)) {
            showAlert("Error", "Password must be at least 6 characters long!");
            return;
        }

        if (!balanceValid(balanceText)) {
            showAlert("Error", "Enter A Valid Balance");
            return;
        }

        double balanceDouble = Double.parseDouble(balanceText);

        // Register Successfully
        showAlert("Success", "signUp Successfully!");
        SystemManager.register(userName, password, licenseNumber, vehicles, balanceDouble);

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
            showAlert("Error", "Failed to load the login page xfml.");
        }
    }
}