package com.androbohij;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

public class WindowHandler {

    @FXML
    private Label versionLabel;

    @FXML
    private Button about;

    @FXML
    private AnchorPane container;

    @FXML
    private AnchorPane content;

    @FXML
    private Button exit;

    @FXML
    private Button home;

    @FXML
    private MenuBar menu;

    @FXML
    private Button settings;

    @FXML
    private AnchorPane sidebar;

    @FXML
    private VBox vbox;

    @FXML
    void goAbout(ActionEvent event) {

    }

    @FXML
    void goExit(ActionEvent event) {
        System.out.println("Exit");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goHome(ActionEvent event) {

    }

    @FXML
    void goSettings(ActionEvent event) {

    }

    public void initialize() {
        versionLabel.setText("JWE v" + App.VERSION);
    }

}
