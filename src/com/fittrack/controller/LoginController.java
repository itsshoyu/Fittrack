package com.fittrack.controller;

import com.fittrack.SceneManager;
import com.fittrack.SessionManager;
import com.fittrack.model.User;
import com.fittrack.model.UserDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@SuppressWarnings("unused")
public class LoginController {

    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void onLoginClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Login Gagal", "...");
            return;
        }

        User user = userDAO.getUserForLogin(username, password);
        if (user != null) {
            SessionManager.login(user);
            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Login Berhasil", "Selamat datang, " + user.getUsername() + "!");
            SceneManager.switchScene(event, "/com/fittrack/dashboard-view.fxml");
        } else {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau password salah.");
        }
    }

    @FXML
    private void keHalamanRegister(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/register-view.fxml");
    }

    @FXML
    private void keHalamanLanding(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/landing-view.fxml");
    }
}