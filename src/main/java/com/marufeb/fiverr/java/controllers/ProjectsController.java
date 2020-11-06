package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
import com.marufeb.fiverr.kotlin.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ProjectsController implements Initializable {
    private final ObservableList<String> projectsList = FXCollections.observableList(new ArrayList<>());
    private final Logger logger = Logger.getLogger("GLOBAL_LOGGER");

    @FXML
    private ListView<String> projects;

    @FXML
    void remove(ActionEvent event) {
        List<String> selected = projects.getSelectionModel().getSelectedItems();
        projectsList.removeAll(selected);
        selected.forEach(it -> logger.info("Deleted project: " + it));
        event.consume();
    }

    @FXML
    void save(ActionEvent event) {
        Project.Companion.getProjects().removeIf(it -> !projectsList.contains(it.getName()));
        Launcher.loader.save();
        Launcher.menu(); // todo: is this fine?
        event.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectsList.addAll(Project.Companion.getProjects().stream().map(Project::getName).collect(Collectors.toSet()));
        projects.setItems(projectsList);
    }
}
