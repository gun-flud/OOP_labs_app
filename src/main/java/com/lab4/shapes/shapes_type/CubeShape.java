package com.lab4.shapes.shapes_type;

import com.lab4.shapes.Shape;
import javafx.scene.canvas.GraphicsContext;

public class CubeShape extends Shape {
    private final RectangleShape frontRect;
    private final RectangleShape backRect;
    private final LineShape[] edges = new LineShape[4];

    private static final int C = 3;

    public CubeShape(int startX, int startY, int endX, int endY) {
        super(startX, startY, endX, endY);
        int minX = Math.min(startX, endX);
        int minY = Math.min(startY, endY);
        int maxX = Math.max(startX, endX);
        int maxY = Math.max(startY, endY);

        int offsetX = (maxX - minX) / 3;
        int offsetY = (maxY - minY) / 3;

        this.frontRect = new RectangleShape(minX, minY + offsetY, maxX - offsetX, maxY);
        this.backRect = new RectangleShape(minX + offsetX, minY, maxX, maxY - offsetY);

        edges[0] = new LineShape(minX + C, minY + offsetY + C, minX + offsetX + C, minY + C);

        edges[1] = new LineShape(maxX - offsetX - C, minY + offsetY + C, maxX - C, minY + C);

        edges[2] = new LineShape(minX + C, maxY - C, minX + offsetX + C, maxY - offsetY - C);

        edges[3] = new LineShape(maxX - offsetX - C, maxY - C, maxX - C, maxY - offsetY - C);
    }

    @Override
    public void draw (GraphicsContext gc) {
        frontRect.draw(gc);
        backRect.draw(gc);

        for (LineShape edge: edges) {
            edge.draw(gc);
        }
    }
}
