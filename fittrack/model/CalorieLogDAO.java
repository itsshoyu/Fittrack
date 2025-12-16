package com.fittrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CalorieLogDAO {

    public List<Makanan> getLogsForDate(int userId, LocalDate date) {
        List<Makanan> logs = new ArrayList<>();
        String sql = "SELECT log_id, food_name, calories FROM calorie_logs WHERE user_id = ? AND log_date = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, date.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                logs.add(new Makanan(
                    rs.getInt("log_id"),
                    rs.getString("food_name"),
                    rs.getInt("calories")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil log: " + e.getMessage());
        }
        return logs;
    }

    public void addLog(int userId, Makanan makanan, LocalDate date) {
        String sql = "INSERT INTO calorie_logs(user_id, food_name, calories, log_date) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, makanan.getNama());
            pstmt.setInt(3, makanan.getKalori());
            pstmt.setString(4, date.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menambah log: " + e.getMessage());
        }
    }

    public void deleteLog(int logId) {
        String sql = "DELETE FROM calorie_logs WHERE log_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, logId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus log: " + e.getMessage());
        }
    }
    
    public void updateLog(Makanan makanan) {
        String sql = "UPDATE calorie_logs SET food_name = ?, calories = ? WHERE log_id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, makanan.getNama());
            pstmt.setInt(2, makanan.getKalori());
            pstmt.setInt(3, makanan.getLogId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal update log: " + e.getMessage());
        }
    }

    public Map<LocalDate, Integer> getCalorieSummaryForLast7Days(int userId) {
        Map<LocalDate, Integer> dailyTotals = new TreeMap<>();
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(6);

        String sql = "SELECT log_date, SUM(calories) as total_calories "
                   + "FROM calorie_logs "
                   + "WHERE user_id = ? AND log_date >= ? "
                   + "GROUP BY log_date "
                   + "ORDER BY log_date ASC";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, sevenDaysAgo.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString("log_date"));
                int totalCalories = rs.getInt("total_calories");
                dailyTotals.put(date, totalCalories);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil ringkasan kalori: " + e.getMessage());
        }
        return dailyTotals;
    }
}