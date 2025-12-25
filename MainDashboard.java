package projectgui2025;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainDashboard extends JFrame {
    JButton btn1, btn2, btn3, btn4;
    JButton btnBack, btnHome, btnLogout;  // navbar buttons
    JPanel navBar;

    MainDashboard() {
        setTitle("Main Dashboard");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ===== Background Image =====
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        Image scaledImg = img.getImage().getScaledInstance(1300, 700, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImg));
        background.setBounds(0, 0, 1300, 700);
        background.setLayout(null);

        // ===== Nav Bar =====
        navBar = new JPanel(new GridLayout(1, 3));
        navBar.setBackground(new Color(0, 0, 0, 200));
        navBar.setBounds(0, 0, 1300, 60);
        btnBack = new JButton("Back");
        btnHome = new JButton("Exit");

        Font navFont = new Font("Arial", Font.BOLD, 20);
        btnBack.setFont(navFont);
        btnHome.setFont(navFont);
  
        navBar.add(btnBack);
        navBar.add(btnHome);


        // ===== Dashboard Buttons =====
        btn1 = new JButton("ADMINSTRATION");
        btn2 = new JButton("DOCTORS");
        btn3 = new JButton("NURSES");
        btn4 = new JButton("RECEPTION");

        JButton[] allBtns = {btn1, btn2, btn3, btn4};
        for (JButton b : allBtns) {
            b.setFont(new Font("Arial", Font.BOLD, 28));
            b.setBackground(Color.WHITE);
            b.setFocusable(false);
        }

        // Use GridBagLayout for positioning inside a panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // transparent to show background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        centerPanel.add(btn1, gbc);
        gbc.gridy = 1;
        centerPanel.add(btn2, gbc);
        gbc.gridy = 2;
        centerPanel.add(btn3, gbc);
        gbc.gridy = 3;
        centerPanel.add(btn4, gbc);

        centerPanel.setBounds(400, 150, 400, 400); // position center panel

        // ===== Add Components =====
        background.add(navBar);
        background.add(centerPanel);
        add(background);

        setVisible(true);

        // ===== Action Listeners =====
        btn1.addActionListener(e -> {
            new login("ADMINSTRATION");
            setVisible(false);
        });
        btn2.addActionListener(e -> {
            new login("DOCTORS");
            setVisible(false);
        });
        btn3.addActionListener(e -> {
            new login("NURSES");
            setVisible(false);
        });
        btn4.addActionListener(e -> {
            new login("RECEPTION");
            setVisible(false);
        });

        btnBack.addActionListener(e -> {
            new HomePage();  // you can link to AdminPage() if you want instead
            setVisible(false);
        });
        btnHome.addActionListener(e -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        new MainDashboard();
    }
}