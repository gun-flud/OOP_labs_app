package com.lab2.shapes;

import javafx.scene.canvas.GraphicsContext;

public abstract class Shape {
    protected int startX, startY, endX, endY;

    public Shape(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }


    public abstract void draw(GraphicsContext gc);
}