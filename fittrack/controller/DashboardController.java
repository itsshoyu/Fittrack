package com.fittrack.controller;

import java.time.LocalDate;
import java.util.List;

import com.fittrack.SceneManager;
import com.fittrack.SessionManager;
import com.fittrack.model.CalorieLogDAO;
import com.fittrack.model.Latihan;
import com.fittrack.model.LatihanData;
import com.fittrack.model.Makanan;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

@SuppressWarnings("unused")
public class DashboardController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label kaloriHariIniLabel;
    
    @FXML
    private Label saranLatihanLabel;

    @FXML
    private ProgressBar calorieProgressBar;

    private final CalorieLogDAO calorieLogDAO = new CalorieLogDAO();
    private Latihan latihanYangDisarankan;

    @FXML
    public void initialize() {
        usernameLabel.setText(SessionManager.getCurrentUsername());
        loadKaloriSummary();
        loadSaranLatihan();
    }

    private void loadKaloriSummary() {
        if (!SessionManager.isLoggedIn()) return;

        int userId = SessionManager.getCurrentUserId();
        List<Makanan> logs = calorieLogDAO.getLogsForDate(userId, LocalDate.now());
        int total = logs.stream().mapToInt(Makanan::getKalori).sum();
        int target = SessionManager.getCurrentUser().getCalorieTarget();
        
        kaloriHariIniLabel.setText(String.format("%d / %d kkal", total, target));
        
        // Update Progress Donut
        double progress = (target == 0) ? 0 : (double) total / target;
        calorieProgressBar.setProgress(progress);

    }

    private void loadSaranLatihan() {
        latihanYangDisarankan = LatihanData.getRandomLatihan();
        if (latihanYangDisarankan != null) {
            saranLatihanLabel.setText(latihanYangDisarankan.getNama());
        }
    }

    @FXML
    void onSaranLatihanClick(MouseEvent event) {
        if (latihanYangDisarankan != null) {
            // Logika untuk pindah ke halaman detail latihan (jika sudah ada)
            System.out.println("Pindah ke detail latihan: " + latihanYangDisarankan.getNama());
        }
    }

    @FXML
    void keHalamanBmi(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/bmi.fxml");
    }

    @FXML
    void keHalamanKalori(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/kalori-view.fxml");
    }

    @FXML
    void onLogoutClick(ActionEvent event) {
        // Hapus sesi dan kembali ke halaman login
        SessionManager.logout();
        SceneManager.switchScene(event, "/com/fittrack/login-view.fxml");
    }

    @FXML
    void keHalamanPanduan(ActionEvent event) {
        // Ganti path FXML ke nama file yang baru
        SceneManager.switchScene(event, "/com/fittrack/panduan-latihan-view.fxml");
    }

    @FXML
    void keHalamanGrafik(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/chart-view.fxml");
    }

    @FXML
    void keHalamanTarget(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/target-kalori-view.fxml");
    }
}