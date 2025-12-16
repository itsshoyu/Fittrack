package com.fittrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean addUser(String username, String plainPassword) {
        if (isUsernameExists(username)) {
            System.out.println("Username sudah ada.");
            return false;
        }

        String sql = "INSERT INTO users(username, password_hash) VALUES(?,?)";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    public boolean validateUser(String username, String plainPassword) {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password_hash");
                return PasswordUtil.checkPassword(plainPassword, storedHashedPassword);
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public User getUserForLogin(String username, String plainPassword) {
        String sql = "SELECT id, username, password_hash, calorie_target FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password_hash");
                if (PasswordUtil.checkPassword(plainPassword, storedHashedPassword)) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("calorie_target")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal Login : " + e.getMessage());
        }
        return null;
    }

    public void updateCalorieTarget(int userId, int newTarget) {
        String sql = "UPDATE users SET calorie_target = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newTarget);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Gagal update target kalori: " + e.getMessage());
        }
    }
}