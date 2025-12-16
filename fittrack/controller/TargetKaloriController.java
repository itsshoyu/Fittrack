package com.fittrack.controller;

import com.fittrack.SceneManager;
import com.fittrack.SessionManager;
import com.fittrack.model.UserDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@SuppressWarnings("unused")
public class TargetKaloriController {

    @FXML
    private Label rekomendasiLabel;
    
    @FXML
    private TextField targetField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        
        int currentTarget = SessionManager.getCurrentUser().getCalorieTarget();
        targetField.setText(String.valueOf(currentTarget));
    }

    @FXML
    void onSimpanClick(ActionEvent event) {
        try {
            int newTarget = Integer.parseInt(targetField.getText());
            if (newTarget <= 0) {
                SceneManager.showAlert(Alert.AlertType.ERROR, "Input Salah", "Target kalori harus lebih dari 0.");
                return;
            }

            int userId = SessionManager.getCurrentUserId();
            userDAO.updateCalorieTarget(userId, newTarget);
            
            SessionManager.getCurrentUser().setCalorieTarget(newTarget);

            SceneManager.showAlert(Alert.AlertType.INFORMATION, "Berhasil", "Target kalori berhasil disimpan!");
            keHalamanDashboard(event);

        } catch (NumberFormatException e) {
            SceneManager.showAlert(Alert.AlertType.ERROR, "Input Salah", "Harap masukkan angka yang valid.");
        }
    }

    @FXML
    void onIncrementClick(ActionEvent event) {
        try {
            int currentValue = Integer.parseInt(targetField.getText());
            currentValue += 50; // Menambah 50 setiap kali diklik
            targetField.setText(String.valueOf(currentValue));
        } catch (NumberFormatException e) {
            targetField.setText("2000"); // Default jika input tidak valid
        }
    }

    /**
     * Dipanggil saat tombol '-' diklik.
     */
    @FXML
    void onDecrementClick(ActionEvent event) {
        try {
            int currentValue = Integer.parseInt(targetField.getText());
            currentValue -= 50; // Mengurangi 50 setiap kali diklik
            if (currentValue < 0) currentValue = 0; // Batas minimum
            targetField.setText(String.valueOf(currentValue));
        } catch (NumberFormatException e) {
            targetField.setText("2000");
        }
    }

    @FXML
    void keHalamanDashboard(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/dashboard-view.fxml");
    }
}