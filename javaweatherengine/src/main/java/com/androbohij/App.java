package com.androbohij;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Scene splash;
    private static final int SPLASH_WIDTH = 600;
    private static final int SPLASH_HEIGHT = 292;
    Timer splashTimer = new Timer();

    @Override
    public void start(Stage initStage) throws IOException {  
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
                    } catch (IOException e) {
                        System.out.println("oh my god bruh " + e.toString());
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
        InputStream stream = new FileInputStream("/images/icon.png");
        stage.getIcons().add(new Image(stream));
        stream.close();
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        splash = new Scene(loadFXML("splash"), 600, 292);
        stage.setScene(splash);
        stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        stage.show();
    }

    public void closeSplashScreen(Stage stage) throws IOException {
        stage.close();
    }

    public void showMainStage() throws IOException {
        Stage mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("Java Weather Engine");
        mainStage.getIcons().add(new Image("/img/icon.png"));
        scene = new Scene(loadFXML("primary"), 720, 540);
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

    public static void main(String[] args) {
        launch();
    }

}