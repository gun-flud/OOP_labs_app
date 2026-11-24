package com.lab4.shapes;

public enum ShapeType {
    POINT("Крапка"),
    LINE("Лінія"),
    RECTANGLE("Прямокутник"),
    ELLIPSE("Еліпс"),
    CUBE("Куб"),
    LINE_WITH_CIRCLES("Лінія з кружечками");


    private final String ukrainianName;

    ShapeType(String ukrainianName) {
        this.ukrainianName = ukrainianName;
    }

    public String getUkrainianName() {
        return ukrainianName;
    }
}
