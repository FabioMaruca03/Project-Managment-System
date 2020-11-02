package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
import com.marufeb.fiverr.java.models.MenuModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MenuController {
    private final MenuModel model;

    public MenuController() {
        model = MenuModel.getInstance();
        model.setController(this);
    }

    @FXML
    void close(MouseEvent event) {
        Launcher.stage.close();
        event.consume();
    }

    @FXML
    void createProject(ActionEvent event) {
        model.createProject();
        event.consume();
    }

    @FXML
    void loadProject(ActionEvent event) {
        model.loadProject();
        event.consume();
    }

    @FXML
    void trackProgress(ActionEvent event) {
        model.trackProgress();
        event.consume();
    }

}
