/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectgui2025;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class SickLeave extends JFrame {
    JLabel title, patientLabel, doctorLabel, startLabel, endLabel, reasonLabel, notesLabel, image;
    JDateChooser startDate, endDate;
    JTextField patientId;
    JTextArea reasonText, notesText;
    JButton save, btnBack, btnHome, btnLogout;
    JPanel navBar, panel;
    int doctorId;

    SickLeave(String username) {
       
        setTitle("Sick Leave");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, 1300, 700);
        image.setLayout(null);

        // Main panel
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

        // Title
        title = new JLabel("Sick Leave");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        // Labels and Inputs
        patientLabel = new JLabel("Patient ID:");
        patientLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientId=new JTextField(); // تملى من DB لاحقًا

        doctorLabel = new JLabel("Doctor:");
        doctorLabel.setFont(new Font("Arial", Font.BOLD, 20));
     

        startLabel = new JLabel("Start Date:");
        startLabel.setFont(new Font("Arial", Font.BOLD, 20));
        startDate = new JDateChooser();
        startDate.setDateFormatString("yyyy-MM-dd");

        endLabel = new JLabel("End Date:");
        endLabel.setFont(new Font("Arial", Font.BOLD, 20));
        endDate = new JDateChooser();
        endDate.setDateFormatString("yyyy-MM-dd");

        reasonLabel = new JLabel("Reason:");
        reasonLabel.setFont(new Font("Arial", Font.BOLD, 20));
        reasonText = new JTextArea();

        notesLabel = new JLabel("Notes:");
        notesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        notesText = new JTextArea();

        save = new JButton("Save");
        btnBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            new DoctorPage("");
            }
            
        });
        btnHome.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            new HomePage();
            }
            
        });
        btnLogout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            new login("");
            }
            
        });
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/cms";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
                java.sql.Statement s=(java.sql.Statement) con.createStatement();
                    int patId=Integer.parseInt(patientId.getText().trim());
                    int doctId=getDoctorIdByUsername(username);
                    int VisitID=getVisitId(patId,doctId);
                    if(doctId==-1){
                        JOptionPane.showMessageDialog(null, "Doctor nut found for username"+username);
                        return;
                    }
                    if(VisitID==-0){
                        JOptionPane.showMessageDialog(null, "Not visit found for this patient with the selected doctor");
                        return;
                    }
                    java.util.Date startdate=startDate.getDate();
                    java.util.Date enddate=endDate.getDate();
                    java.sql.Date start=new java.sql.Date(startdate.getTime());
                    java.sql.Date end=new java.sql.Date(enddate.getTime());
                    String sql1 = "INSERT INTO sick_leave "
               + "(patient_id, doctor_id, visit_id, start_date, end_date, reason, notes) "
               + "VALUES (?,?,?,?,?,?,?)";
    PreparedStatement pst = con.prepareStatement(sql1);
    pst.setInt(1, patId);
    pst.setInt(2, doctId);
    pst.setInt(3, VisitID);
    pst.setDate(4, start);
    pst.setDate(5, end);
    pst.setString(6,reasonText.getText() );
        pst.setString(7,notesText.getText() );
        int rows=pst.executeUpdate();
        if(rows>0){
            JOptionPane.showMessageDialog(null, "Sick Leave saved successfully");
        }

                    
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "error"+ex.getMessage());
                }
            }
            
        });

        // Setting bounds
        title.setBounds(500, 10, 400, 30);
        patientLabel.setBounds(50, 50, 150, 30);
        patientId.setBounds(200, 50, 300, 30);

        
        

        startLabel.setBounds(50, 110, 150, 30);
        startDate.setBounds(200, 110, 200, 30);

        endLabel.setBounds(450, 110, 150, 30);
        endDate.setBounds(600, 110, 200, 30);

        reasonLabel.setBounds(50, 180, 150, 30);
        reasonText.setBounds(200, 180, 300, 100);

        notesLabel.setBounds(550, 180, 150, 30);
        notesText.setBounds(700, 180, 300, 100);

        save.setBounds(100, 320, 100, 50);

        // Adding components to panel
        panel.add(title);
        panel.add(patientLabel);
        panel.add(patientId);
        panel.add(doctorLabel);
        
        panel.add(startLabel);
        panel.add(startDate);
        panel.add(endLabel);
        panel.add(endDate);
        panel.add(reasonLabel);
        panel.add(reasonText);
        panel.add(notesLabel);
        panel.add(notesText);
        panel.add(save);

        // Add navBar and panel to image
        image.add(navBar);
        image.add(panel);

        add(image);
        setVisible(true);
    }
private int getDoctorIdByUsername(String username){
        int id = -1;
        try{
             Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/cms";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
            String sql = "SELECT doctorid FROM doctor WHERE doctorusername=?";
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            java.sql.ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                id = rs.getInt("doctorid");}
            con.close();
        }catch(Exception ex){
            ex.printStackTrace(); }
        return id;
    }
private int getVisitId(int patID,int doctId) {
        int visitId = 0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
            String sql = "SELECT visit_id FROM medical_visit WHERE patient_id = ? and doctor_id=? order by visit_date desc limit 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, patID);
            ps.setInt(2, doctId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                visitId = rs.getInt("visit_id");
            }
            rs.close();
            ps.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return visitId;
    }
    public static void main(String[] args) {
        new SickLeave("");
    }
}

