package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.java.Launcher;
import com.marufeb.fiverr.kotlin.model.Team;
import com.marufeb.fiverr.kotlin.model.User;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TeamsWizardController implements Initializable {
    private final Logger logger = Logger.getLogger("GLOBAL_LOGGER");

    private final ObservableList<String> users = new SimpleListProperty<>();
    private final ObservableList<String> team = new SimpleListProperty<>();

    @FXML
    private ListView<String> viableUsers;

    @FXML
    private ListView<String> myTeam;

    @FXML
    void add(ActionEvent event) {
        List<String> selected = viableUsers.getSelectionModel().getSelectedItems();
        users.removeAll(selected);
        team.addAll(selected);
        selected.forEach(it -> logger.info("Added: " + it + " at "));
        event.consume();
    }

    @FXML
    void cancel(ActionEvent event) {
        Launcher.menu();
        event.consume();
    }

    @FXML
    void rem(ActionEvent event) {
        List<String> selected = myTeam.getSelectionModel().getSelectedItems();
        team.removeAll(selected);
        users.addAll(selected);
        selected.forEach(it -> logger.info("Removed: " + it + " from "));
        event.consume();
    }

    @FXML
    void save(ActionEvent event) {
        Team.Companion.getTeams().removeIf(it -> !team.contains(it.getName()));
        event.consume();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        users.addAll(User.Companion.getUsers().stream().map(User::getEmail).collect(Collectors.toSet()));
        Team team = Team.Companion.findTeamByLeader(Launcher.user.getEmail());
        if (team != null) { // todo: implement new team naming
            Set<String> users = team.getUsers().stream().map(User::getEmail).collect(Collectors.toSet());
            this.team.addAll(users);
            this.users.removeAll(users);
        }

        viableUsers.setItems(users);
        myTeam.setItems(this.team);
    }
}
