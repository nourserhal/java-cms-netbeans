package projectgui2025;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;

public class StartExaminationPage extends JFrame {
    JLabel visitDate, history, shiftsC, physicalexam, diagnosis, title, image;
    JTextArea hist, shift, physexm, diag;
    JDateChooser Date;
    JButton save, btnBack, btnHome, btnLogout;
    JPanel navBar, panel;

    private int patientId, reservationId, doctorId;

    public StartExaminationPage(int patientId, int reservationId, int doctorId) {
        this.patientId = patientId;
        this.reservationId = reservationId;
        this.doctorId = doctorId;

        setTitle("Start Examination");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, 1300, 700);
        image.setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        // Navigation bar
        navBar = new JPanel(new GridLayout(1, 3));
        navBar.setBackground(new Color(0, 0, 0, 200));
        navBar.setBounds(0, 0, 1300, 80);
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

        // Labels and text areas
        title = new JLabel("Visits Date");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        visitDate = new JLabel("Visits Date:");
        visitDate.setFont(new Font("Arial", Font.BOLD, 20));
        Date = new JDateChooser();
        Date.setDateFormatString("yyyy-MM-dd");

        history = new JLabel("History:");
        history.setFont(new Font("Arial", Font.BOLD, 20));
        hist = new JTextArea();

        shiftsC = new JLabel("Shift Complain:");
        shiftsC.setFont(new Font("Arial", Font.BOLD, 20));
        shift = new JTextArea();

        physicalexam = new JLabel("Physical Exam:");
        physicalexam.setFont(new Font("Arial", Font.BOLD, 20));
        physexm = new JTextArea();

        diagnosis = new JLabel("Diagnosis:");
        diagnosis.setFont(new Font("Arial", Font.BOLD, 20));
        diag = new JTextArea();

        save = new JButton("Save");

        // Set bounds
        title.setBounds(460, 10, 400, 30);
        visitDate.setBounds(50, 50, 200, 30);
        Date.setBounds(200, 50, 200, 30);
        history.setBounds(50, 110, 200, 30);
        hist.setBounds(150, 110, 300, 100);
        shiftsC.setBounds(500, 110, 200, 30);
        shift.setBounds(680, 110, 300, 100);
        physicalexam.setBounds(50, 300, 200, 30);
        physexm.setBounds(200, 300, 300, 100);
        diagnosis.setBounds(550, 300, 200, 30);
        diag.setBounds(680, 300, 300, 100);
        save.setBounds(100, 450, 100, 50);

        // Add components to panel
        panel.add(title);
        panel.add(visitDate);
        panel.add(Date);
        panel.add(history);
        panel.add(hist);
        panel.add(shiftsC);
        panel.add(shift);
        panel.add(physicalexam);
        panel.add(physexm);
        panel.add(diagnosis);
        panel.add(diag);
        panel.add(save);

        // Add to frame
        image.add(navBar);
        image.add(panel);
        add(image);
        setVisible(true);

        // Save action
        save.addActionListener(e -> {
            try {
                Class.forName("com.mysql.jdbc.Driver"); // old driver
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");

                String query = "INSERT INTO medical_visit (reservation_id, patient_id, doctor_id, visit_date, history, shift_complain, physical_exam, diagnosis) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, reservationId);
                ps.setInt(2, patientId);
                ps.setInt(3, doctorId);
                ps.setDate(4, new java.sql.Date(Date.getDate().getTime()));
                ps.setString(5, hist.getText());
                ps.setString(6, shift.getText());
                ps.setString(7, physexm.getText());
                ps.setString(8, diag.getText());

                ps.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(null, "Examination saved successfully!");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving examination.");
            }
        });

        // Navigation buttons (example)
        btnBack.addActionListener(e -> {
            new AppointmentCheck("Doctor", doctorId); // return to appointment check
            setVisible(false);
        });
        btnHome.addActionListener(e -> {
            new MainDashboard();
            setVisible(false);
        });
        btnLogout.addActionListener(e -> {
            new login("Doctor");
            setVisible(false);
        });
    }

    public static void main(String[] args) {
        // Example: patientId=1, reservationId=1, doctorId=1
        new StartExaminationPage(1, 1, 1);
    }
}