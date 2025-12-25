package projectgui2025;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceptionForm extends JFrame {
    JLabel image, title, rIdLabel, nNameLabel, nGenderLabel, nPhoneLabel, nShiftLabel, nSalaryLabel, nUsernameLabel, nPasswordLabel;
    JTextField rIdField, nNameField, nPhoneField, nSalaryField, nUsernameField;
    JPasswordField nPasswordField;
    JComboBox<String> genderCombo, ShiftCombo;
    JButton btnAdd , btnUpdate, btnDelete, btnClear, btnBack, btnHome, btnLogout;
    JPanel panel, navBar;

    ReceptionForm() {
        setTitle("Reception Form");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        image = new JLabel(new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg")));
        image.setBounds(0, 0, getWidth(), getHeight());
        image.setOpaque(true);

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

        title = new JLabel("Reception Form");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        rIdLabel = new JLabel("Reception ID"); rIdLabel.setFont(labelFont);
        nNameLabel = new JLabel("Name"); nNameLabel.setFont(labelFont);
        nGenderLabel = new JLabel("Gender"); nGenderLabel.setFont(labelFont);
        nPhoneLabel = new JLabel("Phone"); nPhoneLabel.setFont(labelFont);
        nShiftLabel = new JLabel("Shift"); nShiftLabel.setFont(labelFont);
        nSalaryLabel = new JLabel("Salary"); nSalaryLabel.setFont(labelFont);
        nUsernameLabel = new JLabel("Username"); nUsernameLabel.setFont(labelFont);
        nPasswordLabel = new JLabel("Password"); nPasswordLabel.setFont(labelFont);

        rIdField = new JTextField();
        nNameField = new JTextField();
        nPhoneField = new JTextField();
        nSalaryField = new JTextField();
        nUsernameField = new JTextField();
        nPasswordField = new JPasswordField();

        ShiftCombo = new JComboBox<>(new String[]{"", "Morning(8:00-14:00)", "After-noon(14:00-20:00)", "Evening(20:00-8:00)"});
        genderCombo = new JComboBox<>(new String[]{"", "Male", "Female"});
        btnAdd=new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnAdd.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
           
            if (nNameField.getText().isEmpty() || nPhoneField.getText().isEmpty() || nSalaryField.getText().isEmpty() 
                || nUsernameField.getText().isEmpty() || nPasswordField.getPassword() == null || genderCombo.getSelectedItem() == null ||ShiftCombo.getSelectedItem()==null) {
                
                JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
                return; 
            }

          
            Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = DriverManager.getConnection(myUrl, "root", "");
            Statement s = con.createStatement();

           String pass=new String(nPasswordField.getPassword());
            s.executeUpdate(
                "INSERT INTO reception (name, gender, phone, shift, salary, username, password) VALUES ('"
                + nNameField.getText() + "','"
                + genderCombo.getSelectedItem().toString() + "','"
                + nPhoneField.getText() + "','"
                + ShiftCombo.getSelectedItem().toString() + "','"
                + nSalaryField.getText() + "','"
                + nUsernameField.getText() + "','"
                + nPasswordField.getPassword()+ "')");
               String loginQuery = "INSERT INTO login (username, password, role) VALUES (?, ?, 'Reception')";
PreparedStatement psLogin = con.prepareStatement(loginQuery);
psLogin.setString(1, nUsernameField.getText());
psLogin.setString(2, pass);
psLogin.executeUpdate();
            

        
            JOptionPane.showMessageDialog(null, "Reception has been registered successfully!");

        } catch (Exception ex) {
            
            JOptionPane.showMessageDialog(null, "Error while saving Reception information ❌");
            ex.printStackTrace();
        }
    }
});

        btnClear.addActionListener(e -> {
            rIdField.setText(""); nNameField.setText(""); genderCombo.setSelectedIndex(0);
            nPhoneField.setText(""); ShiftCombo.setSelectedIndex(0); nSalaryField.setText("");
            nUsernameField.setText(""); nPasswordField.setText("");
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 new ReceptionUpdate();
            }
        });

        btnDelete.addActionListener(e -> {
            new ReceptionUpdate();
        });

        btnHome.addActionListener(e -> { new HomePage(); setVisible(false); });
        btnLogout.addActionListener(e -> { new login("Doctor"); setVisible(false); });
        btnBack.addActionListener(e -> { new AdminPage(""); setVisible(false); });

        panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        title.setBounds(450, 10, 300, 30);
        nNameLabel.setBounds(50, 50, 200, 30);
        nNameField.setBounds(240, 50, 200, 30);
        nGenderLabel.setBounds(500, 50, 200, 30); 
        genderCombo.setBounds(680, 50, 200, 30);
        nPhoneLabel.setBounds(50, 110, 200, 30); 
        nPhoneField.setBounds(240, 110, 200, 30);
        nShiftLabel.setBounds(500, 110, 200, 30); 
        ShiftCombo.setBounds(680, 110, 200, 30);
        nSalaryLabel.setBounds(50, 170, 200, 30); 
        nSalaryField.setBounds(240, 170, 200, 30);
        nUsernameLabel.setBounds(500, 170, 200, 30);
        nUsernameField.setBounds(680, 170, 200, 30);
        nPasswordLabel.setBounds(50, 230, 200, 30);
        nPasswordField.setBounds(240, 230, 200, 30);
        btnAdd.setBounds(100, 300, 100, 40);
        btnUpdate.setBounds(300, 300, 100, 40);
        btnDelete.setBounds(500, 300, 100, 40);
        btnClear.setBounds(700, 300, 100, 40);

        panel.add(title);
        panel.add(rIdLabel); 
        panel.add(rIdField);
        panel.add(nNameLabel);
        panel.add(nNameField);
        panel.add(nGenderLabel);
        panel.add(genderCombo);
        panel.add(nPhoneLabel); 
        panel.add(nPhoneField);
        panel.add(nShiftLabel); 
        panel.add(ShiftCombo);
        panel.add(nSalaryLabel); 
        panel.add(nSalaryField);
        panel.add(nUsernameLabel); 
        panel.add(nUsernameField);
        panel.add(nPasswordLabel); 
        panel.add(nPasswordField);
        panel.add(btnUpdate);
        panel.add(btnDelete); 
        panel.add(btnClear);
        panel.add(btnAdd);

        add(panel);
        add(navBar);
        add(image);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReceptionForm();
    }
}
