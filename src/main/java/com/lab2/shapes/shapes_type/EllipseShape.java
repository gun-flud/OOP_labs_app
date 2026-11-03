package com.lab2.shapes.shapes_type;

import com.lab2.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EllipseShape extends Shape {
    public EllipseShape(int startX, int startY, int endX, int endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    public void draw(GraphicsContext gc) {
        // від центру до кута
        double rx = Math.abs(endX - startX);
        double ry = Math.abs(endY - startY);
        double x = startX - rx;
        double y = startY - ry;
        double width = rx * 2;
        double height = ry * 2;

        // сіре, чорний контур
        gc.setFill(Color.GRAY);
        gc.fillOval(x, y, width, height);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeOval(x, y, width, height);
    }
}