package ui;

import dao.BookDAO;
import dao.IssueDAO;
import model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentDashboard extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField bookIdField;

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

        bookIdField = new JTextField(15);
        gbc.gridx = 1;
        issuePanel.add(bookIdField, gbc);

        JButton issueButton = new JButton("Issue Book");
        issueButton.setBackground(new Color(0, 153, 76));
        issueButton.setForeground(Color.WHITE);
        gbc.gridx = 2;
        issuePanel.add(issueButton, gbc);

        // Logout Button Centered
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutBtn.setBackground(Color.GRAY);
        logoutBtn.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        issuePanel.add(logoutBtn, gbc);

        add(issuePanel, BorderLayout.SOUTH);

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
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Book not available or invalid Book ID.");
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        loadBooks();
    }

    private void loadBooks() {
        tableModel.setRowCount(0); // Clear table
        List<Book> books = BookDAO.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                b.getBookId(), b.getTitle(), b.getAuthor(), b.getPublisher(), b.getQuantity()
            });
        }
    }
}
