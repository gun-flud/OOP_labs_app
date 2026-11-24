package com.lab4.workspace;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class WorkspaceFactory {
    public static ScrollPane createWorkspace (Canvas canvas) {
        Group group = new Group(canvas);

        StackPane desk = new StackPane(group);
        desk.setStyle("-fx-background-color: #2b2b2b;");
//        desk.setPadding(new Insets(40));

        ScrollPane scroll = new ScrollPane(desk);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setPannable(false);

        return scroll;
    }
}
