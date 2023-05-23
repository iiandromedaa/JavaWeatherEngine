package com.androbohij;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Properties;

import javafx.application.Platform;

public class WindowHandler {

    @FXML
    private Label versionLabel;

    @FXML
    private Button about;

    @FXML
    private Button exit;

    @FXML
    private Button home;

    @FXML
    private MenuBar menu;

    @FXML
    private Button settings;

    @FXML
    private VBox vbox;

    @FXML
    private BorderPane borderpane;

    @FXML
    void goAbout(ActionEvent event) {
        borderpane.setCenter(null);
    }

    @FXML
    void goExit(ActionEvent event) {
        System.out.println("Exit");
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goHome(ActionEvent event) {
        borderpane.setCenter(null);
        GridPane homePane;
        try {
            homePane = FXMLLoader.load(App.class.getResource("home.fxml"));
            homePane.setPrefSize(1180, 900);
            borderpane.setCenter(homePane);
            BorderPane.setAlignment(homePane, Pos.CENTER);
        } catch (IOException e) {
            System.out.println("home not found???");
        }
    }

    @FXML
    void goSettings(ActionEvent event) {
        borderpane.setCenter(null);
    }

    public void initialize() {
        final Properties properties = new Properties();
        try {
            properties.load(App.class.getResourceAsStream("project.properties"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // System.out.println(properties.getProperty("version"));
        // System.out.println(properties.getProperty("artifactId"));
        versionLabel.setText("JWE v" + properties.getProperty("version"));
        goHome(new ActionEvent());
    }
}
