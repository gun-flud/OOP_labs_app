package com.lab2.shapes.shapes_type;

import com.lab2.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PointShape extends Shape {
    public PointShape(int startX, int startY, int endX, int endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(startX - 2, startY - 2, 4, 4);
    }
}