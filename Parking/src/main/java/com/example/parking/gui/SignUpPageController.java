package com.example.parking.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignUpPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField licenseNumberField;

    @FXML
    private ComboBox<String> vehicleTypeComboBox;

    @FXML
    private TextField licensePlateField;

    @FXML
    private ListView<String> vehicleListView;

    @FXML
    private Button signUpButton;

    @FXML
    private Hyperlink loginLink;

    // ObservableList to store vehicles
    private final ObservableList<String> vehicles = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the ListView with the vehicles list
        vehicleListView.setItems(vehicles);
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

        if (vehicles.size() >= 3) {
            showAlert("Error", "You can only add up to 3 vehicles.");
            return;
        }

        // Add the vehicle to the list
        String vehicleInfo = "Vehicle: " + vehicleType + ", Plate: " + licensePlate;
        vehicles.add(vehicleInfo);

        // Clear the input fields
        licensePlateField.clear();
        vehicleTypeComboBox.setValue(null);
    }

    @FXML
    public void handleRemoveVehicle() {
        // Get the selected vehicle
        String selectedVehicle = vehicleListView.getSelectionModel().getSelectedItem();
        if (selectedVehicle == null) {
            return;
        }

        // Remove the selected vehicle
        vehicles.remove(selectedVehicle);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void signUpButtonClicked() {
        // Get input values
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String licenseNumber = licenseNumberField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || licenseNumber.isEmpty()) {
            showAlert("Error", "All fields must be filled, and a vehicle type must be selected.");
            return;
        }
    }
}
