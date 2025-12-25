/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectgui2025;

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
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorForm extends JFrame {
    JLabel image, dNameLabel, dSpecialityLabel, dPhoneLabel, dEmailLabel, dUsernameLabel, dPasswordLabel, dDepartmentLabel, title;
    JTextField dNameField, dSpecialityField, dPhoneField, dEmailField, dUsernameField;
    JPasswordField dPasswordField;
    JComboBox departmentCombo;
    
    JButton btnAdd, btnUpdate, btnDelete, btnClear;
    
    // Nav bar buttons
    JButton btnBack, btnHome, btnSave;
    
    JPanel panel, navBar;
    
    DoctorForm() {
        setTitle("DoctorForm");
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
        navBar.setBounds(0, 0, getWidth(), 80);
        btnBack = new JButton("Back");
        btnHome = new JButton("Home Page");
        btnSave = new JButton("Save");
        Font navFont = new Font("Arial", Font.BOLD, 20);
        btnBack.setFont(navFont);
        btnHome.setFont(navFont);
        btnSave.setFont(navFont);
        navBar.add(btnBack);
        navBar.add(btnHome);
        navBar.add(btnSave);

        // Form labels and fields
        title = new JLabel("Doctor Form");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        dNameLabel = new JLabel("Doctor Name");
        dSpecialityLabel = new JLabel("Doctor Speciality");
        dPhoneLabel = new JLabel("Doctor Phone");
        dEmailLabel = new JLabel("Doctor Email");
        dUsernameLabel = new JLabel("Username");
        dPasswordLabel = new JLabel("Password");
        dDepartmentLabel = new JLabel("Department Name");

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        dNameLabel.setFont(labelFont);
        dSpecialityLabel.setFont(labelFont);
        dPhoneLabel.setFont(labelFont);
        dEmailLabel.setFont(labelFont);
        dUsernameLabel.setFont(labelFont);
        dPasswordLabel.setFont(labelFont);
        dDepartmentLabel.setFont(labelFont);

        dNameField = new JTextField();
        dSpecialityField = new JTextField();
        dPhoneField = new JTextField();
        dEmailField = new JTextField();
        dUsernameField = new JTextField();
        dPasswordField = new JPasswordField();

        
        departmentCombo = new JComboBox();
        departmentCombo.addItem("Select dep");

        // Action buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnUpdate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorUpdate();
            }
             
         });
         btnDelete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorUpdate();
            }
             
         });
        try {
                Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/cms";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
                java.sql.Statement s=(java.sql.Statement) con.createStatement();
                String sql=("select departementname from departement");
                ResultSet rs=s.executeQuery(sql);
                while(rs.next()){
                    
                    departmentCombo.addItem(rs.getString("departementname"));
                    
                }
             s.close();
             con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NurseForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(NurseForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    
       
         btnAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            try {
                
                if (dNameField.getText().isEmpty() || dSpecialityField.getText().isEmpty() || dPhoneField.getText().isEmpty() 
                || dEmailField.getText().isEmpty() || dUsernameField.getText() == null ) {
                
                JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
                return; 
            }
                Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/cms";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
                java.sql.Statement s=(java.sql.Statement) con.createStatement();
String doctorname = dNameField.getText();
String doctorspeciality = dSpecialityField.getText();
String doctorphone = dPhoneField.getText();
String doctoremail = dEmailField.getText(); // من الكومبو
String doctorusername = dUsernameField.getText();
String doctorpassword = new String(dPasswordField.getPassword());
String depname=departmentCombo.getSelectedItem().toString();


String depSql = "SELECT departementid FROM departement WHERE departementname=?";
PreparedStatement pstDep = con.prepareStatement(depSql);
pstDep.setString(1, depname);
ResultSet rsDep = pstDep.executeQuery();

if (rsDep.next()) {
    int departmentid = rsDep.getInt("departementid");
    String sql1 = "INSERT INTO doctor "
               + "(doctorname, doctorspeciality, doctorphone, departementid, doctoremail, doctorusername, doctorpassword) "
               + "VALUES (?,?,?,?,?,?,?)";
    PreparedStatement pst = con.prepareStatement(sql1);
    pst.setString(1, doctorname);
    pst.setString(2, doctorspeciality);
    pst.setString(3, doctorphone);
    pst.setInt(4, departmentid);
    pst.setString(5, doctoremail);
    pst.setString(6, doctorusername);
    pst.setString(7, doctorpassword);

    pst.executeUpdate();
    JOptionPane.showMessageDialog(null, "Doctor Added Successfully!");
    String loginQuery = "INSERT INTO login (username, password, role) VALUES (?, ?, 'Doctor')";
PreparedStatement psLogin = con.prepareStatement(loginQuery);
psLogin.setString(1, doctorusername);
psLogin.setString(2, doctorpassword);
psLogin.executeUpdate();
}
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NurseForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(NurseForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

});
        btnBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPage("Admin");
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
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dNameField.setText("");
                dSpecialityField.setText("");
                dPhoneField.setText("");
                dEmailField.setText("");
                dUsernameField.setText("");
                dPasswordField.setText("");
                departmentCombo.setSelectedIndex(0);
            }
        });

        // Panel for form
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        // Set bounds
        title.setBounds(450, 10, 300, 30);
        dNameLabel.setBounds(50, 50, 200, 30);
        dNameField.setBounds(240, 50, 200, 30);
        dSpecialityLabel.setBounds(500, 50, 200, 30);
        dSpecialityField.setBounds(680, 50, 200, 30);
        dPhoneLabel.setBounds(50, 110, 200, 30);
        dPhoneField.setBounds(240, 110, 200, 30);
        dEmailLabel.setBounds(500, 110, 200, 30);
        dEmailField.setBounds(680, 110, 200, 30);
        dUsernameLabel.setBounds(50, 170, 200, 30);
        dUsernameField.setBounds(240, 170, 200, 30);
        dPasswordLabel.setBounds(500, 170, 200, 30);
        dPasswordField.setBounds(680, 170, 200, 30);
        dDepartmentLabel.setBounds(50, 230, 200, 30);
        departmentCombo.setBounds(240, 230, 200, 30);

        btnAdd.setBounds(100, 300, 100, 40);
        btnUpdate.setBounds(300, 300, 100, 40);
        btnDelete.setBounds(500, 300, 100, 40);
        btnClear.setBounds(700, 300, 100, 40);

        // Add components to panel
        panel.add(title);
        panel.add(dNameLabel);
        panel.add(dNameField);
        panel.add(dSpecialityLabel);
        panel.add(dSpecialityField);
        panel.add(dPhoneLabel);
        panel.add(dPhoneField);
        panel.add(dEmailLabel);
        panel.add(dEmailField);
        panel.add(dUsernameLabel);
        panel.add(dUsernameField);
        panel.add(dPasswordLabel);
        panel.add(dPasswordField);
        panel.add(dDepartmentLabel);
        panel.add(departmentCombo);
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);

        add(panel);
        add(navBar);
        add(image);

        setVisible(true);
        }
       

    public static void main(String[] args) {
        new DoctorForm();
    }
        }