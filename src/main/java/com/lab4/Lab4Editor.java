package com.lab4;

import com.lab4.menu.AppMenuBar;
import com.lab4.toolbar.AppToolBar;
import com.lab4.util.ZoomHandler;
import com.lab4.workspace.WorkspaceFactory;
import com.lab4.workspace.WorkspaceView;
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

        ScrollPane workspace = WorkspaceFactory.createWorkspace(canvas);

        ZoomHandler zoomHandler = new ZoomHandler(canvas, workspace);
        zoomHandler.attachScrollEvent(workspace);

        WorkspaceView workspaceView = new WorkspaceView(workspace, canvas);

        // меню та тулбар
        AppMenuBar menuBar = new AppMenuBar(primaryStage);
        AppToolBar toolBar = new AppToolBar(
                primaryStage,
                myEditor::setTool,
                zoomHandler,
                workspaceView);

        VBox topPanel = new VBox(menuBar.getMenuBar(), toolBar.getToolBar());

//        Group canvasGroup = new Group(canvas);
//        ScrollPane scrollPane = new ScrollPane(canvasGroup);
//        scrollPane.setPannable(true);

        root.setTop(topPanel);
        root.setCenter(workspace);


        Scene scene = new Scene(root, 1000, 700);
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
