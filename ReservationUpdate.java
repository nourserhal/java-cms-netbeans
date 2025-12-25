/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectgui2025;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationUpdate extends JFrame {
    JLabel image, dateLabel, timeLabel,IdLabel, patientIdLabel, doctorIdLabel, notesLabel, title;
    JDateChooser reservationDate;
    JSpinner timeSpinner;
    JTextField patientIdField ,IDReser;
    JTextArea notesArea;
    JComboBox doctorIdField;
    JButton btnAdd, btnUpdate, btnDelete, btnClear;

    // Nav bar buttons
    JButton btnBack, btnHome, btnSave;

    JPanel panel, navBar;

    ReservationUpdate() {
        setTitle("ReservationForm");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, 1300, 700);
        image.setOpaque(true);

        // Navigation bar
        navBar = new JPanel(new GridLayout(1, 3));
        navBar.setBackground(new Color(0, 0, 0, 200));
        navBar.setBounds(0, 0, 1300, 80);
        btnBack = new JButton("Back");
        btnHome = new JButton("Home Page");
        btnSave = new JButton("Logout");
        Font navFont = new Font("Arial", Font.BOLD, 20);
        btnBack.setFont(navFont);
        btnHome.setFont(navFont);
        btnSave.setFont(navFont);
        navBar.add(btnBack);
        navBar.add(btnHome);
        navBar.add(btnSave);

        // Form labels and fields
        title = new JLabel("Reservation Update");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        IdLabel=new JLabel("Reservation Id");
        dateLabel = new JLabel("Reservation Date");
        timeLabel = new JLabel("Reservation Time");
        patientIdLabel = new JLabel("Patient Name");
        doctorIdLabel = new JLabel("Doctor Name");
        notesLabel = new JLabel("Notes");

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        dateLabel.setFont(labelFont);
        timeLabel.setFont(labelFont);
        patientIdLabel.setFont(labelFont);
        doctorIdLabel.setFont(labelFont);
        notesLabel.setFont(labelFont);
        IdLabel.setFont(labelFont);

        reservationDate = new JDateChooser();
        reservationDate.setDateFormatString("dd/MM/yyyy");

        // Time spinner
        SpinnerDateModel model = new SpinnerDateModel();
        timeSpinner = new JSpinner(model);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date()); // default current time

        patientIdField = new JTextField();
        IDReser=new JTextField();
        doctorIdField = new JComboBox();
        notesArea = new JTextArea();
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);

        // Action buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        try {
                Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/cms";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
                java.sql.Statement s=(java.sql.Statement) con.createStatement();
                String sql=("select doctorname from doctor");
                ResultSet rs=s.executeQuery(sql);
                while(rs.next()){
                    doctorIdField.addItem(rs.getString("doctorname"));
                }
             s.close();
             con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NurseForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(NurseForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        btnDelete.addActionListener(e -> {
    String idText = IDReser.getText().trim();

    if (idText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter Reservation ID to delete.");
        return;
    }

    try {
        int rId = Integer.parseInt(idText);

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this reservation?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM reservation WHERE reservationid=?"
            );
            ps.setInt(1, rId);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Reservation deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Reservation ID not found.");
            }

            con.close();
        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Invalid ID format.");
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(ReceptionUpdate.class.getName()).log(Level.SEVERE, null, ex);
    }
});

        btnUpdate.addActionListener(e -> {
    String idText = IDReser.getText().trim();

    if (idText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter Reservation ID to update.");
        return;
    }

    try {
        int rId = Integer.parseInt(idText);

        if (reservationDate.getDate() == null 
                || timeSpinner.getValue() == null
                || patientIdField.getText().trim().isEmpty()
                || doctorIdField.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
            return;
        }

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");

        // 1) جيب patientid من جدول patient حسب الاسم
        int patientId = -1;
        PreparedStatement psPatient = con.prepareStatement("SELECT patientid FROM patient WHERE patientname=?");
        psPatient.setString(1, patientIdField.getText().trim());
        ResultSet rsPatient = psPatient.executeQuery();
        if (rsPatient.next()) {
            patientId = rsPatient.getInt("patientid");
        } else {
            JOptionPane.showMessageDialog(null, "Patient not found!");
            con.close();
            return;
        }

        // 2) جيب doctorid من جدول doctor حسب الاسم
        int doctorId = -1;
        String doctorName = doctorIdField.getSelectedItem().toString();
        PreparedStatement psDoctor = con.prepareStatement("SELECT doctorid FROM doctor WHERE doctorname=?");
        psDoctor.setString(1, doctorName);
        ResultSet rsDoctor = psDoctor.executeQuery();
        if (rsDoctor.next()) {
            doctorId = rsDoctor.getInt("doctorid");
        } else {
            JOptionPane.showMessageDialog(null, "Doctor not found!");
            con.close();
            return;
        }

        // 3) اعمل Update على جدول reservation
        PreparedStatement ps = con.prepareStatement(
            "UPDATE reservation SET reservationdate=?, reservationtime=?, patientid=?, doctorid=?, notes=? WHERE reservationid=?"
        );

        java.sql.Date sqlDate = new java.sql.Date(reservationDate.getDate().getTime());
         // 1) جيب الوقت من JSpinner كـ Date
java.util.Date utilTime = (java.util.Date) timeSpinner.getValue();

// 2) حوله لـ java.sql.Time

java.sql.Time sqlTime = new java.sql.Time(utilTime.getTime());

// 3) حط الوقت كـ parameter بالـ PreparedStatement
        ps.setDate(1, sqlDate);
        ps.setTime(2, sqlTime);
        ps.setInt(3, patientId);
        ps.setInt(4, doctorId);
        ps.setString(5, notesArea.getText());
        ps.setInt(6, rId);

        int rowsUpdated = ps.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Reservation updated successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Reservation ID not found.");
        }

        con.close();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Invalid ID format.");
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(ReceptionUpdate.class.getName()).log(Level.SEVERE, null, ex);
    }
});

        
        btnHome.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage();
                setVisible(false);
            }
            
        });
        btnSave.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new login("");
                setVisible(false);
            }
            
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservationDate.setDate(null);
                timeSpinner.setValue(new Date());
                patientIdField.setText("");
                doctorIdField.setSelectedItem("");
                notesArea.setText("");
            }
        });

        // Panel for form
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        // Set bounds
        
        title.setBounds(450, 10, 300, 30);
        IdLabel.setBounds(50, 50, 200, 30);
        IDReser.setBounds(240,50,200,30);
        dateLabel.setBounds(50, 110, 200, 30);
        reservationDate.setBounds(240, 110, 200, 30);
        timeLabel.setBounds(500, 50, 200, 30);
        timeSpinner.setBounds(680, 50, 200, 30);
        patientIdLabel.setBounds(50, 170, 200, 30);
        patientIdField.setBounds(240, 170, 200, 30);
        doctorIdLabel.setBounds(500, 110, 200, 30);
        doctorIdField.setBounds(680, 110, 200, 30);
        notesLabel.setBounds(50, 220, 200, 30);
        notesArea.setBounds(240, 220, 640, 100);

      
        btnUpdate.setBounds(300, 360, 100, 40);
        btnDelete.setBounds(500, 360, 100, 40);
        btnClear.setBounds(700, 360, 100, 40);

        // Add components to panel
        panel.add(title);
        panel.add(IdLabel);
        panel.add(IDReser);
        panel.add(dateLabel);
        panel.add(reservationDate);
        panel.add(timeLabel);
        panel.add(timeSpinner);
        panel.add(patientIdLabel);
        panel.add(patientIdField);
        panel.add(doctorIdLabel);
        panel.add(doctorIdField);
        panel.add(notesLabel);
        panel.add(notesArea);
    
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);

        // Add to frame
        add(panel);
        add(navBar);
        add(image);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReservationUpdate();
    }
}