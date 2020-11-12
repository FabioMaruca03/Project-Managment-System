package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
import com.marufeb.fiverr.kotlin.data.LogicKt;
import com.marufeb.fiverr.kotlin.data.RefinedTask;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TrackController implements Initializable {
    @FXML
    private ListView<RefinedTask> tasks;

    @FXML
    private ProgressBar progress;

    @FXML
    private Text end;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final List<RefinedTask> total =
                LogicKt.getCriticalPath(Launcher.opened)
                        .stream()
                        .filter(it -> !it.getTask().getDone())
                        .collect(Collectors.toList());
        tasks.setItems(FXCollections.observableArrayList(total));

        progress.setProgress(LogicKt.getCriticalPath(Launcher.opened)
                .stream()
                .filter(it -> it.getTask().getDone())
                .count() / (float) Launcher.opened.getTasks().size()); // % calculation
        end.setText(LogicKt.getEnd(total).toString()); // Set current end date
        tasks.setCellFactory(param -> new TaskCell()); // Set cell factory
    }

    @FXML
    public void back(ActionEvent event) {
        Launcher.menu();
        event.consume();
    }

}
