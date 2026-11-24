package com.lab4.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class AppMenuBar {
    private final MenuBar menuBar;

    public AppMenuBar(Stage stage) {
        menuBar = new MenuBar();

        // файл
        Menu menuFile = new Menu("Файл");
        MenuItem exitItem = new MenuItem("Вихід");
        exitItem.setOnAction(e -> stage.close());
        menuFile.getItems().add(exitItem);

        menuBar.getMenus().add(menuFile);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
