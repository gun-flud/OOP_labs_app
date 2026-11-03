package com.lab2.shapes.shapes_type;

import com.lab2.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleShape extends Shape {
    public RectangleShape(int startX, int startY, int endX, int endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);


        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeRect(x, y, width, height);
    }
}