package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

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
    public static Map<String, String> getUserProfile(String userId) {
    Map<String, String> profile = new HashMap<>();
    try (Connection conn = DBConnection.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT user_id, name, email, role FROM users WHERE user_id = ?"
        );
        stmt.setString(1, userId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            profile.put("id", rs.getString("user_id"));
            profile.put("name", rs.getString("name"));
            profile.put("email", rs.getString("email"));
            profile.put("role", rs.getString("role"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return profile;
}

}
