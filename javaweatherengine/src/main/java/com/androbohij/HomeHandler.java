package com.androbohij;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HomeHandler {

    @FXML
    private StackPane day0stack;

    @FXML
    private Label day0temp;

    @FXML
    private Label day0temp2;

    @FXML
    private ImageView day1arrow;

    @FXML
    private Label day1date;

    @FXML
    private Label day1day;

    @FXML
    private StackPane day1stack;

    @FXML
    private Label day1temp;

    @FXML
    private Label day1temp2;

    @FXML
    private VBox day1tray;
    private boolean day1trayActive;

    @FXML
    private ImageView day2arrow;

    @FXML
    private Label day2date;

    @FXML
    private Label day2day;

    @FXML
    private StackPane day2stack;

    @FXML
    private Label day2temp;

    @FXML
    private Label day2temp2;

    @FXML
    private VBox day2tray;
    private boolean day2trayActive;

    @FXML
    private ImageView day3arrow;

    @FXML
    private Label day3date;

    @FXML
    private Label day3day;

    @FXML
    private StackPane day3stack;

    @FXML
    private Label day3temp1;

    @FXML
    private Label day3temp2;

    @FXML
    private VBox day3tray;
    private boolean day3trayActive;

    @FXML
    private ImageView day4arrow;

    @FXML
    private Label day4date;

    @FXML
    private Label day4day;

    @FXML
    private StackPane day4stack;

    @FXML
    private Label day4temp1;

    @FXML
    private Label day4temp2;

    @FXML
    private VBox day4tray;
    private boolean day4trayActive;

    @FXML
    private ImageView day5arrow;

    @FXML
    private Label day5date;

    @FXML
    private Label day5day;

    @FXML
    private StackPane day5stack;

    @FXML
    private Label day5temp1;

    @FXML
    private Label day5temp2;

    @FXML
    private VBox day5tray;
    private boolean day5trayActive;

    @FXML
    private ImageView day6arrow;

    @FXML
    private Label day6date;

    @FXML
    private Label day6day;

    @FXML
    private StackPane day6stack;

    @FXML
    private Label day6temp1;

    @FXML
    private Label day6temp2;

    @FXML
    private VBox day6tray;
    private boolean day6trayActive;

    @FXML
    void day1min(MouseEvent event) {
        if (!day1trayActive) {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day1tray);
            translateTransition.setFromY(0);
            translateTransition.setToY(150);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day1arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day1trayActive = true;
            day1arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day1arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        } else {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day1tray);
            translateTransition.setFromY(150);
            translateTransition.setToY(0);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day1arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day1trayActive = false;
            day1arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day1arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        }
    }

    @FXML
    void day2min(MouseEvent event) {
        if (!day2trayActive) {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day2tray);
            translateTransition.setFromY(0);
            translateTransition.setToY(150);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day2arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day2trayActive = true;
            day2arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day2arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        } else {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day2tray);
            translateTransition.setFromY(150);
            translateTransition.setToY(0);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day2arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day2trayActive = false;
            day2arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day2arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        }
    }

    @FXML
    void day3min(MouseEvent event) {
        if (!day3trayActive) {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day3tray);
            translateTransition.setFromY(0);
            translateTransition.setToY(150);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day3arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day3trayActive = true;
            day3arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day3arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        } else {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day3tray);
            translateTransition.setFromY(150);
            translateTransition.setToY(0);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day3arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day3trayActive = false;
            day3arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day3arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        }
    }

    @FXML
    void day4min(MouseEvent event) {
        if (!day4trayActive) {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day4tray);
            translateTransition.setFromY(0);
            translateTransition.setToY(150);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day4arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day4trayActive = true;
            day4arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day4arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        } else {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day4tray);
            translateTransition.setFromY(150);
            translateTransition.setToY(0);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day4arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day4trayActive = false;
            day4arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day4arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        }
    }

    @FXML
    void day5min(MouseEvent event) {
        if (!day5trayActive) {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day5tray);
            translateTransition.setFromY(0);
            translateTransition.setToY(150);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day5arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day5trayActive = true;
            day5arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day5arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        } else {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day5tray);
            translateTransition.setFromY(150);
            translateTransition.setToY(0);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day5arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day5trayActive = false;
            day5arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day5arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        }
    }

    @FXML
    void day6min(MouseEvent event) {
        if (!day6trayActive) {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day6tray);
            translateTransition.setFromY(0);
            translateTransition.setToY(150);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day6arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day6trayActive = true;
            day6arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day6arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        } else {
            TranslateTransition translateTransition =
            new TranslateTransition(Duration.millis(150), day6tray);
            translateTransition.setFromY(150);
            translateTransition.setToY(0);
            translateTransition.play();
            RotateTransition rotateTransition =
            new RotateTransition(Duration.millis(150), day6arrow);
            rotateTransition.setByAngle(180f);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();
            day6trayActive = false;
            day6arrow.setDisable(true);
            final Timeline animation = new Timeline(
            new KeyFrame(Duration.millis(150),
            new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent actionEvent) {
                    day6arrow.setDisable(false);
                }
            }));
            animation.setCycleCount(1);
            animation.play();
        }
    }

    public void initialize() {
        day1trayActive = false;
        day2trayActive = false;
        day3trayActive = false;
        day4trayActive = false;
        day5trayActive = false;
        day6trayActive = false;
    }

}
