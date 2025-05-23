package com.example.applivestream.database;

import com.example.applivestream.model.User;

import java.net.InetAddress;
import java.sql.*;

public class UserRepository {
//    private static final String URL = "jdbc:mysql://localhost:3306/applivestream";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";

    private static final String URL = "jdbc:mysql://sql8.freesqldatabase.com:3306/sql8780238";
    private static final String USER = "sql8780238";
    private static final String PASSWORD = "Pf7bLADBVu";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Đảm bảo đã thêm mysql-connector-j vào pom.xml
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmailTaken(String email) {
        String query = "SELECT 1 FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void saveUser(User user) {
        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testConnection() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Kết nối MySQL thành công");
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối MySQL: " + e.getMessage());
        }
    }
    public static User findUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("name"), email, rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void logAction(String email, String action) {
        String query = "INSERT INTO logs (user_email, action, ip_address) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String ip = InetAddress.getLocalHost().getHostAddress(); // lấy IP local
            stmt.setString(1, email);
            stmt.setString(2, action);
            stmt.setString(3, ip);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Log insert failed: " + e.getMessage());
            e.printStackTrace();
        }
    }


public static void main(String[] args) {
        UserRepository.testConnection();
    }



}
