
package projectgui2025;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JFrame {
    
    JLabel lblUsername, lblPassword, lblRole;
    JTextField txtUsername;
    JPasswordField txtPassword;
    JComboBox<String> cbRole;
    JButton btnLogin, btnForget;
    JButton btnBack, btnHome, btnLogout;
    JPanel navBar;
    
    public login(String role) {
        setTitle("Login");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // ===== Background =====
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        JLabel background = new JLabel(bgIcon);
        background.setBounds(0, 0, 1300, 700);
        background.setLayout(null);
        this.add(background);
        
        // ===== Nav Bar =====
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
        
        background.add(navBar);
        
        // ===== Labels & Fields =====
        lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 30));
        lblUsername.setBounds(350, 200, 200, 40);
        background.add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setBounds(550, 200, 250, 40);
        txtUsername.setMargin(new Insets(5,10,5,10));
        background.add(txtUsername);
        
        lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 30));
        lblPassword.setBounds(350, 260, 200, 40);
        background.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setBounds(550, 260, 250, 40);
        txtPassword.setMargin(new Insets(5,10,5,10));
        background.add(txtPassword);
        
        lblRole = new JLabel("Role:");
        lblRole.setFont(new Font("Arial", Font.BOLD, 30));
        lblRole.setBounds(350, 320, 200, 40);
        background.add(lblRole);
        
        String[] roles = {"", "Admin", "Doctor", "Nurse", "Reception"};
        cbRole = new JComboBox<>(roles);
        cbRole.setBounds(550, 320, 250, 40);
        background.add(cbRole);
        
        // ===== Login & Forget Password =====
        btnLogin = new JButton("Login");
        btnLogin.setBounds(400, 400, 150, 50);
        btnLogin.setFocusable(false);
        background.add(btnLogin);
        
        btnForget = new JButton("Forget Password");
        btnForget.setBounds(600, 400, 180, 50);
        btnForget.setFocusable(false);
        background.add(btnForget);
        btnForget.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new ForgotPasswordPage();
            }
            
            
        });
        
        // ===== Button Actions =====
        btnLogin.addActionListener(e -> loginAction());
        btnBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainDashboard();
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
        btnLogout.addActionListener(e -> dispose());
        
        setVisible(true);
    }
    
    private void loginAction() {
        try {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String role = cbRole.getSelectedItem().toString();
            
            if(username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields ⚠️");
                return;
            }
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM login WHERE username='"+username+"'");
            
            if(rs.next()) {
                String dbPass = rs.getString("password");
                String dbRole = rs.getString("role");
                
                if(dbPass.equals(password) && dbRole.equalsIgnoreCase(role)) {
                    JOptionPane.showMessageDialog(this, "Login successful ✅ ("+role+")");
                    openRoleFrame(role, username);
                    dispose();
                } else if(!dbRole.equalsIgnoreCase(role)) {
                    JOptionPane.showMessageDialog(this, "Role mismatch ❌. Registered as "+dbRole);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid password ❌");
                }
            } else {
                JOptionPane.showMessageDialog(this, "User not found ❌");
            }
            
            rs.close();
            st.close();
            con.close();
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while login ❌");
            ex.printStackTrace();
        }
    }
    
    private void openRoleFrame(String role, String username) {
        switch(role) {
            case "Admin": 
                new AdminPage(username).setVisible(true); 
                break;
            case "Doctor": 
                new DoctorPage(username).setVisible(true); 
                break;
            case "Nurse": 
                int nurseId = getNurseIdByUsername(username);
                new AppointmentCheck(role, nurseId).setVisible(true);
                break;
            case "Reception": 
                new ReceptionPage(username).setVisible(true); 
                break;
        }
    }
    
    private int getNurseIdByUsername(String username) {
        int nurseId = 0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
            String sql = "SELECT nurseid FROM nurse WHERE nurseusername = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                nurseId = rs.getInt("nurseid");
            }
            rs.close();
            ps.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return nurseId;
    }
    
    public static void main(String[] args) {
        new login("");
    }
}
