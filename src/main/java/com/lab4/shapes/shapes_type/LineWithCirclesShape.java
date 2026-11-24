package com.lab4.shapes.shapes_type;

import com.lab4.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;

public class LineWithCirclesShape extends Shape {
    private final LineShape line;
    private final EllipseShape startCircle;
    private final EllipseShape endCircle;

    private static final int RADIUS = 6;

    public LineWithCirclesShape(int startX, int startY, int endX, int endY) {
        super(startX, startY, endX, endY);
        this.line = new LineShape(startX, startY, endX, endY);
        this.startCircle = new EllipseShape(startX - RADIUS, startY - RADIUS, startX + RADIUS, startY + RADIUS);
        this.endCircle = new EllipseShape(endX - RADIUS, endY - RADIUS, endX + RADIUS, endY + RADIUS);
    }

    @Override
    public void draw(GraphicsContext gc) {
        line.draw(gc);
        startCircle.draw(gc);
        endCircle.draw(gc);
    }
}
