package com.marufeb.fiverr.java.models;

import com.marufeb.fiverr.java.Launcher;
import com.marufeb.fiverr.java.controllers.MenuController;

public class MenuModel {
    private static final MenuModel instance = new MenuModel();
    private MenuController controller;

    private MenuModel() {
    }

    public void setController(MenuController model) {
        controller = model;
    }

    public static MenuModel getInstance() {
        return instance;
    }

    public void createProject() {
        Launcher.create();
    }

    public void loadProject() {
        Launcher.load();
    }

    public void trackProgress() {
        Launcher.loadAndTrack();
    }
}
