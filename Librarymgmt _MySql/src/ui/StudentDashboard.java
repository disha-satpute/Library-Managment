package ui;

import dao.BookDAO;
import dao.DBConnection;
import dao.IssueDAO;
import model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDashboard extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton profileBtn;

    public StudentDashboard(String studentId) {
        setTitle("SmartRead Library - Student Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Welcome to SmartRead Library - Student Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel searchLabel = new JLabel("Search Book by Title:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        searchField = new JTextField(20);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        JButton seeAllButton = new JButton("See All Books");
        searchPanel.add(seeAllButton);

        add(searchPanel, BorderLayout.NORTH);

        // Book Table
        String[] columns = {"Book ID", "Title", "Author", "Publisher", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Available Books"));
        add(scrollPane, BorderLayout.CENTER);

        // Issue Panel (South)
        JPanel issuePanel = new JPanel(new GridBagLayout());
        issuePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        issuePanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel issueLabel = new JLabel("Enter Book ID to Issue:");
        issueLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        issuePanel.add(issueLabel, gbc);

        JTextField bookIdField = new JTextField(15);
        gbc.gridx = 1;
        issuePanel.add(bookIdField, gbc);

        JButton issueButton = new JButton("Issue Book");
        issueButton.setBackground(new Color(0, 153, 76));
        issueButton.setForeground(Color.WHITE);
        gbc.gridx = 2;
        issuePanel.add(issueButton, gbc);

        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutBtn.setBackground(Color.GRAY);
        logoutBtn.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        issuePanel.add(logoutBtn, gbc);

        // Profile Button
        profileBtn = new JButton("My Profile");
        profileBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        profileBtn.setBackground(new Color(0, 102, 204));
        profileBtn.setForeground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 1;
        issuePanel.add(profileBtn, gbc);

        add(issuePanel, BorderLayout.SOUTH);

        // Action listeners
        searchButton.addActionListener(e -> {
            String title = searchField.getText().trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a book title to search.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                List<Book> books = BookDAO.getBooksByTitle(title);
                loadBooks(books);
            }
        });

        seeAllButton.addActionListener(e -> {
            loadBooks(BookDAO.getAllBooks());
        });

        issueButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Book ID to issue.");
                return;
            }
            boolean issued = IssueDAO.issueBook(studentId, bookId);
            if (issued) {
                JOptionPane.showMessageDialog(this, "Book issued successfully!");
                bookIdField.setText("");
                loadBooks(BookDAO.getAllBooks());
            } else {
                JOptionPane.showMessageDialog(this, "Book not available or invalid Book ID.");
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        profileBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new StudentProfile(studentId).setVisible(true);
            }
        });

        loadBooks(BookDAO.getAllBooks());
    }

    private void loadBooks(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                b.getBookId(), b.getTitle(), b.getAuthor(), b.getPublisher(), b.getQuantity()
            });
        }
    }

    private void viewIssueHistory(String studentId) {
        JFrame historyFrame = new JFrame("Book Issue History");
        historyFrame.setSize(600, 400);
        historyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"Book Title", "Issue Date", "Return Status"};
        DefaultTableModel historyTableModel = new DefaultTableModel(columns, 0);
        JTable historyTable = new JTable(historyTableModel);
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyFrame.add(historyScrollPane, BorderLayout.CENTER);

        String query = "SELECT b.title, i.issue_date, i.return_status " +
                       "FROM issued_books i " +
                       "JOIN Lib_books b ON i.book_id = b.book_id " +
                       "WHERE i.student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, studentId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                Date issueDate = rs.getDate("issue_date");
                String returnStatus = rs.getString("return_status");
                historyTableModel.addRow(new Object[]{title, issueDate, returnStatus});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching issue history.");
        }

        historyFrame.setVisible(true);
    }
}
