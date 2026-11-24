package com.lab4.util;

import javafx.scene.canvas.Canvas;

public class ZoomHandlerFactory {
    public static ZoomHandler createZoomHandler (Canvas canvas) {
        return new ZoomHandler(canvas);
    }
}
