package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginForm() {
        setTitle(" SmartRead Library - Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Welcome to SmartRead Library");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy++;
        mainPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        userField = new JTextField(20);
        mainPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        passField = new JPasswordField(20);
        mainPanel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(0, 153, 76));
        loginBtn.setForeground(Color.WHITE);
        mainPanel.add(loginBtn, gbc);

        gbc.gridx = 1;
        JButton exitBtn = new JButton("Exit");
        exitBtn.setBackground(Color.GRAY);
        exitBtn.setForeground(Color.WHITE);
        mainPanel.add(exitBtn, gbc);

        loginBtn.addActionListener(e -> loginAction());
        exitBtn.addActionListener(e -> System.exit(0));

        add(mainPanel);
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

            // Pass the user (admin) ID to AdminDashboard constructor
        if (role.equalsIgnoreCase("admin")) {
            new AdminDashboard(user).setVisible(true); // Pass user ID to AdminDashboard
        } else if (role.equalsIgnoreCase("student")) {
            new StudentDashboard(user).setVisible(true); // Pass user ID to StudentDashboard
        }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
