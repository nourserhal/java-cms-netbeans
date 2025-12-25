package projectgui2025;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceptionUpdate extends JFrame {
    JLabel image, title, rIdLabel, nNameLabel, nGenderLabel, nPhoneLabel, nShiftLabel, nSalaryLabel, nUsernameLabel, nPasswordLabel;
    JTextField rIdField, nNameField, nPhoneField, nSalaryField, nUsernameField;
    JPasswordField nPasswordField;
    JComboBox<String> genderCombo, ShiftCombo;
    JButton btnUpdate, btnDelete, btnClear, btnBack, btnHome, btnLogout;
    JPanel panel, navBar;

    ReceptionUpdate() {
        setTitle("Reception Update");
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

        title = new JLabel("Reception Update");
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

        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        btnClear.addActionListener(e -> {
            rIdField.setText(""); nNameField.setText(""); genderCombo.setSelectedIndex(0);
            nPhoneField.setText(""); ShiftCombo.setSelectedIndex(0); nSalaryField.setText("");
            nUsernameField.setText(""); nPasswordField.setText("");
        });

        btnUpdate.addActionListener(e -> {
            String idText = rIdField.getText();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Reception ID to update.");
                return;
            }

            try {
                int rId = Integer.parseInt(idText);
                if (nNameField.getText().isEmpty() || nPhoneField.getText().isEmpty() || nSalaryField.getText().isEmpty()
                        || nUsernameField.getText().isEmpty() || nPasswordField.getPassword().length == 0
                        || genderCombo.getSelectedIndex() == 0 || ShiftCombo.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE reception SET name=?, gender=?, phone=?, shift=?, salary=?, username=?, password=? WHERE id=?"
                );
                ps.setString(1, nNameField.getText());
                ps.setString(2, genderCombo.getSelectedItem().toString());
                ps.setString(3, nPhoneField.getText());
                ps.setString(4, ShiftCombo.getSelectedItem().toString());
                ps.setString(5, nSalaryField.getText());
                ps.setString(6, nUsernameField.getText());
                ps.setString(7, new String(nPasswordField.getPassword()));
                ps.setInt(8, rId);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Receptionist updated successfully!");

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT username FROM reception WHERE id=" + rId);
                    if (rs.next()) {
                        String oldUsername = rs.getString("username");
                        PreparedStatement loginUpdate = con.prepareStatement(
                            "UPDATE login SET username=?, password=? WHERE username=?"
                        );
                        loginUpdate.setString(1, nUsernameField.getText());
                        loginUpdate.setString(2, new String(nPasswordField.getPassword()));
                        loginUpdate.setString(3, oldUsername);
                        loginUpdate.executeUpdate();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Reception ID not found.");
                }

                con.close();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Reception ID.");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ReceptionUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnDelete.addActionListener(e -> {
            String idText = rIdField.getText();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Reception ID to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this receptionist?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            try {
                int rId = Integer.parseInt(idText);
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");
                PreparedStatement ps = con.prepareStatement("DELETE FROM reception WHERE id=?");
                ps.setInt(1, rId);
                int deleted = ps.executeUpdate();
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, "Receptionist deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Reception ID not found.");
                }
                con.close();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid Reception ID.");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ReceptionUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnHome.addActionListener(e -> { new MainDashboard(); setVisible(false); });
        btnLogout.addActionListener(e -> { new login("Doctor"); setVisible(false); });
        btnBack.addActionListener(e -> { new ReceptionForm(); setVisible(false); });

        panel = new JPanel(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        title.setBounds(450, 10, 300, 30);
        rIdLabel.setBounds(50, 50, 200, 30); 
        rIdField.setBounds(240, 50, 200, 30);
        nNameLabel.setBounds(50, 110, 200, 30); 
        nNameField.setBounds(240, 110, 200, 30);
        nGenderLabel.setBounds(500, 50, 200, 30); 
        genderCombo.setBounds(680, 50, 200, 30);
        nPhoneLabel.setBounds(50, 170, 200, 30); 
        nPhoneField.setBounds(240, 170, 200, 30);
        nShiftLabel.setBounds(500, 110, 200, 30); 
        ShiftCombo.setBounds(680, 110, 200, 30);
        nSalaryLabel.setBounds(50, 230, 200, 30); 
        nSalaryField.setBounds(240, 230, 200, 30);
        nUsernameLabel.setBounds(500, 170, 200, 30);
        nUsernameField.setBounds(680, 170, 200, 30);
        nPasswordLabel.setBounds(500, 230, 200, 30);
        nPasswordField.setBounds(680, 230, 200, 30);

        btnUpdate.setBounds(300, 300, 100, 40);
        btnDelete.setBounds(500, 300, 100, 40);
        btnClear.setBounds(700, 300, 100, 40);

        panel.add(title);
        panel.add(rIdLabel); panel.add(rIdField);
        panel.add(nNameLabel); panel.add(nNameField);
        panel.add(nGenderLabel); panel.add(genderCombo);
        panel.add(nPhoneLabel); panel.add(nPhoneField);
        panel.add(nShiftLabel); panel.add(ShiftCombo);
        panel.add(nSalaryLabel); panel.add(nSalaryField);
        panel.add(nUsernameLabel); panel.add(nUsernameField);
        panel.add(nPasswordLabel); panel.add(nPasswordField);
        panel.add(btnUpdate); panel.add(btnDelete); panel.add(btnClear);

        add(panel);
        add(navBar);
        add(image);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReceptionUpdate();
    }
}
