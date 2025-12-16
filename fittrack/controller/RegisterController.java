package com.fittrack.controller;

import com.fittrack.SceneManager;
import com.fittrack.model.UserDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@SuppressWarnings("unused")
public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField; // <-- Tambahkan ini

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void onDaftarClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText(); // <-- Ambil nilai konfirmasi

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Semua field tidak boleh kosong.");
            return;
        }

        // --- LOGIKA BARU: Cek apakah password cocok ---
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Password dan konfirmasi password tidak cocok.");
            return;
        }

        if (userDAO.addUser(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Pendaftaran Berhasil!", "Akun Anda telah berhasil dibuat. Silakan login.");
            keHalamanLogin(event);
        } else {
            showAlert(Alert.AlertType.ERROR, "Pendaftaran Gagal!", "Username '" + username + "' sudah digunakan. Silakan pilih username lain.");
        }
    }

    @FXML
    private void keHalamanLogin(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/login-view.fxml");
    }
    
    @FXML
    private void keHalamanLanding(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/landing-view.fxml");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        // Panggil metode terpusat dari SceneManager
        SceneManager.showAlert(alertType, title, message);
    }
}