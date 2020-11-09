package com.marufeb.fiverr.java;

import com.marufeb.fiverr.kotlin.data.Loader;
import com.marufeb.fiverr.kotlin.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher extends Application {
    public static Stage stage;
    public static User user;
    public static Logger logger = Logger.getLogger("GLOBAL_LOGGER");
    public static Loader loader = new Loader();

    public static void main(String[] args) {
        logger.setLevel(Level.ALL);
        logger.info("Loading [STARTED]");
        loader.load();
        logger.info("Loading [ENDED]");
        launch(args);
    }

    private static Parent loadFromFXML(String name) {
        Parent p = null;
        try {
            p = new FXMLLoader(Launcher.class.getResource("/" + name + ".fxml")).load();
            logger.info("Loaded fxml: " + name);
        } catch (IOException e) {
            logger.warning("Cannot load fxml: " + name);
            e.printStackTrace();
            System.out.println("File (FXML): " + name + ".fxml does not exist.");
            System.exit(1);
        }
        return p;
    }

    public static void menu() {
        stage.setScene(new Scene(loadFromFXML("menu")));
        stage.show();
    }

    public static void create() {
        stage.setScene(new Scene(loadFromFXML("creationWizard")));
        stage.show();
    }

    public static void loadAndTrack() {

    }

    public static void load() {
        stage.setScene(new Scene(loadFromFXML("projects")));
        stage.show();
    }

    public static void login() {
        stage.setScene(new Scene(loadFromFXML("login")));
        stage.show();
    }

    public static void newTeamWizard() {
        Stage wizard = new Stage();
        wizard.setScene(new Scene(loadFromFXML("teamsWizard")));
        wizard.setTitle("Teams wizard");
        wizard.show();
    }

    @Override
    public void start(Stage stage) {
        Launcher.stage = stage;
        login();
    }
}
