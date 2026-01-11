package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: change the implementation to use a connection pool like HikariCP for better performance
public class Database {

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    static {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new RuntimeException("Database environment variables are not set");
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public int RegisterUser(String firstName, String lastName, String phone, String password)
            throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, phone, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);
            pstmt.setString(4, password);
            return pstmt.executeUpdate();
        }
    }

}
