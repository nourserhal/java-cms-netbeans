/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class PatientUpdate extends JFrame {
    JLabel image, pname, padress, pphone, pregisternb, pgender, pnationality,
            pstatus, pbirth, presedate, pbloodtype, title,patientid;
    JTextField name, address, phone, reginb,pid;
    JComboBox status, nationality, gender, bloodtype;
    JDateChooser birth, date;
    JButton  btn2, btn3, btn4;

    // Nav bar buttons
    JButton btnBack, btnHome, btnLogout;

    JPanel panel, navBar;

    PatientUpdate() {
        setTitle("Patient Update");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, 1300, getHeight());
        image.setOpaque(true);

        // Navigation bar
        navBar = new JPanel(new GridLayout(1, 3));
        navBar.setBackground(new Color(0, 0, 0, 200)); // semi-transparent black
        navBar.setBounds(0, 0,1300, 80);

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

        // Form labels and fields
        pname = new JLabel("Patient Name");
        padress = new JLabel("Patient Address");
        pphone = new JLabel("Patient Phone");
        pregisternb = new JLabel("Register Number");
        pgender = new JLabel("Patient Gender");
        pnationality = new JLabel("Patient Nationality");
        pstatus = new JLabel("Patient Status");
        pbirth = new JLabel("Patient Birthday");
        presedate = new JLabel("Patient Registration Date");
        pbloodtype = new JLabel("Patient Blood Type");
        title = new JLabel("Patient Form");
        patientid=new JLabel("Patient Id");

        birth = new JDateChooser();
        date = new JDateChooser();
        birth.setDateFormatString("dd/MM/yyyy");
        date.setDateFormatString("dd/MM/yyyy");

        name = new JTextField(20);
        address = new JTextField(20);
        phone = new JTextField(20);
        reginb = new JTextField(20);
        pid=new JTextField(20);

        String[] stat = {" ", "Single", "Married", "Divorced"};
        String[] natio = {" ", "Lebanon", "Spanish", "France", "America", "Egypt", "Syria", "Iraq", "UAE", "Algeria","Palestin"};
        String[] gend = {" ", "Male", "Female"};
        String[] bll = {" ", "A+", "B+", "O+", "AB", "A-", "B-", "O-"};

        status = new JComboBox(stat);
        nationality = new JComboBox(natio);
        gender = new JComboBox(gend);
        bloodtype = new JComboBox(bll);

        
        btn2 = new JButton("Update");
        btn3 = new JButton("Delete");
        btn4 = new JButton("Clear");

        // Fonts
        title.setFont(new Font("Arial", Font.BOLD, 30));
        pname.setFont(new Font("Arial", Font.BOLD, 20));
        padress.setFont(new Font("Arial", Font.BOLD, 20));
        pphone.setFont(new Font("Arial", Font.BOLD, 20));
        pregisternb.setFont(new Font("Arial", Font.BOLD, 20));
        pgender.setFont(new Font("Arial", Font.BOLD, 20));
        pnationality.setFont(new Font("Arial", Font.BOLD, 20));
        pstatus.setFont(new Font("Arial", Font.BOLD, 20));
        presedate.setFont(new Font("Arial", Font.BOLD, 20));
        pbloodtype.setFont(new Font("Arial", Font.BOLD, 20));
        pbirth.setFont(new Font("Arial", Font.BOLD, 20));
        patientid.setFont(new Font("Arial", Font.BOLD, 20));


        // Panel for form
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        // Bounds for labels and fields
        title.setBounds(450, 10, 300, 30);
        patientid.setBounds(50,50,200,30);
        pid.setBounds(240,50,200,30);
        pname.setBounds(50, 110, 200, 30);
        name.setBounds(240, 110, 200, 30);
        padress.setBounds(500, 50, 200, 30);
        address.setBounds(680, 50, 200, 30);
        pphone.setBounds(50, 170, 200, 30);
        phone.setBounds(240, 170, 200, 30);
        pregisternb.setBounds(500, 110, 200, 30);
        reginb.setBounds(680, 110, 200, 30);
        pgender.setBounds(50, 230, 200, 30);
        gender.setBounds(240, 230, 200, 30);
        pnationality.setBounds(500, 170, 200, 30);
        nationality.setBounds(680, 170, 200, 30);
        pstatus.setBounds(50, 290, 200, 30);
        status.setBounds(240, 290, 200, 30);
        pbirth.setBounds(500, 230, 200, 30);
        birth.setBounds(680, 230, 200, 30);
        pbloodtype.setBounds(290, 340, 200, 30);
        bloodtype.setBounds(490, 340, 200, 30);
        presedate.setBounds(500, 290, 250, 30);
        date.setBounds(740, 290, 200, 30);

        // Buttons bounds
       
        btn2.setBounds(300, 400, 100, 40);
        btn3.setBounds(500, 400, 100, 40);
        btn4.setBounds(700, 400, 100, 40);
        
        btn2.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (name.getText().isEmpty() || address.getText().isEmpty() || phone.getText().isEmpty()
                || reginb.getText().isEmpty() || birth.getDate() == null || date.getDate() == null) {
                
                JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
                return;
            }

            Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = DriverManager.getConnection(myUrl, "root", "");

            String sql = "UPDATE patients SET patientname=?, patientaddress=?, patientphone=?, "
                       + "registernumber=?, patientgender=?, patientnationality=?, patientstatus=?, "
                       + "patientbirthday=?, patientregistrationdate=?, patientbloodtype=? "
                       + "WHERE patientid=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name.getText());
            ps.setString(2, address.getText());
            ps.setString(3, phone.getText());
            ps.setString(4, reginb.getText());
            ps.setString(5, gender.getSelectedItem().toString());
            ps.setString(6, nationality.getSelectedItem().toString());
            ps.setString(7, status.getSelectedItem().toString());
            ps.setDate(8, new java.sql.Date(birth.getDate().getTime()));
            ps.setDate(9, new java.sql.Date(date.getDate().getTime()));
            ps.setString(10, bloodtype.getSelectedItem().toString());

            // مهم: هون لازم تعطي patientid المريض يلي بدك تعدل عليه
            ps.setInt(11, Integer.parseInt(pid.getText()));  

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Patient information updated successfully ✅");
            } else {
                JOptionPane.showMessageDialog(null, "No patient found with this ID ❌");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error while updating patient ❌");
            ex.printStackTrace();
        }
    }
});
        btn3.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (pid.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Patient ID to delete ⚠️");
                return;
            }

            // رسالة تأكيد قبل الحذف
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete patient with ID: " + pid.getText() + " ?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                Class.forName("com.mysql.jdbc.Driver");
                String myUrl = "jdbc:mysql://localhost:3306/cms";
                Connection con = DriverManager.getConnection(myUrl, "root", "");

                String sql = "DELETE FROM patients WHERE patientid=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(pid.getText()));

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Patient deleted successfully 🗑️");
                } else {
                    JOptionPane.showMessageDialog(null, "No patient found with this ID ❌");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Deletion cancelled ❌");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error while deleting patient ❌");
            ex.printStackTrace();
        }
    }
});



        
        btnLogout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new login("");
                setVisible(false);
             }
            
        });
         btnBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReceptionPage("");
                setVisible(false);
             }
         
         
         });
        
        btnHome.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage();
                setVisible(false);
            }
            
        });
        // Clear button action
        

        
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.setText("");
                address.setText("");
                phone.setText("");
                reginb.setText("");
                gender.setSelectedIndex(0);
                status.setSelectedIndex(0);
                nationality.setSelectedIndex(0);
                bloodtype.setSelectedIndex(0);
                birth.setDate(null);
                date.setDate(null);
            }
        });

        // Add components to panel
        panel.add(title);
        panel.add(patientid);
        panel.add(pid);
        panel.add(pname);
        panel.add(name);
        panel.add(padress);
        panel.add(address);
        panel.add(pphone);
        panel.add(phone);
        panel.add(pregisternb);
        panel.add(reginb);
        panel.add(pgender);
        panel.add(gender);
        panel.add(pnationality);
        panel.add(nationality);
        panel.add(pstatus);
        panel.add(status);
        panel.add(pbirth);
        panel.add(birth);
        panel.add(pbloodtype);
        panel.add(bloodtype);
        panel.add(presedate);
        panel.add(date);
   
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);

        // Add to frame
        add(panel);
        add(navBar);
        add(image);

        setVisible(true);
    }

    public static void main(String[] args) {
        new PatientUpdate();
    }
}


