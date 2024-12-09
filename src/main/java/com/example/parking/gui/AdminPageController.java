package com.example.parking.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class AdminPageController {

    @FXML
    private Pane homeStack;

    @FXML
    private Pane SpotStack;

    @FXML
    private void showHomePane() {
        homeStack.setVisible(true);
        SpotStack.setVisible(false);
    }

    @FXML
    private void showSpotsPane() {
        homeStack.setVisible(false);
        SpotStack.setVisible(true);
    }
}
