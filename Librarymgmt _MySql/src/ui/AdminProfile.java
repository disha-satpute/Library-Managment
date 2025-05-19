package ui;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AdminProfile extends JFrame {

    public AdminProfile(String adminId) {
        setTitle("Admin Profile - SmartRead Library");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(230, 247, 255));

        JLabel title = new JLabel("Admin Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 102, 204));
        panel.add(title);

        Map<String, String> profile = UserDAO.getUserProfile(adminId);
        panel.add(new JLabel("ID: " + profile.get("id")));
        panel.add(new JLabel("Name: " + profile.get("name")));
        panel.add(new JLabel("Email: " + profile.get("email")));
        panel.add(new JLabel("Role: " + profile.get("role")));

        add(panel);
    }
   

}
