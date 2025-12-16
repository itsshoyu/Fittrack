package com.fittrack.controller;

import com.fittrack.SceneManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@SuppressWarnings("unused")
public class PanduanLatihanController {

    @FXML
    void showExerciseList(ActionEvent event) {
        String category = (String) ((Button) event.getSource()).getUserData();
        
        DaftarLatihanController.setKategori(category);
        
        SceneManager.switchScene(event, "/com/fittrack/daftar-latihan-view.fxml");
    }

    @FXML
    void keHalamanDashboard(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/dashboard-view.fxml");
    }
}