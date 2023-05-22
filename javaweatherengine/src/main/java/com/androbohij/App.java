package com.androbohij;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ModuleLayer.Controller;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.tools.javac.Main;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Scene splash;
    private static final int SPLASH_WIDTH = 600;
    private static final int SPLASH_HEIGHT = 292;
    Timer splashTimer = new Timer();

    public static final String VERSION = "0.1.1";

    @Override
    public void start(Stage initStage) throws IOException {  
        Platform.setImplicitExit(true);
        try {
            showSplashScreen(initStage);
        } catch (Exception e) {
            System.out.println("check this shit " + e.toString());
        }
        TimerTask task = new TimerTask(){
            public void run() {
                Platform.runLater(() -> {
                    try {
                        closeSplashScreen(initStage);
                        showMainStage();
                        Font.loadFont(getClass().getResourceAsStream("/fonts/Inter-Medium.tff"), 20);
                    } catch (Exception e) {
                        System.out.println("look at this " + e.toString());
                        System.out.println("i give up");
                        System.exit(-1);
                    }
                });
            }
        };
        splashTimer.schedule(task,5000l);
    }
    public void showSplashScreen(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.setTitle("Java Weather Engine");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("images/icon.png")));
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        splash = new Scene(loadFXML("splash"), 600, 292);
        stage.setScene(splash);
        stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        stage.show();
    }

    public void closeSplashScreen(Stage stage) throws IOException {
        Platform.runLater(() -> stage.close());
    }

    public void showMainStage() throws IOException {
        Stage mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setMinWidth(800); mainStage.setMinHeight(600);
        mainStage.setTitle("Java Weather Engine");
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        scene = new Scene(loadFXML("primary"), 1280, 900);
        mainStage.getIcons().add(new Image(App.class.getResourceAsStream("images/icon.png")));
        mainStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1280 / 2);
        mainStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 900 / 2);
        mainStage.setScene(scene);
        mainStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void launcher(String[] args) {
        launch();
    }

}