package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
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


    @FXML
    void back(ActionEvent event) {
        Launcher.menu();
        event.consume();
    }

    @FXML
    void cancel(ActionEvent event) {
        Launcher.menu();
        clear();
        event.consume();
    }

    @FXML
    void next(ActionEvent event) {

        event.consume();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setDisable(false);
        next.setDisable(false);

    }

    private void clear() {
        projectName.clear();
        people.clear();
        deadline.clear();
        clients.clear();
    }
}

