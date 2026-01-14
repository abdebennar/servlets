package org.cinema;

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
        String sql = "SELECT id FROM servlets.users WHERE phone = ? AND password = ? LIMIT 1";
        try (Connection conn = getConnection(); var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, password);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userId = rs.getString("id");
                    // Generate and return JWT token
                    return JwtUtil.generateToken(userId);
                } else {
                    return null;
                }
            }
        }
    }

    public String[] getNames(String userID) {
        String sql = "SELECT first_name, last_name FROM servlets.users WHERE id = CAST(? AS INTEGER) LIMIT 1";
        try (Connection conn = getConnection(); var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            try (var rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    return new String[]{firstName, lastName};
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
