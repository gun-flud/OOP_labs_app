package com.lab3.toolbar;

import com.lab3.docs.StudentInfo;
import com.lab3.shapes.ShapeType;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class AppToolBar {
    private final ToolBar toolBar;
    private final ToggleGroup toolGroup = new ToggleGroup();
    private final MenuButton shapesMenuButton;

    public AppToolBar(Stage stage, Consumer<ShapeType> onToolSelected) {
        toolBar = new ToolBar();
        toolBar.setPadding(new Insets(3, 5, 3, 5));

        // 1. кнопки навігації (вимикають малювання фігур)
        ToggleButton zoomInBtn = new ToggleButton();
        zoomInBtn.setToggleGroup(toolGroup);
        zoomInBtn.setGraphic(loadIcon("/icons/zoom_in.png"));
        zoomInBtn.setTooltip(new Tooltip("Збільшити"));
        zoomInBtn.setPrefSize(32, 32);
        zoomInBtn.setOnAction(e -> {
            onToolSelected.accept(null);
            stage.setTitle("Графічний редактор (Іванников В., гр. ІМ-54) | Інструмент: Збільшити (малювання вимкнено)");
        });

        ToggleButton zoomOutBtn = new ToggleButton();
        zoomOutBtn.setToggleGroup(toolGroup);
        zoomOutBtn.setGraphic(loadIcon("/icons/zoom_out.png"));
        zoomOutBtn.setTooltip(new Tooltip("Зменшити"));
        zoomOutBtn.setPrefSize(32, 32);
        zoomOutBtn.setOnAction(e -> {
            onToolSelected.accept(null);
            stage.setTitle("Графічний редактор (Іванников В., гр. ІМ-54) | Інструмент: Зменшити (малювання вимкнено)");
        });

        ToggleButton handBtn = new ToggleButton();
        handBtn.setToggleGroup(toolGroup);
        handBtn.setGraphic(loadIcon("/icons/hand.png"));
        handBtn.setTooltip(new Tooltip("Рука"));
        handBtn.setPrefSize(32, 32);
        handBtn.setOnAction(e -> {
            onToolSelected.accept(null);
            stage.setTitle("Графічний редактор (Іванников В., гр. ІМ-54) | Інструмент: Рука (малювання вимкнено)");
        });

        // 2. випадаючий список фігур з іконкою та поточною назвою збоку
        shapesMenuButton = new MenuButton(" Фігури: Крапка");
        shapesMenuButton.setGraphic(loadIcon("/icons/shapes.png"));
        shapesMenuButton.setTooltip(new Tooltip("Вибір фігури зі списку"));
        shapesMenuButton.setPrefHeight(32);

        for (ShapeType type : ShapeType.values()) {
            MenuItem item = new MenuItem(type.getUkrainianName());
            item.setGraphic(createMiniShapeIcon(type));
            item.setOnAction(e -> selectShape(type, stage, onToolSelected));
            shapesMenuButton.getItems().add(item);
        }

        // 3. кнопка довідки ?
        Button helpBtn = new Button();
        helpBtn.setGraphic(loadIcon("/icons/help.png"));
        helpBtn.setTooltip(new Tooltip("Довідка"));
        helpBtn.setPrefSize(32, 32);
        helpBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Про програму");
            alert.setHeaderText("Графічний редактор об'єктів (Lab 3)");
            alert.setContentText(StudentInfo.getAboutText());
            alert.showAndWait();
        });

        // збираємо тулбар (лупа+, лупа-, рука, фігури, ?)
        toolBar.getItems().addAll(zoomInBtn, zoomOutBtn, handBtn, new Separator(), shapesMenuButton, new Separator(), helpBtn);

        // початковий вибір крапки
        selectShape(ShapeType.POINT, stage, onToolSelected);
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    // метод вибору фігури та оновлення інтерфейсу
    public void selectShape(ShapeType type, Stage stage, Consumer<ShapeType> onToolSelected) {
        // знімаємо виділення з кнопок навігації при виборі фігури
        toolGroup.selectToggle(null);
        shapesMenuButton.setText(" Фігури: " + type.getUkrainianName());
        onToolSelected.accept(type);
        stage.setTitle("Графічний редактор (Іванников В., гр. ІМ-54, вар. 55) | Обрано: " + type.getUkrainianName());
    }

    // завантаження іконки з ресурсів
    private ImageView loadIcon(String path) {
        try {
            Image img = new Image(getClass().getResourceAsStream(path));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(18);
            iv.setFitHeight(18);
            iv.setPreserveRatio(true);
            return iv;
        } catch (Exception e) {
            return new ImageView();
        }
    }

    // міні-іконка фігури для пунктів списку
    private Canvas createMiniShapeIcon(ShapeType type) {
        Canvas c = new Canvas(16, 16);
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setLineWidth(1.4);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);

        switch (type) {
            case POINT:
                gc.fillOval(6, 6, 4, 4);
                break;
            case LINE:
                gc.strokeLine(2, 14, 14, 2);
                break;
            case RECTANGLE:
                gc.strokeRoundRect(2, 3, 12, 10, 4, 4);
                break;
            case ELLIPSE:
                gc.strokeOval(2, 3, 12, 10);
                break;
        }
        return c;
    }
}
