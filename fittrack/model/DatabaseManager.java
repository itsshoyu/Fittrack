package com.fittrack.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final Path DB_DIRECTORY = Paths.get("database");
    private static final String DATABASE_URL = "jdbc:sqlite:" + DB_DIRECTORY.resolve("fittrack.db").toString();

    public static void initializeDatabase() {
        try {
            if (!Files.exists(DB_DIRECTORY)) {
                Files.createDirectories(DB_DIRECTORY);
            }
        } catch (IOException e) {
            System.err.println("Gagal membuat direktori database: " + e.getMessage());
            return;
        }

        String createUserTableSql = "CREATE TABLE IF NOT EXISTS users ("
                                  + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                  + " username TEXT NOT NULL UNIQUE,"
                                  + " password_hash TEXT NOT NULL,"
                                  + " calorie_target INTEGER DEFAULT 2000"
                                  + ");";

        String createCalorieLogTableSql = "CREATE TABLE IF NOT EXISTS calorie_logs ("
                                        + " log_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                                        + " user_id INTEGER NOT NULL,"
                                        + " food_name TEXT NOT NULL,"
                                        + " calories INTEGER NOT NULL,"
                                        + " log_date TEXT NOT NULL,"
                                        + " FOREIGN KEY (user_id) REFERENCES users (id)"
                                        + ");";
        
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            // Membuat tabel
            stmt.execute(createUserTableSql);
            stmt.execute(createCalorieLogTableSql);
            System.out.println("Database dan tabel berhasil diinisialisasi.");
        } catch (SQLException e) {
            System.out.println("Error saat inisialisasi database: " + e.getMessage());
        }
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Error koneksi ke SQLite: " + e.getMessage());
        }
        return conn;
    }
}