package com.example.parking.gui;

import com.example.parking.spot.*;
import com.example.parking.*;

import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.property.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class TestController {

    Admin admin = new Admin();
    VehicleType currentVehicleType = VehicleType.Car;
    @FXML
    private VBox spotContainer;  // This will hold all the spots

    @FXML
    private ScrollPane spotsPane; // The parent container that holds everything

    // Add a new spot with a confirmation dialog
    public void addSpot() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Add New Spot");
        confirmationAlert.setHeaderText("Are you sure you want to add a new spot?");
        confirmationAlert.setContentText("This will create a new spot with ID: " + SystemManager.nextSpotID);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                admin.addNewSpot(currentVehicleType); // Add a new spot
                refreshSpotView(); // Refresh the view to show the new spot
            }
        });
    }
    // Remove an existing spot
    public void removeSpot() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, getSpotIds());
        dialog.setTitle("Remove Spot");
        dialog.setHeaderText("Select a Spot ID to remove:");
        dialog.setContentText("Spot ID:");
        dialog.showAndWait().ifPresent(spotId -> {
            admin.removeSpot(Integer.parseInt(spotId));  // Remove selected spot
            refreshSpotView();  // Refresh the view to remove the spot
        });
    }
    // Get list of spot IDs to be used for the remove spot dialog
    public List<String> getSpotIds() {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<Spot> spots = SystemManager.getSpotsWithType(currentVehicleType);

        for (Spot spot : spots) {
            ids.add(String.valueOf(spot.getSpotID()));
        }
        return ids;
    }

    public void removeSlot(Spot spot) {
        // Show a dialog to select the slot to remove (e.g., by slot ID)
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, getSlotIds(spot));  // List of slot IDs for the selected spot
        dialog.setTitle("Remove Slot");
        dialog.setHeaderText("Select a Slot ID to remove from Spot ID: " + spot.getSpotID());
        dialog.setContentText("Slot ID:");

        dialog.showAndWait().ifPresent(slotId -> {
            spot.removeSlot(Integer.parseInt(slotId));  // Remove the selected slot from the spot
            refreshSpotView();  // Refresh the UI after removing the slot
        });
    }
    public List<String> getSlotIds(Spot spot) {
        List<String> slotIds = new ArrayList<>();
        for (Slot slot : spot.getSlots()) {
            slotIds.add(String.valueOf(slot.getSlotID()));  // Add slot IDs of the selected spot
        }
        return slotIds;
    }

    public void refreshSpotView() {
        spotContainer.getChildren().clear();

        ArrayList<Spot> spots = SystemManager.getSpotsWithType(currentVehicleType);
        for (Spot spot : spots) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText("Spot ID: " + spot.getSpotID());

            // TableView to display slots
            TableView<Slot> slotTable = new TableView<>();
            slotTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<Slot, String> slotIdCol = new TableColumn<>("Slot ID");
            slotIdCol.setCellValueFactory(param ->
                    new SimpleStringProperty(String.valueOf(param.getValue().getSlotID()))
            );

            // To Show The Start Date , End Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            TableColumn<Slot, String> startDateCol = new TableColumn<>("Start Date");
            startDateCol.setCellValueFactory(param ->
                    new SimpleStringProperty(param.getValue().getStartDate().format(formatter))
            );

            TableColumn<Slot, String> endDateCol = new TableColumn<>("End Date");
            endDateCol.setCellValueFactory(param ->
                    new SimpleStringProperty(param.getValue().getEndDate().format(formatter))
            );

            TableColumn<Slot, Boolean> availabilityCol = new TableColumn<>("Is Available");
            availabilityCol.setCellValueFactory(param ->
                    new SimpleBooleanProperty(param.getValue().isAvailable())
            );

            slotTable.getColumns().addAll(slotIdCol, startDateCol, endDateCol, availabilityCol);
            slotTable.getItems().addAll(spot.getSlots());

            // Add the TableView to the TitledPane
            titledPane.setContent(slotTable);

            // Create text fields for start date and end date
            TextField startDateField = new TextField();
            startDateField.setPromptText("start date: (yyyy-MM-dd HH:mm)");
            TextField endDateField = new TextField();
            endDateField.setPromptText("end date: (yyyy-MM-dd HH:mm)");

            // Create Add Slot and Remove Slot buttons
            Button addSlotButton = new Button("Add Slot");
            addSlotButton.setOnAction(e -> {
                String startDateText = startDateField.getText();
                String endDateText = endDateField.getText();

                if (startDateText.isEmpty() || endDateText.isEmpty()) {
                    showAlert("Start date and end date cannot be empty.");
                    return;
                }
                // Validate and create a new slot
                try {
                    LocalDateTime startDate = LocalDateTime.parse(startDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    LocalDateTime endDate = LocalDateTime.parse(endDateText, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//                    Slot newSlot = new Slot(startDate, endDate, true); // Assuming availability is true
//                    spot.addSlot(newSlot); // Add slot to the spot
                    System.out.println("Add Slot Clicked");
                    refreshSpotView(); // Refresh the view
                } catch (DateTimeParseException ex) {
                    // Handle invalid date format
                    showAlert("Invalid date format. Please enter a valid date.");
                }
            });

            Button removeButton = new Button("Remove Slot");
            removeButton.setOnAction(e -> {
                removeSlot(spot);
            });

            // Layout for the text fields and buttons
            HBox actionBox = new HBox(10, startDateField, endDateField, addSlotButton, removeButton);
            actionBox.setAlignment(Pos.CENTER);

            // Add the actionBox (with text fields and buttons) below the table
            VBox spotContent = new VBox();
            spotContent.getChildren().addAll(slotTable, actionBox);
            titledPane.setContent(spotContent);

            // Ensure TitledPane's height fits content size
            titledPane.setMaxHeight(Region.USE_COMPUTED_SIZE);

            spotContainer.getChildren().add(titledPane);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
