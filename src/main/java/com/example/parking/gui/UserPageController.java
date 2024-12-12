package com.example.parking.gui;

import com.example.parking.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

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
    private Pane homePane, reservationPane, depositPane, feedbackPane, updateDataPane,backPane;
    @FXML
    private ScrollPane spotsPane;

    @FXML
    private TableView<Feedback> FeedbackTable;

    @FXML
    private TableView<Reservation> ReservationTable;

    @FXML
    private Label totalAmountLabel;


    public void initialize() {
        owner = LoginPageController.getCurrentOwner();
        homePane.setVisible(false);
        spotsPane.setVisible(false);
        reservationPane.setVisible(false);
        depositPane.setVisible(false);
        feedbackPane.setVisible(false);
        updateDataPane.setVisible(false);
        backPane.setVisible(false);

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
        goToReservationPage();
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
        switchToPane(reservationPane);
    }
    public void goToReservationPage() {
        switchToPane(reservationPane);
        backPane.setVisible(true);
        reservationView();
    }
    public void makeReservation(){
        //MakereservationView();
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
        FeedbackView();
    }
    public void FeedbackView() {
        ArrayList<Feedback> feedbacks = SystemManager.getAllFeedBacks();
        FeedbackTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        double avgFeedbackNum = 0;
        for (Feedback feedback : feedbacks) {
            avgFeedbackNum += feedback.getRate();
        }
        if (!feedbacks.isEmpty()) {
            avgFeedbackNum /= feedbacks.size();
        }

        // to prevent duplication
        FeedbackTable.getColumns().clear();
        FeedbackTable.getItems().clear();


        TableColumn<Feedback, String> ownerIdCol = new TableColumn<>("Owner ID");
        ownerIdCol.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(param.getValue().getOwenerID())));

        TableColumn<Feedback, String> ResIdCol = new TableColumn<>("Reservation ID");
        ResIdCol.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(param.getValue().getReservationID())));

        TableColumn<Feedback, String> RatingCol = new TableColumn<>("Rate");
        RatingCol.setCellValueFactory(param ->
                new SimpleStringProperty(String.valueOf(param.getValue().getRate())));

        TableColumn<Feedback, String> CommentCol = new TableColumn<>("Feedback Message");
        CommentCol.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getFeedbackMessage()));

        FeedbackTable.getColumns().addAll(ownerIdCol, ResIdCol, RatingCol, CommentCol);
        FeedbackTable.getItems().addAll(feedbacks);
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