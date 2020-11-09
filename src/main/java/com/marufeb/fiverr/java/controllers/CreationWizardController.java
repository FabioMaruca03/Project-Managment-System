package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
import com.marufeb.fiverr.kotlin.model.Project;
import com.marufeb.fiverr.kotlin.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private AnchorPane tasksView;

    @FXML
    private ListView<String> teams;

    @FXML
    private ListView<String> teams1;

    @FXML
    private Button assign;

    @FXML
    private Button unsign;


    private final List<Parent> p = new ArrayList<>();
    private int index = 0;

    @FXML
    void back(ActionEvent event) {
        load(-1);
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
        switch (index) {
            case 0: {
                if (Project.Companion.getProjects().stream().anyMatch(it -> it.getName().equals(projectName.getText())))
                    return;
                break;
            }
            case 1: {
                if (teams1.getItems().isEmpty())
                    return;
                break;
            }
            default:
                break;
        }
        load(1);
        event.consume();
    }

    @FXML
    void newTeam(MouseEvent event) {
        Launcher.newTeamWizard();
        event.consume();
    }

    private void load(int cursor) {
        if (cursor == -1 && index != 0) {
            index--;
            p.get(index).toFront();
        } else if (cursor == 1 && index != p.size() - 1) {
            index++;
            p.get(index).toFront();
        } else if (index == 0) {
            Launcher.menu();
        } else if (index == p.size()) {
            Launcher.loadAndTrack();
        }
    }

    private final ObservableList<String> prop = FXCollections.observableArrayList();
    private final ObservableList<String> prop1 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setDisable(false);
        next.setDisable(false);
        prop.addAll(Team.Companion.getTeams().stream().map(Team::getName).collect(Collectors.toSet()));
        assign.setOnAction(it -> {
            final ObservableList<String> selectedItems = teams.getSelectionModel().getSelectedItems();
            teams1.getItems().addAll(selectedItems);
            teams.getItems().removeAll(selectedItems);
            it.consume();
        });
        unsign.setOnAction(it -> {
            final ObservableList<String> selectedItems = teams1.getSelectionModel().getSelectedItems();
            teams.getItems().addAll(selectedItems);
            teams1.getItems().removeAll(selectedItems);
            it.consume();
        });

        teams.setItems(prop);
        teams1.setItems(prop1);

        p.add(startPane);
        p.add(tasksView);

        p.get(0).toFront();
    }

    private void clear() {
        projectName.clear();
        people.clear();
        deadline.clear();
    }
}

