package com.lab2;

import com.lab2.menu.AppMenuBar;
import com.lab2.shapes.Shape;
import com.lab2.shapes.ShapeType;
import com.lab2.shapes.shapes_type.*;
import com.lab2.util.AppConstants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Lab2Editor extends Application {
    private final Shape[] pcshape = new Shape[AppConstants.ARRAY_SIZE];
    private int shapeCount = 0;

    private ShapeType currentShapeType = ShapeType.POINT;
    private int startX, startY;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Лабораторна робота №2 - Графічний редактор (Іванников Владислав, ІМ-54, Варіант 54)");

        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(800, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // очищення полотна
        clearCanvas(gc, canvas);

        // меню
        MenuBar menuBar = AppMenuBar.createMenu(primaryStage, newShapeType -> {
            currentShapeType = newShapeType;
        });

        root.setTop(menuBar);
        root.setCenter(canvas);

        // обробка подій миші + гумовй слід
        canvas.setOnMousePressed((MouseEvent e) -> {
            startX = (int) e.getX();
            startY = (int) e.getY();
        });

        canvas.setOnMouseDragged((MouseEvent e) -> {
            // клір полотна і перемальовуємо фігури перед новим гумовим слідом
            clearCanvas(gc, canvas);
            redrawAll(gc);

            int currentX = (int) e.getX();
            int currentY = (int) e.getY();

            // колір слід
            gc.setStroke(Color.BLUE);
            gc.setLineWidth(1);

            switch (currentShapeType) {
                case LINE:
                    gc.strokeLine(startX, startY, currentX, currentY);
                    break;
                case RECTANGLE:
                    double x = Math.min(startX, currentX);
                    double y = Math.min(startY, currentY);
                    gc.strokeRect(x, y, Math.abs(currentX - startX), Math.abs(currentY - startY));
                    break;
                case ELLIPSE:
                    double rx = Math.abs(currentX - startX);
                    double ry = Math.abs(currentY - startY);
                    gc.strokeOval(startX - rx, startY - ry, rx * 2, ry * 2);
                    break;
                default:
                    break;
            }
        });

        canvas.setOnMouseReleased((MouseEvent e) -> {
            int endX = (int) e.getX();
            int endY = (int) e.getY();

            if (shapeCount < AppConstants.ARRAY_SIZE) {
                // Створення об'єкта залежно від обраного типу (поліморфізм)
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
                }
            }

            // очищуємо і малюємо все ззаново
            clearCanvas(gc, canvas);
            redrawAll(gc);
        });

        Scene scene = new Scene(root, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearCanvas(GraphicsContext gc, Canvas canvas) {
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void redrawAll(GraphicsContext gc) {
        for (int i = 0; i < shapeCount; i++) {
            if (pcshape[i] != null) {
                // виклик draw для кожної фігури
                pcshape[i].draw(gc);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}