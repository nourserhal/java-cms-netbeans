package projectgui2025;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VitalsSigns extends JFrame {
    JLabel image, title, nurseLabel, tempLabel, bpLabel, weightLabel, heightLabel, hbLabel, respLabel, noteLabel;
    JTextField tempField, bpField, weightField, heightField, hbField, respField;
    JTextArea noteArea;
    JButton btnSave, btnClear;
    JButton btnBack, btnHome, btnLogout;
    JPanel panel, navBar;

    private int patientId, appointmentId, nurseId;
    private boolean editable;

    public VitalsSigns(int patientId, int appointmentId, boolean editable, int nurseId) {
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.editable = editable;
        this.nurseId = nurseId;
      
        setTitle("Vitals Signs Form");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, getWidth(), getHeight());
        image.setLayout(null);

        // NavBar
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

        // Nurse label (for doctor view)
        nurseLabel = new JLabel();
        nurseLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nurseLabel.setBounds(50, 90, 500, 30);
        image.add(nurseLabel);

        // Labels
        title = new JLabel(editable ? "Add Vitals Signs" : "View Vitals Signs");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        tempLabel = new JLabel("Temperature:");
        bpLabel = new JLabel("Blood Pressure:");
        weightLabel = new JLabel("Weight:");
        heightLabel = new JLabel("Height:");
        hbLabel = new JLabel("Heart Beat:");
        respLabel = new JLabel("Respiration Rate:");
        noteLabel = new JLabel("Note:");
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        tempLabel.setFont(labelFont);
        bpLabel.setFont(labelFont);
        weightLabel.setFont(labelFont);
        heightLabel.setFont(labelFont);
        hbLabel.setFont(labelFont);
        respLabel.setFont(labelFont);
        noteLabel.setFont(labelFont);

        // Fields
        tempField = new JTextField();
        bpField = new JTextField();
        weightField = new JTextField();
        heightField = new JTextField();
        hbField = new JTextField();
        respField = new JTextField();
        noteArea = new JTextArea();
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);

        // Buttons
        btnSave = new JButton("Save");
        btnClear = new JButton("Clear");

        // Panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 130, 1100, 500);

        // Bounds
        title.setBounds(450, 10, 400, 30);
        tempLabel.setBounds(50, 50, 200, 30);
        tempField.setBounds(240, 50, 200, 30);
        bpLabel.setBounds(50, 100, 200, 30);
        bpField.setBounds(240, 100, 200, 30);
        weightLabel.setBounds(500, 50, 200, 30);
        weightField.setBounds(680, 50, 200, 30);
        heightLabel.setBounds(500, 100, 200, 30);
        heightField.setBounds(680, 100, 200, 30);
        hbLabel.setBounds(50, 150, 200, 30);
        hbField.setBounds(240, 150, 200, 30);
        respLabel.setBounds(500, 150, 200, 30);
        respField.setBounds(680, 150, 200, 30);
        noteLabel.setBounds(50, 200, 200, 30);
        noteArea.setBounds(240, 200, 640, 100);
        btnSave.setBounds(300, 330, 100, 40);
        btnClear.setBounds(500, 330, 100, 40);

        // Add to panel
        panel.add(title);
        panel.add(tempLabel); panel.add(tempField);
        panel.add(bpLabel); panel.add(bpField);
        panel.add(weightLabel); panel.add(weightField);
        panel.add(heightLabel); panel.add(heightField);
        panel.add(hbLabel); panel.add(hbField);
        panel.add(respLabel); panel.add(respField);
        panel.add(noteLabel); panel.add(noteArea);
        if (editable) {
            panel.add(btnSave);
            panel.add(btnClear);
        }

        // Load existing vitals if doctor view
        if (!editable) loadVitals();

        // Disable fields for doctor view
        if (!editable) {
            tempField.setEditable(false);
            bpField.setEditable(false);
            weightField.setEditable(false);
            heightField.setEditable(false);
            hbField.setEditable(false);
            respField.setEditable(false);
            noteArea.setEditable(false);
        }

        // Save action
        btnSave.addActionListener(e -> saveVitals());

        // Clear action
        btnClear.addActionListener(e -> {
            tempField.setText(""); bpField.setText("");
            weightField.setText(""); heightField.setText("");
            hbField.setText(""); respField.setText("");
            noteArea.setText("");
        });

        // Navigation
        btnHome.addActionListener(e -> {
            new MainDashboard();
            setVisible(false);
        });
        btnLogout.addActionListener(e -> {
            new login("");
            setVisible(false);
        });
        btnBack.addActionListener(e -> {
            new AppointmentCheck("Nurse", nurseId);
            setVisible(false);
        });

        // Add
        image.add(navBar);
        image.add(panel);
        add(image);

        setVisible(true);
    }

    private void saveVitals() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); // older driver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");
            String sql = "INSERT INTO vitalsigns (appointmentid, patientid, nurseid, temperature, bloodpressure, weight, height, heartrate, respirationrate, note) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.setInt(2, patientId);
            ps.setInt(3, nurseId);
            ps.setString(4, tempField.getText());
            ps.setString(5, bpField.getText());
            ps.setString(6, weightField.getText());
            ps.setString(7, heightField.getText());
            ps.setString(8, hbField.getText());
            ps.setString(9, respField.getText());
            ps.setString(10, noteArea.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vitals saved successfully!");
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving vitals.");
        }
    }

    private void loadVitals() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");
            String sql = "SELECT v.*, n.nursename FROM vitalsigns v JOIN nurse n ON v.nurseid = n.nurseid WHERE v.appointmentid=? AND v.patientid=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.setInt(2, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tempField.setText(rs.getString("temperature"));
                bpField.setText(rs.getString("bloodpressure"));
                weightField.setText(rs.getString("weight"));
                heightField.setText(rs.getString("height"));
                hbField.setText(rs.getString("heartrate"));
                respField.setText(rs.getString("respirationrate"));
                noteArea.setText(rs.getString("note"));
                nurseLabel.setText("Vitals recorded by Nurse: " + rs.getString("nursename"));
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading vitals.");
        }
    }

    public static void main(String[] args) {
        new VitalsSigns(1, 1, true, 1); // Example: nurse adds vitals
    }
}