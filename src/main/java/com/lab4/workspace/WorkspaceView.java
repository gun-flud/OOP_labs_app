package com.lab4.workspace;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;

public class WorkspaceView {
    private final ScrollPane scrollPane;
    private final Canvas canvas;

    public WorkspaceView(ScrollPane scrollPane, Canvas canvas) {
        this.scrollPane = scrollPane;
        this.canvas = canvas;
    }

    public void enableHandTool () {
        scrollPane.setPannable(true);
        canvas.setCursor(Cursor.OPEN_HAND);
    }

    public void enableDrawingTool () {
        scrollPane.setPannable(false);
        canvas.setCursor(Cursor.CROSSHAIR);
    }

}
