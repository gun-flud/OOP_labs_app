package com.lab2.menu;

import com.lab2.docs.StudentInfo;
import com.lab2.shapes.ShapeType;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class AppMenuBar {


    public static MenuBar createMenu(Stage stage, Consumer<ShapeType> onShapeTypeChanged) {
        MenuBar menuBar = new MenuBar();

        // файл
        Menu menuFile = new Menu("Файл");
        MenuItem exitItem = new MenuItem("Вихід");
        exitItem.setOnAction(e -> stage.close());
        menuFile.getItems().add(exitItem);

        // об’єкти
        Menu menuObjects = new Menu("Об’єкти");
        RadioMenuItem itemPoint = new RadioMenuItem(ShapeType.POINT.getUkrainianName());
        RadioMenuItem itemLine = new RadioMenuItem(ShapeType.LINE.getUkrainianName());
        RadioMenuItem itemRect = new RadioMenuItem(ShapeType.RECTANGLE.getUkrainianName());
        RadioMenuItem itemEllipse = new RadioMenuItem(ShapeType.ELLIPSE.getUkrainianName());

        ToggleGroup group = new ToggleGroup();
        itemPoint.setToggleGroup(group);
        itemLine.setToggleGroup(group);
        itemRect.setToggleGroup(group);
        itemEllipse.setToggleGroup(group);


        itemPoint.setSelected(true);


        itemPoint.setOnAction(e -> {
            onShapeTypeChanged.accept(ShapeType.POINT);
            stage.setTitle("Графічний редактор | Обрано: Крапка (Іванников В., гр. ІМ-54)");
        });
        itemLine.setOnAction(e -> {
            onShapeTypeChanged.accept(ShapeType.LINE);
            stage.setTitle("Графічний редактор (Іванников В., гр. ІМ-54) | Обрано: Лінія");
        });
        itemRect.setOnAction(e -> {
            onShapeTypeChanged.accept(ShapeType.RECTANGLE);
            stage.setTitle("Графічний редактор (Іванников В., гр. ІМ-54) | Обрано: Прямокутник");
        });
        itemEllipse.setOnAction(e -> {
            onShapeTypeChanged.accept(ShapeType.ELLIPSE);
            stage.setTitle("Графічний редактор (Іванников В., гр. ІМ-54)  | Обрано: Еліпс");
        });

        menuObjects.getItems().addAll(itemPoint, itemLine, itemRect, itemEllipse);

        // довідка
        Menu menuHelp = new Menu("Довідка");
        MenuItem aboutItem = new MenuItem("Про програму");
        aboutItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Про програму");
            alert.setHeaderText("Графічний редактор об'єктів (Lab 2)");
            alert.setContentText(StudentInfo.getAboutText());
            alert.showAndWait();
        });
        menuHelp.getItems().add(aboutItem);

        menuBar.getMenus().addAll(menuFile, menuObjects, menuHelp);
        return menuBar;
    }
}