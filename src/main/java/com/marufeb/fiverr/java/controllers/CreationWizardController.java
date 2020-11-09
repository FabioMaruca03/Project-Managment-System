package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
import com.marufeb.fiverr.kotlin.model.Project;
import com.marufeb.fiverr.kotlin.model.Task;
import com.marufeb.fiverr.kotlin.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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

    @FXML
    private AnchorPane teamsView;

    @FXML
    private TextField taskName;

    @FXML
    private TextArea taskDescription;

    @FXML
    private ListView<String> tasks;

    @FXML
    private TextField taskDuration;

    @FXML
    private ListView<String> dependencies;

    @FXML
    void addAsDependency(ActionEvent event) {
        event.consume();
    }

    private final List<Task> addedTasks = new ArrayList<>();

    @FXML
    void addTask(ActionEvent event) {
        if (!taskName.getText().isBlank() && !tasksList.contains(taskName.getText()))
            if (!taskDuration.getText().isBlank()) {
                Task t = new Task(
                        taskName.getText(),
                        taskDescription.getText(),
                        Integer.parseInt(taskDuration.getText()),
                        Team.Companion.findTeamByLeader(Launcher.user.getEmail()),
                        null
                );
                addedTasks.add(t);
                tasksList.add(t.getName());
            }
        event.consume();
    }

    @FXML
    void removeDependency(ActionEvent event) {
        dependencies.getItems().removeAll(dependencies.getSelectionModel().getSelectedItems());
        event.consume();
    }

    @FXML
    void removeTask(MouseEvent event) {
        tasks.getItems().removeAll(tasks.getSelectionModel().getSelectedItems());
        event.consume();
    }


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
//            case 1: {
//                if (teams1.getItems().isEmpty())
//                    return;
//                break;
//            }
            default:
                break;
        }
        load(1);
        event.consume();
    }

    @FXML
    void newTeamWizard(MouseEvent event) {
        Launcher.newTeamWizard();
        prop.addAll(Team.Companion.getTeams().stream().filter(it -> it.getLeader().getEmail().equals(Launcher.user.getEmail())).map(Team::getName).collect(Collectors.toSet()));
        prop.removeAll(prop1);
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
            if (!tasksList.isEmpty())
                Launcher.loadAndTrack();
        }
    }

    private final ObservableList<String> prop = FXCollections.observableArrayList();
    private final ObservableList<String> prop1 = FXCollections.observableArrayList();
    private final ObservableList<String> tasksList = FXCollections.observableArrayList();
    private final ObservableList<String> dependenciesList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setDisable(false);
        next.setDisable(false);

        prop.addAll(Team.Companion.getTeams().stream().filter(it -> it.getLeader().getEmail().equals(Launcher.user.getEmail())).map(Team::getName).collect(Collectors.toSet()));

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

        taskDuration.textProperty().addListener((bo, o, n) -> {
            if (n.chars().anyMatch(it -> !Character.isDigit(it))) {
                taskDuration.setText(o);
            }
        });

        teams.setItems(prop);
        teams1.setItems(prop1);

        tasks.setItems(tasksList);
        dependencies.setItems(dependenciesList);

        p.add(0, startPane);
        p.add(1, tasksView);
        p.add(2, teamsView);

        p.get(0).toFront();
    }

    private void clear() {
        projectName.clear();
        people.clear();
        deadline.clear();
    }
}

