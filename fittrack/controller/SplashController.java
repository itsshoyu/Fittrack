package com.fittrack.controller;

import java.io.IOException;

import com.fittrack.SceneManager;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("unused")
public class SplashController {

    @FXML
    private StackPane rootPane;

    @FXML
    public void initialize() {
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        
        delay.setOnFinished(event -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(250), rootPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            
            fadeOut.setOnFinished(e -> {
                try {
                    Parent landingRoot = FXMLLoader.load(getClass().getResource("/com/fittrack/landing-view.fxml"));
                    Scene landingScene = new Scene(landingRoot, SceneManager.APP_WIDTH, SceneManager.APP_HEIGHT);
                    landingScene.setFill(Paint.valueOf("#1c1c1e"));
                    landingRoot.setOpacity(0.0);
                    
                    Stage stage = (Stage) rootPane.getScene().getWindow();
                    stage.setScene(landingScene);
                    stage.centerOnScreen();

                    FadeTransition fadeIn = new FadeTransition(Duration.millis(250), landingRoot);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            
            fadeOut.play();
        });
        
        delay.play();
    }
}