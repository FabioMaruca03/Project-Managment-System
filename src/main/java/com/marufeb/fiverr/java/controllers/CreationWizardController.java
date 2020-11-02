package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreationWizardController implements Initializable {

    @FXML
    private AnchorPane startPane;

    @FXML
    private Button back;

    @FXML
    private Button next;

    @FXML
    private TextField projectName;

    @FXML
    private TextField people;

    @FXML
    private TextField deadline;

    @FXML
    private TextField clients;

    private final IntegerProperty PANE = new SimpleIntegerProperty(0);

    private final int MAX_PANE = 2;

    @FXML
    void back(ActionEvent event) {
        PANE.setValue(PANE.getValue() - 1);
        event.consume();
    }

    @FXML
    void cancel(ActionEvent event) {
        Launcher.menu();
        event.consume();
    }

    @FXML
    void next(ActionEvent event) {
        PANE.setValue(PANE.getValue() + 1);
        event.consume();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PANE.addListener((ob, newV, oldV) -> {
            if (newV.intValue() < 0)
                PANE.setValue(0);
            else if (newV.intValue() > MAX_PANE)
                PANE.setValue(MAX_PANE);
            back.setDisable(newV.intValue() == 0);
            next.setDisable(newV.intValue() == MAX_PANE);
        });
    }
}

