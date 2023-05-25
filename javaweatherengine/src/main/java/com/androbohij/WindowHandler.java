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

    private int display;
    //0 = home
    //1 = settings
    //2 = about
    //3 = offline
    //4 = other
    public BorderPane offlinePane;

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
        if (display == 2 || display == 3) {
            return;
        }
        display = 2;
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
        if (display == 0 || display == 3) {
            return;
        }
        display = 0;
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

    void goOffline(ActionEvent event) {
        display = 3;
        borderpane.setCenter(null);
        borderpane.setCenter(offlinePane);
    }

    @FXML
    void goSettings(ActionEvent event) {
        if (display == 1 || display == 3) {
            return;
        }
        display = 1;
        borderpane.setCenter(null);
    }

    public void fresh() throws IOException {
        App.jwel.refresh();
        if (App.ONLINE) {
            display = 4;
            goHome(new ActionEvent());
        }
    }

    public void initialize() throws IOException {
        App.controller = this;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("offline.fxml"));
        // offlinePane = FXMLLoader.load(App.class.getResource("offline.fxml"));
        offlinePane = loader.load();
        App.controller2 = loader.getController();
        System.out.println(App.controller2);
        App.controller2.setMainTestController(App.controller);
        System.out.println(App.controller);
        display = 4;
        final Properties properties = new Properties();
        try {
            properties.load(App.class.getResourceAsStream("project.properties"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        versionLabel.setText("JWE v" + properties.getProperty("version"));
        if (!App.ONLINE) {
            display = 3;
            goOffline(new ActionEvent());
        }
        goOffline(new ActionEvent());
        // goHome(new ActionEvent());
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("offline.fxml"));
        // OfflineHandler controller = (OfflineHandler)loader.getController();
        // controller.setMainTestController(this);
    }
}
