package projectgui2025;

import com.mysql.jdbc.ResultSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorUpdate extends JFrame {
    JLabel image, title, dIdLabel, dNameLabel, dSpecialityLabel, dPhoneLabel, dEmailLabel, dUsernameLabel, dPasswordLabel, dDepartmentLabel;
    JTextField dIdField, dNameField, dSpecialityField, dPhoneField, dEmailField, dUsernameField;
    JPasswordField dPasswordField;
    JComboBox<String> deptCombo;
    JButton btnAdd, btnUpdate, btnDelete, btnClear;

    // Nav bar buttons  
    JButton btnBack, btnHome, btnLogout;

    JPanel panel, navBar;

    DoctorUpdate() {
        setTitle("Doctor Update");
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
        btnLogout = new JButton("Logout");
        Font navFont = new Font("Arial", Font.BOLD, 20);
        btnBack.setFont(navFont);
        btnHome.setFont(navFont);
        btnLogout.setFont(navFont);
        navBar.add(btnBack);
        navBar.add(btnHome);
        navBar.add(btnLogout);

        // Form labels and fields
        title = new JLabel("Doctor Form");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        dIdLabel = new JLabel("Doctor ID");
        dNameLabel = new JLabel("Doctor Name");
        dSpecialityLabel = new JLabel("Speciality");
        dPhoneLabel = new JLabel("Phone");
        dEmailLabel = new JLabel("Email");
        dUsernameLabel = new JLabel("Username");
        dPasswordLabel = new JLabel("Password");
        dDepartmentLabel = new JLabel("Department");

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        dIdLabel.setFont(labelFont);
        dNameLabel.setFont(labelFont);
        dSpecialityLabel.setFont(labelFont);
        dPhoneLabel.setFont(labelFont);
        dEmailLabel.setFont(labelFont);
        dUsernameLabel.setFont(labelFont);
        dPasswordLabel.setFont(labelFont);
        dDepartmentLabel.setFont(labelFont);

        dIdField = new JTextField();
        dNameField = new JTextField();
        dSpecialityField = new JTextField();
        dPhoneField = new JTextField();
        dEmailField = new JTextField();
        dUsernameField = new JTextField();
        dPasswordField = new JPasswordField();

        deptCombo = new JComboBox<>();
        deptCombo.addItem("Select dep");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = DriverManager.getConnection(myUrl, "root", "");
            Statement st = con.createStatement();
            String sql = "select departementname from departement";
            ResultSet rs = (ResultSet) st.executeQuery(sql);
            while (rs.next()) {
                deptCombo.addItem(rs.getString("departementname"));
            }
            st.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DoctorUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Action buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        // Clear button
        btnClear.addActionListener(e -> {
            dIdField.setText("");
            dNameField.setText("");
            dSpecialityField.setText("");
            dPhoneField.setText("");
            dEmailField.setText("");
            dUsernameField.setText("");
            dPasswordField.setText("");
            deptCombo.setSelectedIndex(0);
        });

        // Delete doctor by ID
        btnDelete.addActionListener(e -> {
            String idText = dIdField.getText();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Doctor ID to delete.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this receptionist?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
            try {
                if (dIdField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
                    return;
                }
                int docId = Integer.parseInt(idText);
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");
                Statement stmt = con.createStatement();
                int deleted = stmt.executeUpdate("DELETE FROM doctors WHERE doctorid = " + docId);
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, "Doctor deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Doctor ID not found.");
                }
                con.close();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Doctor ID.");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(DoctorUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnUpdate.addActionListener(e -> {
    String idText = dIdField.getText();
    if (idText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter Doctor ID to update.");
        return;
    }

    try {
        if (dIdField.getText().isEmpty() || dSpecialityField.getText().isEmpty() || dPhoneField.getText().isEmpty()
                        || dEmailField.getText().isEmpty() || dUsernameField.getText().isEmpty() || dPasswordField.getPassword().length == 0
                        || deptCombo.getSelectedItem() == "") {

                    JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
                    return;
                }
        
        int docId = Integer.parseInt(idText);

        // Check required fields
        if (dNameField.getText().isEmpty() || dSpecialityField.getText().isEmpty() || dPhoneField.getText().isEmpty()
                || dEmailField.getText().isEmpty() || dUsernameField.getText().isEmpty() || dPasswordField.getPassword().length == 0
                || deptCombo.getSelectedItem() == "") {

            JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
            return;
        }

        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");

        // Get department ID from combo
        String selectedDept = deptCombo.getSelectedItem().toString();
        Statement deptStmt = con.createStatement();
        ResultSet rs = (ResultSet) deptStmt.executeQuery("SELECT departementid FROM departement WHERE departementname = '" + selectedDept + "'");
        int deptId = -1;
        if (rs.next()) {
            deptId = rs.getInt("departementid");
        } else {
            JOptionPane.showMessageDialog(null, "Departement not found.");
            return;
        }

        // Update doctor table
        String updateQuery = "UPDATE doctor SET doctorname='" + dNameField.getText() + "', "
                + "doctorspeciality='" + dSpecialityField.getText() + "', "
                + "doctorphone='" + dPhoneField.getText() + "', "
                + "doctoremail='" + dEmailField.getText() + "', "
                + "departementid=" + deptId + ", "
                + "doctorusername='" + dUsernameField.getText() + "', "
                + "doctorpassword='" + new String(dPasswordField.getPassword()) + "' "
                + "WHERE doctorid=" + docId;

        Statement stmt = con.createStatement();
        int rowsUpdated = stmt.executeUpdate(updateQuery);

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Doctor updated successfully!");

            // Update login table
            String updateLogin = "UPDATE login SET username='" + dUsernameField.getText() + "', "
                    + "password='" + new String(dPasswordField.getPassword()) + "' "
                    + "WHERE username=(SELECT doctorusername FROM doctor WHERE doctorid=" + docId + ")";
            stmt.executeUpdate(updateLogin);
        } else {
            JOptionPane.showMessageDialog(null, "Doctor ID not found.");
        }

        con.close();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Invalid Doctor ID.");
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(DoctorUpdate.class.getName()).log(Level.SEVERE, null, ex);
    }
});

        // Navigation buttons
        btnHome.addActionListener(e -> {
            new HomePage();
            setVisible(false);
        });
        btnLogout.addActionListener(e -> {
            new login("Doctor");
            setVisible(false);
        });
        btnBack.addActionListener(e -> {
            new AdminPage("");
            setVisible(false);
        });

        // Panel for form
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        // Set bounds for labels and fields
        title.setBounds(450, 10, 300, 30);
        dIdLabel.setBounds(50, 50, 200, 30);
        dIdField.setBounds(240, 50, 200, 30);
        dNameLabel.setBounds(50, 110, 200, 30);
        dNameField.setBounds(240, 110, 200, 30);
        dSpecialityLabel.setBounds(500, 50, 200, 30);
        dSpecialityField.setBounds(680, 50, 200, 30);
        dPhoneLabel.setBounds(50, 170, 200, 30);
        dPhoneField.setBounds(240, 170, 200, 30);
        dEmailLabel.setBounds(500, 110, 200, 30);
        dEmailField.setBounds(680, 110, 200, 30);
        dUsernameLabel.setBounds(50, 230, 200, 30);
        dUsernameField.setBounds(240, 230, 200, 30);
        dPasswordLabel.setBounds(500, 170, 200, 30);
        dPasswordField.setBounds(680, 170, 200, 30);
        dDepartmentLabel.setBounds(500, 230, 200, 30);
        deptCombo.setBounds(680, 230, 200, 30);

        btnAdd.setBounds(100, 300, 100, 40);
        btnUpdate.setBounds(300, 300, 100, 40);
        btnDelete.setBounds(500, 300, 100, 40);
        btnClear.setBounds(700, 300, 100, 40);

        // Add components to panel
        panel.add(title);
        panel.add(dIdLabel);
        panel.add(dIdField);
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
        panel.add(deptCombo);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);

        add(panel);
        add(navBar);
        add(image);

        setVisible(true);
    }

    public static void main(String[] args) {
        new DoctorUpdate();
    }
}