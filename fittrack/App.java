package com.fittrack;

import java.io.IOException;
import java.net.URL;

import com.fittrack.model.DatabaseManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseManager.initializeDatabase();

        URL fxmlLocation = getClass().getResource("/com/fittrack/splash-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Scene scene = new Scene(fxmlLoader.load(), SceneManager.APP_WIDTH, SceneManager.APP_HEIGHT);
        scene.setFill(Paint.valueOf("#1c1c1e"));
        stage.setTitle("FitTrack");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}