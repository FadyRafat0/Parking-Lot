package com.example.parking.gui;

import com.example.parking.*;
import com.sun.javafx.charts.Legend;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;

import javafx.scene.layout.*;
import javafx.stage.*;
import org.controlsfx.control.Rating;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

public class UserPageController {
    private Owner owner;
    private VehicleType currentVehicleType;

    private ScrollPane lastScrollPane = new ScrollPane();
    private Pane lastPane = new Pane();
    @FXML
    private Label headerText;

    @FXML
    private Pane homePane, reservationPane, depositPane, updateDataPane,backPane, updateReservationPane;
    @FXML
    private BorderPane feedbackPane;
    @FXML
    private ScrollPane makeReservationPane;


    //feedback
    @FXML
    private Label submit_msg;
    @FXML
    private TextField OwnerID_field;
    @FXML
    private TextField ReservationID_field;
    @FXML
    private Rating RatingBar;
    @FXML
    private TextArea txt_field;
    @FXML
    private Button submit_btn;
    private Feedback feedback;
    private boolean isFeedbackSubmitted = false;

    @FXML
    private TextField depositAmountField;
    @FXML
    private Label  balanceLabel;
    @FXML
    private Button depositButton;

    // Reservations
    @FXML
    private TableView<Reservation> ReservationTable;
    @FXML
    private GridPane gridPaneSlots;


    public void initialize() {
        owner = LoginPageController.getCurrentOwner();
        homePane.setVisible(false);
        reservationPane.setVisible(false);
        depositPane.setVisible(false);
        feedbackPane.setVisible(false);
        updateDataPane.setVisible(false);
        backPane.setVisible(false);
        makeReservationPane.setVisible(false);
        updateReservationPane.setVisible(false);

        headerText.setText("Welcome " + owner.getUserName());
        switchToPane(homePane);
    }

    private void switchToPane(Pane pane) {
        backPane.setVisible(false);

        lastPane.setVisible(false);
        lastScrollPane.setVisible(false);
        pane.setVisible(true);
        lastPane = pane;
    }
    private  void switchToPane(ScrollPane pane) {
        backPane.setVisible(false);

        lastPane.setVisible(false);
        lastScrollPane.setVisible(false);
        pane.setVisible(true);
        lastScrollPane = pane;
    }

    private void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        alert.getDialogPane().getStyleClass().add("user-alert");

        URL cssFile = getClass().getResource("/css/adminStyle.css");
        if (cssFile != null) {
            alert.getDialogPane().getStylesheets().add(cssFile.toExternalForm());
        } else {
            System.out.println("CSS file not found.");
        }

        alert.showAndWait();
    }

    public void handleBackButton() {
        switchToPane(reservationPane);
        backPane.setVisible(false);
    }

    // Left Buttons
    // Home Page
    @FXML
    public void goToHomePage() {
        headerText.setText("Welcome " + owner.getUserName());
        switchToPane(homePane);
    }

    // Reservations Page
    public void chooseReservations() {
        headerText.setText("Reservation Management");
        switchToPane(reservationPane);
    }
    public void goToUpdateReservationPane() {
        switchToPane(updateReservationPane);
        headerText.setText("Reservations History");
        backPane.setVisible(true);
        updateReservationView();
    }
    public void updateReservationView() {
        // to prevent duplication
        ReservationTable.getColumns().clear();
        ReservationTable.getItems().clear();
        ReservationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ArrayList<Reservation> reservations = owner.getReservations();

        TableColumn<Reservation, String> ownerIdCol = new TableColumn<>("Owner ID");
        ownerIdCol.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(param.getValue().getOwnerID())));

        TableColumn<Reservation, String> reservationIdCol = new TableColumn<>("Reservation ID");
        reservationIdCol.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(param.getValue().getReservationID())));

        TableColumn<Reservation, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(param.getValue().getTotalAmount())));

        // To Show The Start Date , End Date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Start Date Column
        TableColumn<Reservation, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getReservationDate().format(formatter))
        );

        TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(param ->
                new SimpleStringProperty((param.getValue().getStatus() ? "Confirmed" : "Canceled"))
        );

        ReservationTable.getColumns().addAll(ownerIdCol, reservationIdCol, amountCol, dateCol, statusCol);
        ReservationTable.getItems().addAll(reservations);
    }

    public void goToMakeReservation(){
        switchToPane(makeReservationPane);
        headerText.setText("Make a New Reservation");
        backPane.setVisible(true);
        makeReservationView();
    }

    private List<Slot> selectedSlots = new ArrayList<>();
    private double currentTotalAmount = 0;
    public void makeReservationView() {
        ArrayList<Slot> slots = SystemManager.getAllAvaiableSlots(owner);
        int columns = 4; // Slots per row

        // Create a label to display the Total Amount
        Label totalAmountLabel = new Label("Total Amount: $0.00");
        totalAmountLabel.getStyleClass().add("total-amount-label");
        gridPaneSlots.add(totalAmountLabel, 0, 0, columns, 1); // Add the total amount label above the slots

        for (int i = 0; i < slots.size(); ++i) {
            Slot slot = slots.get(i);
            VBox slotBox = createSlotBox(
                    slot.getSpotType() + " Spot",
                    slot.getSpotID(),
                    slot.getStartDate(),
                    slot.getEndDate(),
                    slot.getHours(),
                    5.5,
                    totalAmountLabel, // Pass totalAmountLabel to track the total
                    slot
            );
            slotBox.setAlignment(Pos.CENTER); // Centers content within the VBox
            slotBox.setSpacing(10);
            slotBox.getStyleClass().add("slot-box");

            int row = i / columns;
            int col = i % columns;
            gridPaneSlots.add(slotBox, col, row + 1); // Offset by 1 to leave space for the total amount label
        }

        // Create a button to confirm reservation
        Button makeReservationButton = new Button("Make Reservation");
        makeReservationButton.setOnAction(event -> showConfirmationDialog(totalAmountLabel.getText()));

        // Add the button below the ScrollPane
        HBox buttonContainer = new HBox(10, makeReservationButton);
        buttonContainer.setAlignment(Pos.CENTER);
        gridPaneSlots.add(buttonContainer, 0, slots.size() / 4 + 2, 4, 1); // Add below grid
    }
    private VBox createSlotBox(String spotId, int slotId, LocalDateTime startDate, LocalDateTime endDate,
                               int hours, double amount, Label totalAmountLabel, Slot slot) {
        // Header (Spot ID)
        Label labelSpotId = new Label(spotId);
        labelSpotId.setMaxWidth(Double.MAX_VALUE);
        labelSpotId.setAlignment(Pos.CENTER);
        labelSpotId.getStyleClass().add("slot-header");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Adjust format as needed
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        // Body Rows
        HBox slotRow = createDetailRow("Spot:", String.valueOf(slotId));
        HBox startDateRow = createDetailRow("Start:", formattedStartDate);
        HBox endDateRow = createDetailRow("End:", formattedEndDate);
        HBox hoursRow = createDetailRow("Hours:", String.valueOf(hours));

        VBox body = new VBox(5, slotRow, startDateRow, endDateRow, hoursRow);

        // Footer (Amount on the left, Checkbox on the right)
        Label amountLabel = new Label("Amount: $" + amount);
        amountLabel.getStyleClass().add("amount-label");

        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().add("slot-checkbox");
        checkBox.setOnAction(e -> {
            if (checkBox.isSelected()) {
                selectedSlots.add(slot);
                currentTotalAmount += amount;
            }
            else {
                selectedSlots.remove(slot);
                currentTotalAmount -= amount;
            }
            totalAmountLabel.setText("Total Amount: $" + currentTotalAmount);
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

    private void showConfirmationDialog(String totalAmountText) {
        double penalty = owner.getPayment().getPenalty();  // Your logic for penalty calculation
        double baseAmount = 0;
        for (Slot slot : selectedSlots) {
            baseAmount += owner.getPayment().reservationBaseAmount(slot.getSpotID(), slot.getSlotID());
        }
        double balance = owner.getPayment().getBalance(); // Assuming you have a method to get the balance

        double totalAmount = baseAmount + penalty;
        if (balance < totalAmount) {
            // Handle insufficient balance
            Alert alert = new Alert(Alert.AlertType.ERROR, "Insufficient balance. Please add more funds.");
            alert.getDialogPane().getStyleClass().add("dialog-content"); // Apply dialog content CSS
            alert.showAndWait();
        } else {
            // Proceed with reservation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reservation Confirmation");
            alert.setHeaderText("Reservation Details");
            alert.setContentText(String.format("Base Amount: $%.2f\nPenalty: $%.2f\nFinal Amount: $%.2f\nBalance: $%.2f",
                    baseAmount, penalty, totalAmount, balance));

            // Apply the dialog's custom CSS
            alert.getDialogPane().getStyleClass().add("dialog-content");
            ButtonType okButton = ButtonType.OK;
            ButtonType cancelButton = ButtonType.CANCEL;
            alert.getButtonTypes().setAll(okButton, cancelButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    for (Slot slot : selectedSlots) {
                        SystemManager.addNewReservation(owner.getOwnerID(), slot,
                        owner.getPayment().reservationBaseAmount(slot.getSpotID(), slot.getSlotID()),
                        owner.getPayment().reservationTotalAmount(slot.getSpotID(), slot.getSlotID()));
                    }
                }
            });
        }
    }


    // Deposit Page
    public void goToDepositPage() {
        switchToPane(depositPane);
        headerText.setText("Deposit Management");
        depositView();
    }
    public void depositView() {
        try {
            double balance = owner.getPayment().getBalance();
            balanceLabel.setText("Current Balance: $" + String.format("%.2f", balance));

            depositAmountField.clear();
            depositButton.setOnAction(event -> {
                String depositAmountText = depositAmountField.getText().trim();
                if (depositAmountText.isEmpty()) {
                    showAlert("Message","Error", "Deposit amount cannot be empty.");
                    return;
                }
                try {
                    double depositAmount = Double.parseDouble(depositAmountText);
                    if (depositAmount <= 0) {
                        showAlert("Message","Error", "Deposit amount must be greater than zero.");
                        return;
                    }
                    owner.getPayment().deposit(depositAmount);
                    balanceLabel.setText("Current Balance: $" + String.format("%.2f", owner.getPayment().getBalance()));
                    showAlert("Message","Success", "Funds added successfully.");
                    depositAmountField.clear();
                } catch (NumberFormatException e) {
                    showAlert("Message","Error", "Invalid amount entered. Please enter a valid number.");
                }
            });
        } catch (Exception e) {
            showAlert("Message","Error", "An error occurred while loading the deposit view.");
        }
    }


    // Feedback Page
    public void goToFeedbackPage() {
        headerText.setText("Make Feedback");
        switchToPane(feedbackPane);
      //  FeedbackView();
    }
    public void FeedbackView() {

        if (isFeedbackSubmitted) {
            return;
        }
        try {

            String ownerIdText = OwnerID_field.getText();
            String reservationIdText = ReservationID_field.getText();

            if (ownerIdText.isEmpty() || reservationIdText.isEmpty()) {
                submit_msg.setText("Owner ID and Reservation ID cannot be empty!");
                return;
            }


            int ownerID = Integer.parseInt(ownerIdText);
            int reservationID = Integer.parseInt(reservationIdText);


            String message = txt_field.getText();
            if (message.isEmpty()) {
                submit_msg.setText("Feedback message cannot be empty!");
                return;
            }


            double rate = RatingBar.getRating();

            feedback = new Feedback(ownerID, reservationID, rate, message);
            isFeedbackSubmitted = true;

            disableInputs();
            submit_msg.setText("Your feedback has been submitted successfully!");

        } catch (NumberFormatException e) {
            submit_msg.setText("Invalid ID Number! Please enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            submit_msg.setText(e.getMessage());
        }
    }
    private void disableInputs() {
        OwnerID_field.setDisable(true);
        ReservationID_field.setDisable(true);
        RatingBar.setDisable(true);
        txt_field.setDisable(true);
        submit_btn.setDisable(true);
    }

    // Update Page

    public void goToUpdateDataPage(){
        switchToPane(updateDataPane);
        UpdateDataView();

    }
    public void UpdateDataView() {
        // Prevent duplication
        updateDataPane.getChildren().clear();

        // Username
        Label userNameLabel = new Label("Username:");
        userNameLabel.getStyleClass().add("form-label");

        TextField userNameField = new TextField(owner.getUserName());
        userNameField.setPromptText("Enter new username");
        userNameField.getStyleClass().add("form-textfield");

        // Old Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("form-label");

        PasswordField oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Enter your old password");
        oldPasswordField.getStyleClass().add("form-textfield");

        // New Password
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter new password");
        passwordField.getStyleClass().add("form-textfield");

        //Confirm Password
        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.getStyleClass().add("form-label");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm new password");
        confirmPasswordField.getStyleClass().add("form-textfield");

        //Update Profile
        Button updateButton = new Button("Update Profile");
        updateButton.setOnAction(event -> handleUpdateProfile(userNameField, oldPasswordField, passwordField, confirmPasswordField));
        updateButton.getStyleClass().add("update-button");

        //Form
        VBox updateForm = new VBox(10, userNameLabel, userNameField, passwordLabel, oldPasswordField, passwordField, confirmPasswordLabel, confirmPasswordField, updateButton);
        updateForm.setAlignment(Pos.CENTER);
        updateForm.getStyleClass().add("form-container");
        updateDataPane.getChildren().add(updateForm);
    }


    private void handleUpdateProfile(TextField userNameField, PasswordField oldPasswordField, PasswordField passwordField,PasswordField confirmPasswordField) {
        String newUserName = userNameField.getText().trim();
        String oldPassword = oldPasswordField.getText().trim();
        String newPassword = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (newUserName.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Message","Error", "All fields must be filled out.");
            return;
        }

        if(!oldPassword.equals(owner.getPassword())){
            showAlert("Message","Erorr", "You have entered wrong password, try again!");
            return;
        }

        if(newPassword.length()<6)
        {
            showAlert("Message","Error","Password must be at least 6 characters long!");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Message","Error", "Passwords do not match.");
            return;
        }

        owner.setUserName(newUserName);
        owner.setPassword(newPassword);

        showAlert("Message","Success", "Profile updated successfully.");
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
            showAlert("Message","Error", "Failed to load the login page xfml.");
        }
    }
}