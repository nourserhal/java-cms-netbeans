package projectgui2025;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NurseUpdate extends JFrame {
    JLabel image, nIdLabel, nNameLabel, nGenderLabel, nPhoneLabel, nShiftLabel, nSalaryLabel, nUsernameLabel, nPasswordLabel, nDepartmentLabel, title;
    JTextField nIdField, nNameField, nPhoneField, nSalaryField, nUsernameField;
    JPasswordField nPasswordField;
    JComboBox genderCombo, nShiftField, departmentCombo;
    JButton btnUpdate, btnDelete, btnClear;

    // Nav bar buttons
    JButton btnBack, btnHome, btnSave;

    JPanel panel, navBar;

    NurseUpdate() {
        setTitle("Nurse Update Form");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

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

        title = new JLabel("Update Nurse");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        nIdLabel = new JLabel("Nurse ID");
        nNameLabel = new JLabel("Nurse Name");
        nGenderLabel = new JLabel("Nurse Gender");
        nPhoneLabel = new JLabel("Nurse Phone");
        nShiftLabel = new JLabel("Nurse Shift");
        nSalaryLabel = new JLabel("Nurse Salary");
        nUsernameLabel = new JLabel("Username");
        nPasswordLabel = new JLabel("Password");
        nDepartmentLabel = new JLabel("Nurse Department");

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        nIdLabel.setFont(labelFont);
        nNameLabel.setFont(labelFont);
        nGenderLabel.setFont(labelFont);
        nPhoneLabel.setFont(labelFont);
        nShiftLabel.setFont(labelFont);
        nSalaryLabel.setFont(labelFont);
        nUsernameLabel.setFont(labelFont);
        nPasswordLabel.setFont(labelFont);
        nDepartmentLabel.setFont(labelFont);

        nIdField = new JTextField();
        nNameField = new JTextField();
        nPhoneField = new JTextField();
        String[] shifts = {" ", "Morning (8:00-14:00)", "Afternoon (14:00-20:00)", "Night (20:00-8:00)"};
        nShiftField = new JComboBox(shifts);
        nSalaryField = new JTextField();
        nUsernameField = new JTextField();
        nPasswordField = new JPasswordField();

        String[] genders = {"", "Male", "Female"};
        genderCombo = new JComboBox<>(genders);
        

        departmentCombo = new JComboBox();
        departmentCombo.addItem("Select dep");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = (Connection) DriverManager.getConnection(myUrl, "root", "");
            Statement s = (Statement) con.createStatement();
            String sql = ("select departementname from departement");
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                departmentCombo.addItem(rs.getString("departementname"));
            }
            s.close();
            con.close();
        } catch (Exception ex) {
            Logger.getLogger(NurseUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }

        // DELETE BUTTON ACTION
btnDelete.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this receptionist?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = (Connection) DriverManager.getConnection(myUrl, "root", "");

            int nurseid = Integer.parseInt(nIdField.getText());

            String sql = "DELETE FROM nurse WHERE nurseid=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, nurseid);

            int deleted = pst.executeUpdate();
            if (deleted > 0) {
                JOptionPane.showMessageDialog(null, "Nurse Deleted Successfully!");
                // clear fields after delete
                nIdField.setText("");
                nNameField.setText("");
                genderCombo.setSelectedIndex(0);
                nPhoneField.setText("");
                nShiftField.setSelectedIndex(0);
                nSalaryField.setText("");
                nUsernameField.setText("");
                nPasswordField.setText("");
                departmentCombo.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(null, "Nurse ID not found.");
            }

            con.close();
        } catch (Exception ex) {
            Logger.getLogger(NurseUpdate.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
});
        
        // UPDATE BUTTON ACTION
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    String myUrl = "jdbc:mysql://localhost:3306/cms";
                    Connection con = (Connection) DriverManager.getConnection(myUrl, "root", "");

                    int nurseid = Integer.parseInt(nIdField.getText());
                    String nursename = nNameField.getText();
                    String nursegender = genderCombo.getSelectedItem().toString();
                    String nursephone = nPhoneField.getText();
                    String depname = departmentCombo.getSelectedItem().toString();
                    String shift = nShiftField.getSelectedItem().toString();
                    double salary = Double.parseDouble(nSalaryField.getText());
                    String nurseusername = nUsernameField.getText();
                    String nursepassword = new String(nPasswordField.getPassword());

                    // Get department ID
                    String depSql = "SELECT departementid FROM departement WHERE departementname=?";
                    PreparedStatement pstDep = con.prepareStatement(depSql);
                    pstDep.setString(1, depname);
                    ResultSet rsDep = pstDep.executeQuery();

                    if (rsDep.next()) {
                        int departementid = rsDep.getInt("departementid");

                        String sql1 = "UPDATE nurse SET nursename=?, nursegender=?, nursephone=?, shift=?, salary=?, nurseusername=?, nursepassword=?, departmentid=? WHERE nurseid=?";
                        PreparedStatement pst = con.prepareStatement(sql1);
                        pst.setString(1, nursename);
                        pst.setString(2, nursegender);
                        pst.setString(3, nursephone);
                        pst.setString(4, shift);
                        pst.setDouble(5, salary);
                        pst.setString(6, nurseusername);
                        pst.setString(7, nursepassword);
                        pst.setInt(8, departementid);
                        pst.setInt(9, nurseid);

                        int updated = pst.executeUpdate();
                        if (updated > 0) {
                            JOptionPane.showMessageDialog(null, "Nurse Updated Successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Nurse ID not found.");
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(NurseUpdate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // CLEAR BUTTON
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
       
        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new login("");
                setVisible(false);
                
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NurseForm();
                setVisible(false);
                
            }
        });


        // Panel setup
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 130, 1100, 500);

        // Set bounds
        title.setBounds(450, 4, 300, 30);
        nIdLabel.setBounds(50, 70, 200, 30);
        nIdField.setBounds(240, 70, 200, 30);

        nNameLabel.setBounds(50, 130, 200, 30);
        nNameField.setBounds(240, 130, 200, 30);
        nGenderLabel.setBounds(500, 70, 200, 30);
        genderCombo.setBounds(680, 70, 200, 30);

        nPhoneLabel.setBounds(50, 190, 200, 30);
        nPhoneField.setBounds(240, 190, 200, 30);
        nShiftLabel.setBounds(500, 130, 200, 30);
        nShiftField.setBounds(680, 130, 200, 30);

        nSalaryLabel.setBounds(50, 250, 200, 30);
        nSalaryField.setBounds(240, 250, 200, 30);
        nUsernameLabel.setBounds(500, 190, 200, 30);
        nUsernameField.setBounds(680, 190, 200, 30);

        nPasswordLabel.setBounds(290, 300, 200, 30);
        nPasswordField.setBounds(400, 300, 200, 30);
        nDepartmentLabel.setBounds(500, 250, 200, 30);
        departmentCombo.setBounds(680, 250, 200, 30);
        
        btnUpdate.setBounds(300, 360, 100, 40);
        btnDelete.setBounds(500, 360, 100, 40);
        btnClear.setBounds(700, 360, 100, 40);

        // Add components
        panel.add(title);
        panel.add(nIdLabel);
        panel.add(nIdField);
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
        new NurseUpdate();
    }
}