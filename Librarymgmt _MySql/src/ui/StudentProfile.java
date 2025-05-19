package ui;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StudentProfile extends JFrame {

    public StudentProfile(String studentId) {
        setTitle("Student Profile - SmartRead Library");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(255, 255, 240));

        JLabel title = new JLabel("Student Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 102, 0));
        panel.add(title);

        Map<String, String> profile = UserDAO.getUserProfile(studentId);
        panel.add(new JLabel("ID: " + profile.get("id")));
        panel.add(new JLabel("Name: " + profile.get("name")));
        panel.add(new JLabel("Email: " + profile.get("email")));
        panel.add(new JLabel("Role: " + profile.get("role")));

        add(panel);
    }
    
    

}
