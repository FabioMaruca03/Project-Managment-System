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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private TextField startTime;

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
        final ObservableList<String> selectedItems = tasks.getSelectionModel().getSelectedItems().filtered(it -> !dependencies.getItems().contains(it));
        dependencies.getItems().addAll(selectedItems);
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
                System.out.println(addedTasks.stream().filter(it -> dependencies.getItems().contains(it.getName())).map(Task::getId).collect(Collectors.toList()));
                t.getDependencies().addAll(addedTasks.stream().filter(it -> dependencies.getItems().contains(it.getName())).map(Task::getId).collect(Collectors.toList()));
                addedTasks.add(t);
                tasksList.add(t.getName());
            }

        clearTask();

        event.consume();
    }

    void clearTask() {
        taskName.clear();
        taskDuration.clear();
        taskDescription.clear();
        dependencies.getItems().clear();
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

    Date finalStartDate;
    @FXML
    void next(ActionEvent event) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        switch (index) {
            case 0: {
                if (projectName.getText().isBlank())
                    return;
                try {
                    if (startTime.getText().isBlank())
                        return;
                    else {
                        finalStartDate = format.parse(startTime.getText());
                    }
                } catch (Exception e) {
                    return;
                }
                if (Project.Companion.getProjects().stream().anyMatch(it -> it.getName().equals(projectName.getText())))
                    return;
                break;
            }
            case 1: {
                if (teams1.getItems().isEmpty())
                    return;
                break;
            }
            case 2: {
                if (tasks.getItems().isEmpty())
                    return;
                else {
                    Launcher.opened = new Project(
                            projectName.getText(),
                            finalStartDate,
                            addedTasks,
                            Launcher.user,
                            teams1.getItems().stream().map(it -> Team.Companion.findTeamByName(it)).collect(Collectors.toList())
                    );
                    addedTasks.forEach(it -> it.setProjectReference(Launcher.opened));
                    Launcher.loader.saveTasks();
                    Launcher.loader.saveProjects();
                }
                break;
            }
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
//    private Task currentTask = null;

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

//        tasks.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c -> {
//            c.next();
//            String s = c.getList().get(0);
//            addedTasks.stream().filter(it -> it.getName().equals(s)).forEach(it -> {
//                taskName.setText(it.getName());
//                taskDescription.setText(it.getDescription());
//                taskDuration.setText(String.valueOf(it.getDuration()));
//                dependencies.setItems(FXCollections.observableArrayList(it.getDependencies().stream().map(d -> Task.Companion.findTaskByUUID(d.toString())).map(d -> d.getName()).collect(Collectors.toList())));
//            });
//
//        });

        p.add(0, startPane);
        p.add(1, tasksView);
        p.add(2, teamsView);

        p.get(0).toFront();
    }

    private void clear() {
        projectName.clear();
        startTime.clear();
    }
}

