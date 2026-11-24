package com.lab4;

import com.lab4.shapes.Shape;
import com.lab4.shapes.ShapeType;
import com.lab4.shapes.shapes_type.EllipseShape;
import com.lab4.shapes.shapes_type.LineShape;
import com.lab4.shapes.shapes_type.PointShape;
import com.lab4.shapes.shapes_type.RectangleShape;
import com.lab4.shapes.shapes_type.CubeShape;
import com.lab4.shapes.shapes_type.LineWithCirclesShape;

import com.lab4.util.AppConstants;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MyEditor {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Shape[] pcshape = new Shape[AppConstants.ARRAY_SIZE];
    private int shapeCount = 0;

    // якщо null — малювання вимкнено (наприклад, обрано лупу чи руку)
    private ShapeType currentShapeType = ShapeType.POINT;
    private int startX, startY;

    public MyEditor(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        clearCanvas();
        mouseHandlers();
    }

    public void mouseHandlers () {
        // обробка подій миші + гумовий слід
        canvas.setOnMousePressed((MouseEvent e) -> {
            if (currentShapeType == null) return;

            startX = (int) e.getX();
            startY = (int) e.getY();
        });

        canvas.setOnMouseDragged((MouseEvent e) -> {
            if (currentShapeType == null) return;

            // клір полотна і перемальовуємо перед новим гумовим слідом
            clearCanvas();
            redrawAll();

            int currentX = (int) e.getX();
            int currentY = (int) e.getY();

            // синій штриховий гумовий слід (Ж mod 4 = 3)
            gc.setStroke(Color.BLUE);
            gc.setLineWidth(1);
            gc.setLineDashes(5, 5);

            switch (currentShapeType) {
                case LINE:
                    gc.strokeLine(startX, startY, currentX, currentY);
                    break;
                case RECTANGLE:
                    double rx = Math.min(startX, currentX);
                    double ry = Math.min(startY, currentY);
                    gc.strokeRoundRect(rx, ry, Math.abs(currentX - startX), Math.abs(currentY - startY), 20, 20);
                    break;
                case ELLIPSE:
                    double ex = Math.min(startX, currentX);
                    double ey = Math.min(startY, currentY);
                    gc.strokeOval(ex, ey, Math.abs(currentX - startX), Math.abs(currentY - startY));
                    break;
                case CUBE:
                    double minX = Math.min(startX, currentX);
                    double minY = Math.min(startY, currentY);
                    double maxX = Math.max(startX, currentX);
                    double maxY = Math.max(startY, currentY);

                    double offX = (maxX - minX) / 3;
                    double offY = (maxY - minY) / 3;

                    gc.strokeRoundRect(minX, minY + offY, (maxX - minX) - offX, (maxY - minY) - offY, 20, 20);
                    gc.strokeRoundRect(minX + offX, minY, (maxX - minX) - offX, (maxY - minY) - offY, 20, 20);

                    gc.strokeLine(minX + 3, minY + offY + 3, minX + offX + 3, minY + 3);
                    gc.strokeLine(maxX - offX - 3, minY + offY + 3, maxX - 3, minY + 3);
                    gc.strokeLine(minX + 3, maxY - 3, minX + offX + 3, maxY - offY - 3);
                    gc.strokeLine(maxX - offX - 3, maxY - 3, maxX - 3, maxY - offY - 3);
                    break;
                case LINE_WITH_CIRCLES:
                    gc.strokeLine(startX, startY, currentX, currentY);
                    int r = 6;
                    gc.strokeOval(startX - r, startY - r, r * 2, r * 2);
                    gc.strokeOval(currentX - r, currentY - r, r * 2, r * 2);
                    break;

                default:
                    break;
            }

            gc.setLineDashes(null);
        });

        canvas.setOnMouseReleased((MouseEvent e) -> {
            if (currentShapeType == null) return;

            int endX = (int) e.getX();
            int endY = (int) e.getY();

            if (shapeCount < AppConstants.ARRAY_SIZE) {
                switch (currentShapeType) {
                    case POINT:
                        pcshape[shapeCount++] = new PointShape(startX, startY, endX, endY);
                        break;
                    case LINE:
                        pcshape[shapeCount++] = new LineShape(startX, startY, endX, endY);
                        break;
                    case RECTANGLE:
                        pcshape[shapeCount++] = new RectangleShape(startX, startY, endX, endY);
                        break;
                    case ELLIPSE:
                        pcshape[shapeCount++] = new EllipseShape(startX, startY, endX, endY);
                        break;
                    case CUBE:
                        pcshape[shapeCount++] = new CubeShape(startX, startY, endX, endY);
                        break;
                    case LINE_WITH_CIRCLES:
                        pcshape[shapeCount++] = new LineWithCirclesShape(startX, startY, endX, endY);
                        break;
                }
            }

            // очищуємо і малюємо все знову
            clearCanvas();
            redrawAll();
        });
    }

    // метод встановлення інструменту або вимкнення малювання
    public void setTool (ShapeType type) {
        this.currentShapeType = type;
    }

    public void destroy () {
        canvas.setOnMousePressed(null);
        canvas.setOnMouseDragged(null);
        canvas.setOnMouseReleased(null);

        for (int i = 0; i < shapeCount; i++) {
            pcshape[i] = null;
        }

        shapeCount = 0;
    }

    private void clearCanvas() {
        gc.setLineDashes(null);
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void redrawAll() {
        gc.setLineDashes(null);
        for (int i = 0; i < shapeCount; i++) {
            if (pcshape[i] != null) {
                pcshape[i].draw(gc);
            }
        }
    }
}

