package com.marufeb.fiverr.java.controllers;

import com.marufeb.fiverr.kotlin.data.RefinedTask;
import com.marufeb.fiverr.kotlin.model.Task;
import javafx.scene.control.ListCell;

// Helper class
public class TaskCell extends ListCell<RefinedTask> {
    @Override
    protected void updateItem(RefinedTask item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty)
            this.setText(item.getTask().getName());
    }

    public static class NormalTaskCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty)
                this.setText(item.getName());
        }
    }
}