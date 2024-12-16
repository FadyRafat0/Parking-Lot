package com.example.parking.gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class TestController {

    @FXML
    private GridPane gridPaneSlots;

    public void initialize() {
        int totalSlots = 12; // Total slots
        int columns = 4; // Slots per row

        for (int i = 0; i < totalSlots; i++) {
            VBox slotBox = createSlotBox(
                    "Truck Spot",
                     String.valueOf(i + 1),
                    "2024-12-13",
                    "2024-12-20",
                    4,
                    "$20"
            );
            slotBox.setAlignment(Pos.CENTER); // Centers content within the VBox
            slotBox.setSpacing(10);
            slotBox.getStyleClass().add("slot-box");
            int row = i / columns;
            int col = i % columns;
            gridPaneSlots.add(slotBox, col, row);
        }
    }
    private VBox createSlotBox(String spotId, String slotId, String startDate, String endDate, int hours, String amount) {
        // Header (Spot ID)
        Label labelSpotId = new Label(spotId);
        labelSpotId.setMaxWidth(Double.MAX_VALUE);
        labelSpotId.setAlignment(Pos.CENTER);
        labelSpotId.getStyleClass().add("slot-header");

        // Body Rows
        HBox slotRow = createDetailRow("Spot:", slotId);
        HBox startDateRow = createDetailRow("Start:", startDate);
        HBox endDateRow = createDetailRow("End:", endDate);
        HBox hoursRow = createDetailRow("Hours:", String.valueOf(hours));

        VBox body = new VBox(5, slotRow, startDateRow, endDateRow, hoursRow);

        // Footer (Amount on the left, Checkbox on the right)
        Label amountLabel = new Label("Amount: " + amount);
        amountLabel.getStyleClass().add("amount-label");

        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().add("slot-checkbox");
        checkBox.setOnAction(event -> {
            if (checkBox.isSelected()) {
                System.out.println(slotId + " selected");
            } else {
                System.out.println(slotId + " deselected");
            }
        });

        // Spacer to separate amount and checkbox
        Region spacer = new Region();
        spacer.setMinWidth(10); // Adjust spacing as needed
        HBox.setHgrow(spacer, Priority.ALWAYS); // Expands to push elements apart

        HBox footer = new HBox(10, amountLabel, spacer, checkBox);
        footer.getStyleClass().add("slot-footer");

        // Slot Box Layout
        VBox slotBox = new VBox(labelSpotId, body, footer);
        slotBox.getStyleClass().add("slot-box");

        return slotBox;
    }
    private HBox createDetailRow(String label, String value) {
        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("slot-label");

        Label valueNode = new Label(value);
        valueNode.getStyleClass().add("slot-value");

        HBox row = new HBox(10, labelNode, valueNode);
        row.getStyleClass().add("slot-row");

        return row;
    }
}
