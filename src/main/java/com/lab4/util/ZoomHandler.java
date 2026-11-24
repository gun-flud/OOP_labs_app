package com.lab4.util;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

public class ZoomHandler {
    private final Canvas canvas;
    private final ScrollPane scrollPane;
    private double currentScale = 1.0;

    private static final double MIN_SCALE = 0.2;
    private static final double MAX_SCALE = 4.0;
    private static final double SCALE_FACTOR = 1.2;

    public double getCurrentScale() {
        return currentScale;
    }

    public ZoomHandler (Canvas canvas, ScrollPane scrollPane) {
        this.canvas = canvas;
        this.scrollPane = scrollPane;
    }

    public void zoomIn () {
        if (currentScale * SCALE_FACTOR <= MAX_SCALE) {
            currentScale *= SCALE_FACTOR;
            applyZoom();
        }
    }

    public void zoomOut () {
        if (currentScale / SCALE_FACTOR >= MIN_SCALE) {
            currentScale /= SCALE_FACTOR;
            applyZoom();
        }
    }

    private void applyZoom () {
        canvas.setScaleX(currentScale);
        canvas.setScaleY(currentScale);

        if (scrollPane != null) {
            Platform.runLater(() -> {
                scrollPane.setHvalue(0.5); // 50% по горизонталі
                scrollPane.setVvalue(0.5); // 50% по вертикалі
            });
        }
    }

    public void resetZoom() {
        currentScale = 1.0;
        applyZoom();
    }

    public void attachScrollEvent(Node target) {
        target.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else {
                zoomOut();
            }

            event.consume();
        });
    }
}


