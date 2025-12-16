package com.fittrack.controller;

import com.fittrack.SceneManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

@SuppressWarnings("unused")
public class LandingController {

    @FXML
    void keHalamanLogin(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/login-view.fxml");
    }

    @FXML
    void keHalamanRegister(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/register-view.fxml");
    }
}