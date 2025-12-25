package projectgui2025;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PatientHistoryPage extends JFrame {
    JTable table;
    DefaultTableModel tableModel;
    JButton btnBack, btnSearch, btnHome, btnLogout;
    JLabel title, lblPatientId;
    JTextField txtPatientId;
    JPanel navBar;

    public PatientHistoryPage() {    
        setTitle("Patient Medical History");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); // use absolute layout for background

        // ===== BACKGROUND IMAGE =====
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, 1200, 700);
        background.setLayout(null);
        this.add(background);

        // ===== NAVBAR =====
        navBar = new JPanel(new GridLayout(1, 3));
        navBar.setBackground(new Color(0, 0, 0, 200));
        navBar.setBounds(0, 0, getWidth(), 80);
        btnBack = new JButton("Back");
        btnHome = new JButton("Home Page");
        btnLogout = new JButton("Logout");
        Font navFont = new Font("Arial", Font.BOLD, 20);
        btnBack.setFont(navFont);
        btnHome.setFont(navFont);
        btnLogout.setFont(navFont);
        navBar.add(btnBack);
        navBar.add(btnHome);
        navBar.add(btnLogout);
        background.add(navBar);

        // ===== TITLE =====
        title = new JLabel("Patient Medical History");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBounds(300, 90, 600, 40);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(title);

        // ===== SEARCH PANEL =====
        lblPatientId = new JLabel("Enter Patient ID:");
        lblPatientId.setBounds(200, 150, 150, 30);
        txtPatientId = new JTextField();
        txtPatientId.setBounds(350, 150, 150, 30);
        btnSearch = new JButton("Search");
        btnSearch.setBounds(520, 150, 100, 30);
        background.add(lblPatientId);
        background.add(txtPatientId);
        background.add(btnSearch);

        // ===== TABLE =====
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Visit ID");
        tableModel.addColumn("Reservation ID");
        tableModel.addColumn("Visit Date");
        tableModel.addColumn("History");
        tableModel.addColumn("Shift Complain");
        tableModel.addColumn("Physical Exam");
        tableModel.addColumn("Diagnosis");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 200, 1100, 400);
        background.add(scrollPane);

        // ===== BUTTON ACTIONS =====
        btnSearch.addActionListener(e -> {
            String patientIdText = txtPatientId.getText().trim();
            if (!patientIdText.isEmpty()) {
                try {
                    int patientId = Integer.parseInt(patientIdText);
                    loadPatientHistory(patientId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Patient ID (number).");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a Patient ID.");
            }
        });

        btnBack.addActionListener(e -> {
            dispose(); // close this page
            // new PreviousPage(); // link to previous page if needed
        });

        btnHome.addActionListener(e -> {
            dispose();
            // new MainDashboard(); // link to main dashboard
        });

        btnLogout.addActionListener(e -> {
            dispose();
            // new Login(); // link to login page
        });

        setVisible(true);
    }

    private void loadPatientHistory(int patientId) {
        tableModel.setRowCount(0);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");
            String query = "SELECT visit_id, reservation_id, visit_date, history, shift_complain, physical_exam, diagnosis " +
                           "FROM medical_visit WHERE patient_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                Object[] row = {
                    rs.getInt("visit_id"),
                    rs.getInt("reservation_id"),
                    rs.getDate("visit_date"),
                    rs.getString("history"),
                    rs.getString("shift_complain"),
                    rs.getString("physical_exam"),
                    rs.getString("diagnosis")
                };
                tableModel.addRow(row);
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No medical history found for Patient ID: " + patientId);
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading patient history");
        }
    }

    public static void main(String[] args) {
        new PatientHistoryPage();
    }
}