package projectgui2025;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdminPage extends JFrame {
    JButton btn1, btn2, btn3, btn4;
    JButton backBtn, homeBtn, logoutBtn;
    String username;

    AdminPage(String username) {
        this.username=username;
        // ---------------- BACKGROUND PANEL ----------------
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 700));

        // Background image label
        JLabel bgLabel = new JLabel(new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg")));
        bgLabel.setBounds(0, 0, 1300, 700);

        // ---------------- NAVBAR ----------------
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setBackground(new Color(0, 0, 0, 180));
        navBar.setBounds(0, 0, 1300, 60);

        backBtn = new JButton("BACK");
        homeBtn = new JButton("HOMEPAGE");
        logoutBtn = new JButton("LOGOUT");

        JButton[] navButtons = {backBtn, homeBtn, logoutBtn};
        for (JButton b : navButtons) {
            b.setFont(new Font("Arial", Font.BOLD, 30));
           
            b.setFocusable(false);
        }

        navBar.add(backBtn);
        navBar.add(homeBtn);
        navBar.add(logoutBtn);

        // ---------------- CENTER PANEL ----------------
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBounds(250, 150, 700, 400); // Positioned centrally

        btn1 = new JButton("ADD DOCTORS");
        btn2 = new JButton("ADD NURSES");
        btn3 = new JButton("ADD RECEPTION");
        btn4 = new JButton("ADD DEPARTMENT");

        JButton[] centerButtons = {btn1, btn2, btn3, btn4};
        for (JButton b : centerButtons) {
            b.setFont(new Font("Arial", Font.BOLD, 30));
            b.setBackground(Color.WHITE);
            b.setFocusable(false);
        }
        btn1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorForm();
                setVisible(false);
            }
            
            
        });
        btn2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new NurseForm();
                setVisible(false);
            }
            
            
        });
        btn3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReceptionForm();
                setVisible(false);
            }
            
            
        });
        btn4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepartmentForm();
                setVisible(false);
            }
            
            
        });
        backBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new login("");
                setVisible(false);
            }
            
            
        });
        homeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage();
                setVisible(false);
            }
            
            
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(btn1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(btn2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(btn3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(btn4, gbc);

        // ---------------- LAYERING ----------------
        layeredPane.add(bgLabel, Integer.valueOf(0));       // Background layer
        layeredPane.add(navBar, Integer.valueOf(1));        // Navbar layer
        layeredPane.add(centerPanel, Integer.valueOf(2));   // Buttons layer

        // ---------------- FRAME SETTINGS ----------------
        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 700);
        setLocationRelativeTo(null);
       setVisible(true);
    }

    
}