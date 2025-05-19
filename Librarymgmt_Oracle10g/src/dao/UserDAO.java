package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public static String validateLogin(String userId, String password) {
        String role = null;
        try {
            Connection conn = DBConnection.getConnection();
            // Correct table name is Lib_users
            PreparedStatement ps = conn.prepareStatement(
                "SELECT role FROM Lib_users WHERE user_id = ? AND password = ?"
            );
            ps.setString(1, userId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}
