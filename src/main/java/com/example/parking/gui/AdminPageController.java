package com.example.parking.gui;

import com.example.parking.spot.*;

import com.example.parking.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminPageController {

    private Admin admin;
    private VehicleType currentVehicleType;

    private ScrollPane lastScrollPane = new ScrollPane();
    private Pane lastPane = new Pane();
    @FXML
    private Label headerText;

    @FXML
    private Pane homePane, chooseCarPane, ownersPane, totalAmountPane, feedbackPane;
    @FXML
    private ScrollPane spotsPane;

    @FXML
    private VBox spotContainer;

    public void initialize() {
        admin = new Admin();
        homePane.setVisible(false);
        chooseCarPane.setVisible(false);
        spotsPane.setVisible(false);
//        ownersPane.setVisible(false);
//        totalAmountPane.setVisible(false);
//        feedbackPane.setVisible(false);

        switchToPane(homePane);
    }

    private void switchToPane(Pane pane) {
        lastPane.setVisible(false);
        lastScrollPane.setVisible(false);
        pane.setVisible(true);
        lastPane = pane;
    }
    private  void switchToPane(ScrollPane pane) {
        lastPane.setVisible(false);
        lastScrollPane.setVisible(false);
        pane.setVisible(true);
        lastScrollPane = pane;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Left Buttons
    // Home Page
    @FXML
    public void goToHomePage() {
        headerText.setText("Welcome To Admin Dashboard");
        switchToPane(homePane);
    }

    // Owners Page
    public void goToOwnersPage() {
        headerText.setText("Owners");
        switchToPane(ownersPane);
    }

    // Spots Page
    public void goToChooseCarPage() {
        headerText.setText("Choose Car");
        switchToPane(chooseCarPane);
    }
    public void carSelection() {
        headerText.setText("Car Spots");
        currentVehicleType = VehicleType.Car;
        goToSpotsPage();
    }
    public void bikeSelection() {
        headerText.setText("Bike Spots");
        currentVehicleType = VehicleType.Bike;
        goToSpotsPage();
    }
    public void truckSelection() {
        headerText.setText("Four By Four Spots");
        currentVehicleType = VehicleType.Truck;
        goToSpotsPage();
    }
    public void goToSpotsPage() {
        switchToPane(spotsPane);

        refreshSpotView();
    }
    public void addSpot() {
        // Show a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Add New Spot");
        confirmationAlert.setHeaderText("Are you sure you want to add a new spot?");
        confirmationAlert.setContentText("This will create a new spot with ID: " + SystemManager.nextSpotID);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Give The Spot Type
                admin.addNewSpot(currentVehicleType); // Add new spot with the generated ID
                refreshSpotView(); // Refresh the UI
            }
        });
    }
    public void removeSpot() {
        // Example: Prompt user to select a spot to remove
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, getSpotIds());
        dialog.setTitle("Remove Spot");
        dialog.setHeaderText("Select a Spot ID to remove:");
        dialog.setContentText("Spot ID:");
        dialog.showAndWait().ifPresent(spotId -> {
            admin.removeSpot(Integer.parseInt(spotId));
            refreshSpotView();
        });
    }
    public List<String> getSpotIds() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<Spot> spots = SystemManager.getSpotsWithType(currentVehicleType);

        for (Spot spot : spots) {
            ids.add(String.valueOf(spot.getSpotID()));
        }
        return ids;
    }

    public void refreshSpotView() {
        spotContainer.getChildren().clear();

        ArrayList<Spot> spots = SystemManager.getSpotsWithType(currentVehicleType);
        for (Spot spot : spots) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText("Spot ID: " + spot.getSpotID());

            TableView<Slot> slotTable = new TableView<>();
            slotTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<Slot, String> slotIdCol = new TableColumn<>("Slot ID");
            slotIdCol.setCellValueFactory(param ->
                    new SimpleStringProperty(String.valueOf(param.getValue().getSlotID())));

            // To Show The Start Date , End Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            // Start Date Column
            TableColumn<Slot, String> startDateCol = new TableColumn<>("Start Date");
            startDateCol.setCellValueFactory(param ->
                    new SimpleStringProperty(param.getValue().getStartDate().format(formatter))
            );
            // End Date Column
            TableColumn<Slot, String> endDateCol = new TableColumn<>("End Date");
            endDateCol.setCellValueFactory(param ->
                    new SimpleStringProperty(param.getValue().getEndDate().format(formatter))
            );

            // Availability Column
            TableColumn<Slot, Boolean> availabilityCol = new TableColumn<>("Is Available");
            availabilityCol.setCellValueFactory(param ->
                    new SimpleBooleanProperty(param.getValue().isAvailable())
            );

            // Add columns to TableView
            slotTable.getColumns().addAll(slotIdCol, startDateCol, endDateCol, availabilityCol);
            slotTable.getItems().addAll(spot.getAllSlots());

            // Add the TableView to the TitledPane
            titledPane.setContent(slotTable);

            // Ensure TitledPane's height fits content size (TableView)
            titledPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

            spotContainer.getChildren().add(titledPane);
        }
    }


    // Total Amount Page
    public void goToTotalAmountPage() {
        headerText.setText("Total Amount");
        switchToPane(totalAmountPane);
    }

    // Feedback Page
    public void goToFeedbackPage() {
        headerText.setText("Feedbacks");
        switchToPane(feedbackPane);
    }

    // Logout
    public void logoutButton(ActionEvent event) {
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
