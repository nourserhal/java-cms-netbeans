/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectgui2025;

/**
 *
 * @author HP
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DepartmentForm extends JFrame {

    private JTextField txtDepId, txtDepName;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnBack, btnHome, btnLogout;
    private Connection con;
    private Statement st;
    JLabel title;
    ButtonGroup depGroup;
    JRadioButton rbDepId,rbDepName;

    public DepartmentForm() {
        setTitle("Department Form");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
         title = new JLabel("Department Form");
         title.setFont(new Font("Arial", Font.BOLD, 30));

        // -------- Background Image ----------
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"))); // ضع صورتك هنا
        background.setBounds(0, 0, 1300, 700);
        setContentPane(background);
        background.setLayout(null);

        // -------- Navbar ----------
    
JPanel navbar = new JPanel();
navbar.setLayout(new GridLayout(1, 3, 0, 0)); // 3 أعمدة, 0 مسافة
navbar.setBounds(0, 0, 1300, 60); // العرض كامل, الارتفاع 50
navbar.setOpaque(false); // خلي الخلفية شفافة (حتى يبين الـ background image)
depGroup=new ButtonGroup();
btnBack = new JButton("Back");
btnHome = new JButton("Home");
btnLogout = new JButton("Logout");

btnBack.setFont(new Font("Arial", Font.BOLD, 20));
btnHome.setFont(new Font("Arial", Font.BOLD, 20));
btnLogout.setFont(new Font("Arial", Font.BOLD, 20));

navbar.add(btnBack);
navbar.add(btnHome);
navbar.add(btnLogout);

background.add(navbar);


        

        

        // -------- Labels + TextFields ----------
        rbDepId = new JRadioButton("Department ID:");
        rbDepId.setForeground(Color.BLACK);
        rbDepId.setFont(new Font("Arial", Font.BOLD, 20));
        rbDepId.setBounds(400, 200, 180, 30);

        txtDepId = new JTextField();
        txtDepId.setBounds(630, 200, 200, 30);
        
        
        

        rbDepName = new JRadioButton("Department Name:");
        rbDepName.setForeground(Color.BLACK);
        rbDepName.setFont(new Font("Arial", Font.BOLD, 20));
        rbDepName.setBounds(400, 260, 210, 30);
        depGroup.add(rbDepId);
        depGroup.add(rbDepName);
        

        txtDepName = new JTextField();
        txtDepName.setBounds(630, 260, 200, 30);

        background.add(rbDepId);
        background.add(txtDepId);
        background.add(rbDepName);
        background.add(txtDepName);
        title.setBounds(450, 100, 300, 30);
        background.add(title);

        // -------- Buttons Group ----------
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        btnAdd.setBounds(400, 350, 120, 50);
btnUpdate.setBounds(540, 350, 120, 50);
btnDelete.setBounds(680, 350, 120, 50);
btnClear.setBounds(820, 350, 120, 50);


        background.add(btnAdd);
        background.add(btnUpdate);
        background.add(btnDelete);
        background.add(btnClear);

        

        // -------- Button Actions ----------
        btnAdd.addActionListener(e -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = DriverManager.getConnection(myUrl, "root", "");
            Statement st = con.createStatement();
                if(rbDepId.isSelected()){
                    String sql="insert into departement (departementid) values('"+txtDepId.getText()+"')";
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Add Departement by ID");
                }else if(rbDepName.isSelected()){
                    String sql="insert into departement (departementname) values('"+txtDepName.getText()+"')";
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null,  "Add Departement by Name");
                    
                }else{
                    JOptionPane.showMessageDialog(null,  "Please select id or name first");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = DriverManager.getConnection(myUrl, "root", "");
            Statement st = con.createStatement();
                if(rbDepId.isSelected()){
                    String sql="update departement set departementname='"+txtDepName.getText()+"'where departementid='"+txtDepId.getText()+"'" ;
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Update Departments by ID");
                }else if(rbDepName.isSelected()){
                    String sql="update departement set departementid='"+txtDepId.getText()+"'where departementname='"+txtDepName.getText()+"'" ;
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null,  "Update Departments by Name");
                    
                }else{
                    JOptionPane.showMessageDialog(null,  "Please select id or name first");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = DriverManager.getConnection(myUrl, "root", "");
            Statement st = con.createStatement();
                if(rbDepId.isSelected()){
                    String sql="delete from departement where departementid='"+txtDepId.getText()+"'";
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "delete Departments by ID");
                }else if(rbDepName.isSelected()){
                  String sql="delete from departement where departementname='"+txtDepName.getText()+"'"; 
                  st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null,  "delete Departments by Name");
                    
                }else{
                    JOptionPane.showMessageDialog(null,  "Please select id or name first");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });


        btnClear.addActionListener(e -> {
            txtDepId.setText("");
            txtDepName.setText("");
            depGroup.clearSelection();
        });
        
        btnBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminPage("Doctor");
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
        btnLogout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new login("");
                setVisible(false);
             }
            
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new DepartmentForm().setVisible(true);
    }
}

