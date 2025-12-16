package com.fittrack.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.fittrack.SceneManager;
import com.fittrack.SessionManager;
import com.fittrack.model.CalorieLogDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

@SuppressWarnings("unused")
public class ChartController {

    @FXML
    private BarChart<String, Number> calorieChart;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;

    private final CalorieLogDAO calorieLogDAO = new CalorieLogDAO();

    @FXML
    public void initialize() {
        populateChart();
    }

    private void populateChart() {
        int userId = SessionManager.getCurrentUserId();
        Map<LocalDate, Integer> dailyData = calorieLogDAO.getCalorieSummaryForLast7Days(userId);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Asupan Kalori Harian");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        for (Map.Entry<LocalDate, Integer> entry : dailyData.entrySet()) {
            String formattedDate = entry.getKey().format(formatter);
            series.getData().add(new XYChart.Data<>(formattedDate, entry.getValue()));
        }

        calorieChart.getData().add(series);
    }

    @FXML
    void keHalamanDashboard(ActionEvent event) {
        SceneManager.switchScene(event, "/com/fittrack/dashboard-view.fxml");
    }
}