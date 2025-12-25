package projectgui2025;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ForgotPasswordPage extends JFrame {
    JLabel titleLabel, usernameLabel, newPassLabel, confirmPassLabel;
    JTextField usernameField;
    JPasswordField newPassField, confirmPassField;
    JButton resetButton, backButton;

    public ForgotPasswordPage() {
        setTitle("Forgot Password");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Title
        titleLabel = new JLabel("Forgot Password", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(100, 20, 300, 40);
        add(titleLabel);

        // Username
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        usernameLabel.setBounds(50, 80, 120, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(195, 80, 200, 30);
        add(usernameField);

        // New Password
        newPassLabel = new JLabel("New Password:");
        newPassLabel.setFont(new Font("Arial", Font.BOLD, 16));
        newPassLabel.setBounds(50, 130, 120, 30);
        add(newPassLabel);

        newPassField = new JPasswordField();
        newPassField.setBounds(195, 130, 200, 30);
        add(newPassField);

        // Confirm Password
        confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setFont(new Font("Arial", Font.BOLD, 16));
        confirmPassLabel.setBounds(50, 180, 150, 30);
        add(confirmPassLabel);

        confirmPassField = new JPasswordField();
        confirmPassField.setBounds(195, 180, 200, 30);
        add(confirmPassField);

        // Buttons
        resetButton = new JButton("Reset Password");
        resetButton.setBounds(50, 250, 150, 40);
        add(resetButton);

        backButton = new JButton("Back to Login");
        backButton.setBounds(230, 250, 150, 40);
        add(backButton);

        // Action Listeners
        resetButton.addActionListener(e -> resetPassword());
        backButton.addActionListener(e -> {
            new login(""); // go back to login page
            dispose();
        });

        setVisible(true);
    }

    private void resetPassword() {
        String username = usernameField.getText().trim();
        String newPass = String.valueOf(newPassField.getPassword());
        String confirmPass = String.valueOf(confirmPassField.getPassword());

        if(username.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        if(!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cms","root","");

            
            // Check if user exists in login table
            String checkQuery = "SELECT * FROM login WHERE username=?";
            PreparedStatement psCheck = con.prepareStatement(checkQuery);
            psCheck.setString(1, username);
            ResultSet rs = psCheck.executeQuery();

            if(!rs.next()) {
                JOptionPane.showMessageDialog(this, "Username not found.");
                con.close();
                return;
            }

            // Update password
            String updateQuery = "UPDATE login SET password=? WHERE username=?";
            PreparedStatement psUpdate = con.prepareStatement(updateQuery);
            psUpdate.setString(1, newPass); // In production, hash passwords!
            psUpdate.setString(2, username);
            psUpdate.executeUpdate();

            JOptionPane.showMessageDialog(this, "Password reset successfully!");
            con.close();
            dispose();
            new login(""); // redirect to login page

        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error resetting password.");
        }
    }

    public static void main(String[] args) {
        new ForgotPasswordPage();
    }
}