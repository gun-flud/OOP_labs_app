package com.lab4.util;

import javafx.scene.canvas.Canvas;

public class ZoomHandler {
    private final Canvas canvas;
    private double currentScale = 1.0;

    private static final double MIN_SCALE = 0.2;
    private static final double MAX_SCALE = 4.0;
    private static final double SCALE_FACTOR = 1.2;

    public double getCurrentScale() {
        return currentScale;
    }

    public ZoomHandler (Canvas canvas) {
        this.canvas = canvas;
    }

    public void zoomIn () {
        if (currentScale * SCALE_FACTOR <= MAX_SCALE) {
            currentScale *= SCALE_FACTOR;
            applyZoom();
        }
    }

    public void zoomOut () {
        if (currentScale * SCALE_FACTOR >= MIN_SCALE) {
            currentScale *= SCALE_FACTOR;
            applyZoom();
        }
    }

    private void applyZoom () {
        canvas.setScaleX(currentScale);
        canvas.setScaleY(currentScale);
    }

    public void resetZoom() {
        currentScale = 1.0;
        applyZoom();
    }
}
