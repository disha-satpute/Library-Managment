package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginForm() {
        setTitle("SmartRead Library - Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set overall background
        getContentPane().setBackground(new Color(230, 240, 255));

        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(Color.WHITE);
        loginCard.setPreferredSize(new Dimension(400, 300));
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("SmartRead Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginCard.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        loginCard.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        userField = new JTextField(20);
        userField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        loginCard.add(userField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        loginCard.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passField = new JPasswordField(20);
        passField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        loginCard.add(passField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(0, 153, 76));
        loginCard.add(loginBtn, gbc);

        gbc.gridx = 1;
        JButton exitBtn = new JButton("Exit");
        styleButton(exitBtn, new Color(204, 0, 0));
        loginCard.add(exitBtn, gbc);

        loginBtn.addActionListener(e -> loginAction());
        exitBtn.addActionListener(e -> System.exit(0));

        // Center the card in the window
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(loginCard);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void loginAction() {
        String user = userField.getText();
        String pass = new String(passField.getPassword());

        String role = UserDAO.validateLogin(user, pass);

        if (role == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Welcome, " + role);
            dispose();

            if (role.equalsIgnoreCase("admin")) {
                new AdminDashboard(user).setVisible(true);
            } else if (role.equalsIgnoreCase("student")) {
                new StudentDashboard(user).setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UIManager.put("Button.arc", 999);
            UIManager.put("Component.arc", 10); // Rounded corners if using FlatLaf
            new LoginForm().setVisible(true);
        });
    }
}
