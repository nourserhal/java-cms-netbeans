/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectgui2025;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NurseForm extends JFrame {
    JLabel image, nNameLabel, nGenderLabel, nPhoneLabel, nShiftLabel, nSalaryLabel, nUsernameLabel, nPasswordLabel, nDepartmentLabel, title;
    JTextField nNameField, nPhoneField,  nSalaryField, nUsernameField;
    JPasswordField nPasswordField;
    JComboBox genderCombo, nShiftField,departmentCombo;
    JButton btnAdd, btnUpdate, btnDelete, btnClear;

    // Nav bar buttons
    JButton btnBack, btnHome, btnSave;

    JPanel panel, navBar;

    NurseForm() {
        setTitle("NurseForm");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
                
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
       
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, getWidth(), getHeight());
        image.setOpaque(true);
    
        navBar = new JPanel(new GridLayout(1, 3));
        navBar.setBackground(new Color(0, 0, 0, 200));
        navBar.setBounds(0, 0, getWidth(), 80);
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

        
        title = new JLabel("Nurse Form");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        nNameLabel = new JLabel("Nurse Name");
        nGenderLabel = new JLabel("Nurse Gender");
        nPhoneLabel = new JLabel("Nurse Phone");
        nShiftLabel = new JLabel("Nurse Shift");
        nSalaryLabel = new JLabel("Nurse Salary");
        nUsernameLabel = new JLabel("Username");
        nPasswordLabel = new JLabel("Password");
        nDepartmentLabel = new JLabel("Nurse Department");

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        nNameLabel.setFont(labelFont);
        nGenderLabel.setFont(labelFont);
        nPhoneLabel.setFont(labelFont);
        nShiftLabel.setFont(labelFont);
        nSalaryLabel.setFont(labelFont);
        nUsernameLabel.setFont(labelFont);
        nPasswordLabel.setFont(labelFont);
        nDepartmentLabel.setFont(labelFont);

        nNameField = new JTextField();
        nPhoneField = new JTextField();
        String[] shifts = {" ","Morning (8:00-14:00)", "Afternoon (14:00-20:00)", "Night (20:00-8:00)"};
        nShiftField = new JComboBox(shifts);
        nSalaryField = new JTextField();
        nUsernameField = new JTextField();
        nPasswordField = new JPasswordField();

        String[] genders = {"", "Male", "Female"};
        genderCombo = new JComboBox<>(genders);
        
        
         btnUpdate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new NurseUpdate();
            }
             
         });
         btnDelete.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new NurseUpdate();
            }
             
         });
        
        departmentCombo=new JComboBox();
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
                Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/project2025gui";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
                java.sql.Statement s=(java.sql.Statement) con.createStatement();
String nursename = nNameField.getText();
String nursegender = genderCombo.getSelectedItem().toString();
String nursephone = nPhoneField.getText();
String depname = departmentCombo.getSelectedItem().toString(); // من الكومبو
String shift = nShiftField.getSelectedItem().toString();
double salary = Double.parseDouble(nSalaryField.getText());
String nurseusername = nUsernameField.getText();
String nursepassword = new String(nPasswordField.getPassword());


String depSql = "SELECT departementid FROM departement WHERE departementname=?";
PreparedStatement pstDep = con.prepareStatement(depSql);
pstDep.setString(1, depname);
ResultSet rsDep = pstDep.executeQuery();

if (rsDep.next()) {
    int departmentid = rsDep.getInt("departementid");
    String sql1 = "INSERT INTO nurse "
               + "(nursename, nursegender, nursephone, departementid, shift, salary, nurseusername, nursepassword) "
               + "VALUES (?,?,?,?,?,?,?,?)";
    PreparedStatement pst = con.prepareStatement(sql1);
    pst.setString(1, nursename);
    pst.setString(2, nursegender);
    pst.setString(3, nursephone);
    pst.setInt(4, departmentid);
    pst.setString(5, shift);
    pst.setDouble(6, salary);
    pst.setString(7, nurseusername);
    pst.setString(8, nursepassword);

    pst.executeUpdate();
    JOptionPane.showMessageDialog(null, "Nurse Added Successfully!");
    String loginQuery = "INSERT INTO login (username, password, role) VALUES (?, ?, 'Nurse')";
PreparedStatement psLogin = con.prepareStatement(loginQuery);
psLogin.setString(1, nurseusername);
psLogin.setString(2, nursepassword);
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
                nNameField.setText("");
                genderCombo.setSelectedIndex(0);
                nPhoneField.setText("");
                nShiftField.setSelectedIndex(0);
                nSalaryField.setText("");
                nUsernameField.setText("");
                nPasswordField.setText("");
                departmentCombo.setSelectedIndex(0);
            }
        });
        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        // Set bounds
        title.setBounds(450, 10, 300, 30);
        nNameLabel.setBounds(50, 50, 200, 30);
        nNameField.setBounds(240, 50, 200, 30);
        nGenderLabel.setBounds(500, 50, 200, 30);
        genderCombo.setBounds(680, 50, 200, 30);
        nPhoneLabel.setBounds(50, 110, 200, 30);
        nPhoneField.setBounds(240, 110, 200, 30);
        nShiftLabel.setBounds(500, 110, 200, 30);
        nShiftField.setBounds(680, 110, 200, 30);
        nSalaryLabel.setBounds(50, 170, 200, 30);
        nSalaryField.setBounds(240, 170, 200, 30);
        nUsernameLabel.setBounds(500, 170, 200, 30);
        nUsernameField.setBounds(680, 170, 200, 30);
        nPasswordLabel.setBounds(50, 230, 200, 30);
        nPasswordField.setBounds(240, 230, 200, 30);
        nDepartmentLabel.setBounds(500, 230, 200, 30);
        departmentCombo.setBounds(680, 230, 200, 30);

        btnAdd.setBounds(100, 300, 100, 40);
        btnUpdate.setBounds(300, 300, 100, 40);
        btnDelete.setBounds(500, 300, 100, 40);
        btnClear.setBounds(700, 300, 100, 40);

        // Add components to panel
        panel.add(title);
        panel.add(nNameLabel);
        panel.add(nNameField);
        panel.add(nGenderLabel);
        panel.add(genderCombo);
        panel.add(nPhoneLabel);
        panel.add(nPhoneField);
        panel.add(nShiftLabel);
        panel.add(nShiftField);
        panel.add(nSalaryLabel);
        panel.add(nSalaryField);
        panel.add(nUsernameLabel);
        panel.add(nUsernameField);
        panel.add(nPasswordLabel);
        panel.add(nPasswordField);
        panel.add(nDepartmentLabel);
        panel.add(departmentCombo);
        panel.add(btnAdd);
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
        new NurseForm();
    }
}
