package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
import com.marufeb.fiverr.kotlin.data.LogicKt;
import com.marufeb.fiverr.kotlin.data.RefinedTask;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class TrackController implements Initializable {
    @FXML
    private ListView<RefinedTask> tasks;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tasks.setItems(FXCollections.observableArrayList(LogicKt.getCriticalPath(Launcher.opened)));
        tasks.setCellFactory(param -> new Cell());
    }

    private class Cell extends ListCell<RefinedTask> {
        @Override
        protected void updateItem(RefinedTask item, boolean empty) {
            super.updateItem(item, empty);
            setText(item.getTask().getName());
        }
    }

}
