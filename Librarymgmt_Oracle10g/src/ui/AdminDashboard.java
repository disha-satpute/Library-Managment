package ui;

import dao.BookDAO;
import model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTextField bookIdField, titleField, authorField, publisherField, quantityField;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public AdminDashboard(String adminId) {
        setTitle("SmartRead Library - Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Admin Dashboard - SmartRead Library", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0, 102, 204));
        add(titleLabel, BorderLayout.NORTH);

        // Split pane
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(500);

        // ===== LEFT: Form panel =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields
        bookIdField = new JTextField(20);
        titleField = new JTextField(20);
        authorField = new JTextField(20);
        publisherField = new JTextField(20);
        quantityField = new JTextField(20);

        String[] labels = {"Book ID:", "Title:", "Author:", "Publisher:", "Quantity:"};
        JTextField[] fields = {bookIdField, titleField, authorField, publisherField, quantityField};

        gbc.gridx = 0;
        gbc.gridy = 0;

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            formPanel.add(fields[i], gbc);

            gbc.gridy++;
        }

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addBtn = new JButton("Add Book");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete Book");

        Color btnColor = new Color(0, 153, 76);
        Font btnFont = new Font("SansSerif", Font.BOLD, 14);

        for (JButton btn : new JButton[]{addBtn, updateBtn, deleteBtn}) {
            btn.setBackground(btnColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(btnFont);
            btn.setPreferredSize(new Dimension(130, 35));
            buttonPanel.add(btn);
        }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(buttonPanel, gbc);

        splitPane.setLeftComponent(formPanel);

        // ===== RIGHT: Table panel =====
        tableModel = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Publisher", "Quantity"}, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        splitPane.setRightComponent(scrollPane);

        add(splitPane, BorderLayout.CENTER);

        // ===== Logout button at bottom =====
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(204, 0, 0));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        logoutBtn.setPreferredSize(new Dimension(120, 40));

        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(new Color(240, 248, 255));
        logoutPanel.add(logoutBtn);

        add(logoutPanel, BorderLayout.SOUTH);

        // Load data
        loadBooks();

        // ===== Button Actions =====
        addBtn.addActionListener(e -> {
            Book book = getBookFromFields();
            if (BookDAO.addBook(book)) {
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                loadBooks();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add book.");
            }
        });

        updateBtn.addActionListener(e -> {
            Book book = getBookFromFields();
            if (BookDAO.updateBook(book)) {
                JOptionPane.showMessageDialog(this, "Book updated successfully!");
                loadBooks();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update book.");
            }
        });

        deleteBtn.addActionListener(e -> {
            String bookId = bookIdField.getText();
            if (BookDAO.deleteBook(bookId)) {
                JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                loadBooks();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete book.");
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        bookTable.getSelectionModel().addListSelectionListener(event -> {
            int row = bookTable.getSelectedRow();
            if (row >= 0) {
                bookIdField.setText(tableModel.getValueAt(row, 0).toString());
                titleField.setText(tableModel.getValueAt(row, 1).toString());
                authorField.setText(tableModel.getValueAt(row, 2).toString());
                publisherField.setText(tableModel.getValueAt(row, 3).toString());
                quantityField.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private Book getBookFromFields() {
        return new Book(
                bookIdField.getText(),
                titleField.getText(),
                authorField.getText(),
                publisherField.getText(),
                Integer.parseInt(quantityField.getText())
        );
    }

    private void loadBooks() {
        tableModel.setRowCount(0);
        List<Book> books = BookDAO.getAllBooks();
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getBookId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getQuantity()
            });
        }
    }

    private void clearFields() {
        bookIdField.setText("");
        titleField.setText("");
        authorField.setText("");
        publisherField.setText("");
        quantityField.setText("");
    }
}
