package com.lab4;

import com.lab4.menu.AppMenuBar;
import com.lab4.toolbar.AppToolBar;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Lab4Editor extends Application {
    private MyEditor myEditor;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(800, 500);

        this.myEditor = new MyEditor(canvas);

        // меню та тулбар
        AppMenuBar menuBar = new AppMenuBar(primaryStage);
        AppToolBar toolBar = new AppToolBar(primaryStage, myEditor::setTool, canvas);

        VBox topPanel = new VBox(menuBar.getMenuBar(), toolBar.getToolBar());

        Group canvasGroup = new Group(canvas);
        ScrollPane scrollPane = new ScrollPane(canvasGroup);
        scrollPane.setPannable(true);

        root.setTop(topPanel);
        root.setCenter(scrollPane);


        Scene scene = new Scene(root, 800, 560);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        if (myEditor != null) {
            myEditor.destroy();
            myEditor = null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
