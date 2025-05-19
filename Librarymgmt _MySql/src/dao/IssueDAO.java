package dao;

import java.sql.*;

public class IssueDAO {
    public static boolean issueBook(String studentId, String bookId) {
        try {
            Connection conn = DBConnection.getConnection();

            // Check availability
            PreparedStatement checkStmt = conn.prepareStatement("SELECT quantity FROM Lib_books WHERE book_id = ?");
            checkStmt.setString(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int qty = rs.getInt("quantity");
                if (qty <= 0) {
                    return false;
                }

                // Decrease quantity
                PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE Lib_books SET quantity = quantity - 1 WHERE book_id = ?"
                );
                updateStmt.setString(1, bookId);
                updateStmt.executeUpdate();

                // Issue book
                PreparedStatement issueStmt = conn.prepareStatement(
                    "INSERT INTO issued_books (student_id, book_id, issue_date) " +
                    "VALUES (?, ?, SYSDATE())"
                );
                issueStmt.setString(1, studentId);
                issueStmt.setString(2, bookId);
                return issueStmt.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
