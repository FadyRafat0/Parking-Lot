package com.example.parking.gui;

import com.example.parking.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.controlsfx.control.Rating;

import java.io.*;
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
    private Pane homePane, reservationPane, depositPane, updateDataPane,backPane, displayReservationsPane, makeReservationPane;
    @FXML
    private ScrollPane spotsPane;
    @FXML
    private BorderPane feedbackPane;

    @FXML
    private TableView<Feedback> FeedbackTable;

    @FXML
    private TableView<Reservation> ReservationTable;

    @FXML
    private Label totalAmountLabel;

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


    public void initialize() {
        owner = LoginPageController.getCurrentOwner();
        homePane.setVisible(false);
        spotsPane.setVisible(false);
        reservationPane.setVisible(false);
        depositPane.setVisible(false);
        feedbackPane.setVisible(false);
        updateDataPane.setVisible(false);
        backPane.setVisible(false);
        displayReservationsPane.setVisible(false);
        makeReservationPane.setVisible(false);

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
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

    // Spots Page
    public void goToSpotsPage() {
        switchToPane(spotsPane);
        refreshSpotView();
    }
    public void refreshSpotView() {

    }

    // Reservations Page
    public void chooseReservations()
    {
        headerText.setText("Reservation Management");
        switchToPane(reservationPane);
    }
    public void goToDisplayReservationPage() {
        switchToPane(displayReservationsPane);
        headerText.setText("Reservations History");
        backPane.setVisible(true);
        reservationView();
    }
    public void reservationView() {
        // to prevent duplication
        ReservationTable.getColumns().clear();
        ReservationTable.getItems().clear();
        ReservationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ArrayList<Reservation> reservations = owner.getReservations();

        double totalAmount = 0;
        for (Reservation reservation : reservations) {
            if (reservation.isActive())
                totalAmount += reservation.getTotalAmount();
        }
        totalAmountLabel.setText(String.valueOf(totalAmount));

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

    public void makeReservation(){
        switchToPane(makeReservationPane);
        headerText.setText("Make a New Reservation");
        backPane.setVisible(true);
        makeReservationView();
    }

    public void makeReservationView()
    {

    }

    // Deposit Page
    public void goToDepositPage() {
        switchToPane(depositPane);
        depositView();
    }
    public void depositView() {
    }

    // Feedback Page
    public void goToFeedbackPage() {
        headerText.setText("Make FeedBack");
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
    public void goToUpdateDataPage() {
        switchToPane(updateDataPane);
        updateDataView();
    }
    public void updateDataView() {
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