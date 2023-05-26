package com.androbohij;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.*;

import org.tensorflow.TensorFlow;

/**
 * JavaFX App
 */
public class App extends Application {
    public static Preferences prefs = Preferences.userNodeForPackage(com.androbohij.App.class);
    public static Scene scene;
    public static WindowHandler controller;
    public static OfflineHandler controller2;
    public static Scene splash;
    private final int SPLASH_WIDTH = 700;
    private final int SPLASH_HEIGHT = 392;
    public static JWEL jwel;
    public static Boolean ONLINE = true;
    Timer splashTimer = new Timer();
    Timer jwelTimer = new Timer();

    public static boolean getOnline() throws IOException {
        try {
            InetAddress address = InetAddress.getByName("142.250.190.78");
            return address.isReachable(2000);
        } catch (SocketException e) {
            return false;
        }
    }

    @Override
    public void start(Stage initStage) throws IOException {  
        System.out.println(TensorFlow.version());
        Platform.setImplicitExit(true);
        ONLINE = getOnline();
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
                        e.printStackTrace();
                        System.out.println("i give up");
                        System.exit(-1);
                    }
                });
            }
        };
        TimerTask jwelTask = new TimerTask() {
            public void run() {
                jwel = new JWEL();
            }
        };
        jwelTimer.schedule(jwelTask,2000l);
        splashTimer.schedule(task,7000l);
    }

    public void showSplashScreen(Stage stage) throws IOException {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.setTitle("Java Weather Engine");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("images/icon.png")));
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        splash = new Scene(loadFXML("splash"), 700, 392);
        splash.setFill(Color.TRANSPARENT);
        stage.setScene(splash);
        stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        stage.show();
    }

    public void closeSplashScreen(Stage stage) throws IOException {
        Platform.runLater(() -> stage.close());
    }

    public void showMainStage() throws IOException {
        Stage mainStage = new Stage(StageStyle.UNIFIED);
        mainStage.setMinWidth(1024); mainStage.setMinHeight(768);
        mainStage.setTitle("Java Weather Engine");
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        // scene = new Scene(loadFXML("primary"), 1280, 900);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
        // Parent par = fxmlLoader.load();
        // controller = fxmlLoader.getController();
        scene = new Scene(fxmlLoader.load(), 1280, 900);
        mainStage.getIcons().add(new Image(App.class.getResourceAsStream("images/icon.png")));
        mainStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 1280 / 2);
        mainStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 900 / 2);
        mainStage.setScene(scene);
        mainStage.show();
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Exit");
                Platform.exit();
                System.exit(0);
            }
        });
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