package com.lab2.shapes;

public enum ShapeType {
    POINT("Крапка"),
    LINE("Лінія"),
    RECTANGLE("Прямокутник"),
    ELLIPSE("Еліпс");

    private final String ukrainianName;

    ShapeType(String ukrainianName) {
        this.ukrainianName = ukrainianName;
    }

    public String getUkrainianName() {
        return ukrainianName;
    }
}