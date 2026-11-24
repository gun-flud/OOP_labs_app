package com.lab3;

import com.lab3.menu.AppMenuBar;
import com.lab3.shapes.Shape;
import com.lab3.shapes.ShapeType;
import com.lab3.shapes.shapes_type.*;
import com.lab3.toolbar.AppToolBar;
import com.lab3.util.AppConstants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Lab3Editor extends Application {
    private final Shape[] pcshape = new Shape[AppConstants.ARRAY_SIZE];
    private int shapeCount = 0;

    // якщо null — малювання вимкнено (наприклад, обрано лупу чи руку)
    private ShapeType currentShapeType = ShapeType.POINT;
    private int startX, startY;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(800, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // очищення полотна
        clearCanvas(gc, canvas);

        // меню та тулбар
        AppMenuBar menuBar = new AppMenuBar(primaryStage);
        AppToolBar toolBar = new AppToolBar(primaryStage, this::setTool);

        VBox topPanel = new VBox(menuBar.getMenuBar(), toolBar.getToolBar());
        root.setTop(topPanel);
        root.setCenter(canvas);

        // обробка подій миші + гумовий слід
        canvas.setOnMousePressed((MouseEvent e) -> {
            // якщо не обрано фігуру — малювання не відбувається
            if (currentShapeType == null) return;

            startX = (int) e.getX();
            startY = (int) e.getY();
        });

        canvas.setOnMouseDragged((MouseEvent e) -> {
            // якщо не обрано фігуру — малювання не відбувається
            if (currentShapeType == null) return;

            // клір полотна і перемальовуємо фігури перед новим гумовим слідом
            clearCanvas(gc, canvas);
            redrawAll(gc);

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
                default:
                    break;
            }

            gc.setLineDashes(null);
        });

        canvas.setOnMouseReleased((MouseEvent e) -> {
            // якщо не обрано фігуру — малювання не відбувається
            if (currentShapeType == null) return;

            int endX = (int) e.getX();
            int endY = (int) e.getY();

            if (shapeCount < AppConstants.ARRAY_SIZE) {
                // створення об'єкта залежно від обраного типу
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

        Scene scene = new Scene(root, 800, 560);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // метод встановлення інструменту або вимкнення малювання
    private void setTool(ShapeType type) {
        this.currentShapeType = type;
    }

    private void clearCanvas(GraphicsContext gc, Canvas canvas) {
        gc.setLineDashes(null);
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void redrawAll(GraphicsContext gc) {
        gc.setLineDashes(null);
        for (int i = 0; i < shapeCount; i++) {
            if (pcshape[i] != null) {
                pcshape[i].draw(gc);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
