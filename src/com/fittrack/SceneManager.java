package com.fittrack;

import java.io.IOException;
import java.util.Objects; // <-- Import baru

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneManager {

    public static final double APP_WIDTH = 400;
    public static final double APP_HEIGHT = 700;

    public static void switchScene(ActionEvent event, String fxmlFile) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent currentRoot = stage.getScene().getRoot();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            try {
                Parent newRoot = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(fxmlFile)));
                Scene newScene = new Scene(newRoot, APP_WIDTH, APP_HEIGHT);
                newScene.setFill(Paint.valueOf("#1c1c1e"));
                
                newRoot.setOpacity(0.0);
                stage.setScene(newScene);
                stage.centerOnScreen();

                FadeTransition fadeIn = new FadeTransition(Duration.millis(150), newRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();

            } catch (IOException | NullPointerException ioException) {
                // PERBAIKAN DI SINI: Gunakan Platform.runLater
                Platform.runLater(() -> {
                    showAlert(Alert.AlertType.ERROR, "Kesalahan Navigasi", "Gagal memuat halaman: " + fxmlFile);
                });
                ioException.printStackTrace();
            }
        });
        
        fadeOut.play();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("FitTrack");
        alert.setHeaderText(title);
        alert.setContentText(message);
        // ... (kode styling dialog Anda jika ada) ...
        alert.showAndWait();
    }
}