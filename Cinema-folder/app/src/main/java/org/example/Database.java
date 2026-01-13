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
        String sql = "INSERT INTO servlets.users (first_name, last_name, phone, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, phone);
            // TODO : hash the password before storing it
            pstmt.setString(4, password);
            return pstmt.executeUpdate();
        }
    }

    public String AuthenticateUser(String phone, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM servlets.users WHERE phone = ? AND password = ?";
        try (Connection conn = getConnection(); var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, password);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // TODO: change this to Jwt or Cookies based session management
                    return "user_session_" + rs.getInt(1);
                } else {
                    return null;
                }
            }
        }
    }

    public boolean isAuthenticated(String sessionId) {

        String sql = "SELECT COUNT(*) FROM servlets.sessions WHERE session_id = ?";
        try (Connection conn = getConnection(); var pstmt = conn.prepareStatement(sql)) {
            // return "user_session_" + rs.getInt(1);

            String phoneFromSessionId = sessionId.replace("user_session_", "");
            pstmt.setString(1, phoneFromSessionId);

            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
