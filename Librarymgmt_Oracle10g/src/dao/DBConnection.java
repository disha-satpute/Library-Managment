package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", // Change SID if needed
                    "system",                       // Replace with Oracle username
                    "hr"                        // Replace with Oracle password
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
