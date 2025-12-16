package com.fittrack.controller;

import com.fittrack.SceneManager;
import com.fittrack.model.BMICalculator;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.util.Duration;

@SuppressWarnings("unused")
public class BmiController {

    @FXML
    private TextField tinggiField;

    @FXML
    private TextField beratField;

    @FXML
    private Label hasilLabel;

    @FXML
    private Label kategoriLabel;

    @FXML
    private Line jarumIndikator;

    @FXML
    private Button hitungButton;

    @FXML
    private Button kembaliButton;

    private final BMICalculator calculator = new BMICalculator();

    @FXML
    protected void onHitungButtonClick() {
        try {
            double tinggi = Double.parseDouble(tinggiField.getText());
            double berat = Double.parseDouble(beratField.getText());

            double bmi = calculator.calculate(berat, tinggi);
            String kategori = calculator.getCategory(bmi);

            hasilLabel.setText(String.format("%.1f", bmi));
            kategoriLabel.setText(kategori);

            animasikanJarum(bmi);

        } catch (NumberFormatException e) {
            hasilLabel.setText("Error");
            kategoriLabel.setText("Input salah!");
        }
    }

    private double hitungSudutDariBmi(double bmi) {
        final double bmiKurus = 18.5;       final double sudutKurus = -45.0;
        final double bmiNormal = 25.0;      final double sudutNormal = 0.0;
        final double bmiGemuk = 30.0;       final double sudutGemuk = 45.0;
        final double bmiObesitas = 40.0;    final double sudutObesitas = 90.0;

        if (bmi < bmiKurus) {
            return interpolasi(bmi, 15.0, bmiKurus, -90.0, sudutKurus);
        } else if (bmi < bmiNormal) {
            return interpolasi(bmi, bmiKurus, bmiNormal, sudutKurus, sudutNormal);
        } else if (bmi < bmiGemuk) {
            return interpolasi(bmi, bmiNormal, bmiGemuk, sudutNormal, sudutGemuk);
        } else {
            return interpolasi(bmi, bmiGemuk, bmiObesitas, sudutGemuk, sudutObesitas);
        }
    }

    private double interpolasi(double nilai, double minNilai, double maxNilai, double minSudut, double maxSudut) {
        nilai = Math.max(minNilai, Math.min(maxNilai, nilai));
        if (maxNilai == minNilai) return minSudut;
        double persentase = (nilai - minNilai) / (maxNilai - minNilai);
        return minSudut + persentase * (maxSudut - minSudut);
    }

    private void animasikanJarum(double bmi) {
        double sudutTujuan = hitungSudutDariBmi(bmi);
        RotateTransition rt = new RotateTransition(Duration.seconds(1), jarumIndikator);
        rt.setToAngle(sudutTujuan);
        rt.setCycleCount(1);
        rt.setAutoReverse(false);
        rt.play();
    }

    @FXML
    private void keHalamanDashboard(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/dashboard-view.fxml");
    }
}