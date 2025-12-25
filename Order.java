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
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

/**
 *
 * @author HP
 */
public class Order extends JFrame {
    JLabel orderDate,ordername,notes,ordertype,title,image,patientLabel;
    JTextArea note,oname;
    JDateChooser Date;
    JButton save,btnBack,btnHome,btnLogout;
    JPanel navBar,panel;
    JComboBox otype;
    JTextField patientId;
    Order(String username){
        setTitle("Orders");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0,1300, 700);
        image.setLayout(null);
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);
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

        // Title and date search
        title = new JLabel("Order");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        orderDate = new JLabel("Order Date:");
        orderDate.setFont(new Font("Arial", Font.BOLD, 20));
        Date = new JDateChooser();
        Date.setDateFormatString("yyyy-MM-dd");
        ordertype=new JLabel("Order Type:");
        ordertype.setFont(new Font("Arial", Font.BOLD, 20));
        String type[]={"","Lab Test","Radiology","Prescription","Procedure"};
        otype=new JComboBox(type);
        ordername=new JLabel("Order Name:");
        ordername.setFont(new Font("Arial", Font.BOLD, 20));
        oname=new JTextArea();
        notes=new JLabel("Notes:");
        notes.setFont(new Font("Arial", Font.BOLD, 20));
        note=new JTextArea();
        save=new JButton("Save");
        patientLabel = new JLabel("Patient ID:");
        patientLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientId=new JTextField();
        btnBack.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            new DoctorPage("");
            }
            
        });
        btnHome.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            new HomePage();
            }
            
        });
        btnLogout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            new login("");
            }
            
        });
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/cms";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
                java.sql.Statement s=(java.sql.Statement) con.createStatement();
                    int patId=Integer.parseInt(patientId.getText().trim());
                    int doctId=getDoctorIdByUsername(username);
                    int VisitID=getVisitId(patId,doctId);
                    if(doctId==-1){
                        JOptionPane.showMessageDialog(null, "Doctor nut found for username"+username);
                        return;
                    }
                    if(VisitID==0){
                        JOptionPane.showMessageDialog(null, "Not visit found for this patient with the selected doctor");
                        return;
                    }
                    java.util.Date startdate=Date.getDate();
                    
                    java.sql.Date start=new java.sql.Date(startdate.getTime());
                    
                    String sql1 = "INSERT INTO orders "
               + "(patient_id, doctor_id, visit_id, order_type, order_name, notes, order_date) "
               + "VALUES (?,?,?,?,?,?,?)";
    PreparedStatement pst = con.prepareStatement(sql1);
    pst.setInt(1, patId);
    pst.setInt(2, doctId);
    pst.setInt(3, VisitID);
    pst.setString(4, otype.getSelectedItem().toString());
    pst.setString(5, oname.getText());
    pst.setString(6,notes.getText() );
        pst.setDate(7,start );
        int rows=pst.executeUpdate();
        if(rows>0){
            JOptionPane.showMessageDialog(null, "Orders saved successfully");
        }

                    
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "error"+ex.getMessage());
                }
            }
            
        });

        
        title.setBounds(460, 10, 400, 30);
        orderDate.setBounds(50, 50, 200, 30);
        Date.setBounds(200,50,200,30);
        ordertype.setBounds(50,110,200,30);
        otype.setBounds(200,110,200,30);
        notes.setBounds(500,110,400,30);
        note.setBounds(680,110,300,100);
        ordername.setBounds(50,180,400,30);
        oname.setBounds(200,180,300,100);
        patientLabel.setBounds(500, 50, 150, 30);
        patientId.setBounds(680, 50, 300, 30);

        
        save.setBounds(100, 300, 100, 50);
        
        panel.add(title);
        panel.add(orderDate);
        panel.add(Date);
        panel.add(ordertype);
        panel.add(otype);
        panel.add(notes);
        panel.add(note);
        panel.add(ordername);
        panel.add(oname);
        panel.add(patientLabel);
        panel.add(patientId);
        
        panel.add(save);
        
                        

        
        image.add(navBar);
        image.add(panel);
        add(image);
        setVisible(true);
        
        
        
        
        

        
    }
    private int getDoctorIdByUsername(String username){
        int id = -1;
        try{
             Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/cms";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
            String sql = "SELECT doctorid FROM doctor WHERE doctorusername=?";
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            java.sql.ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                id = rs.getInt("doctorid");}
            con.close();
        }catch(Exception ex){
            ex.printStackTrace(); }
        return id;
    }
private int getVisitId(int patID,int doctId) {
        int visitId = 0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
            String sql = "SELECT visit_id FROM medical_visit WHERE patient_id = ? and doctor_id=? order by visit_date desc limit 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, patID);
            ps.setInt(2, doctId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                visitId = rs.getInt("visit_id");
            }
            rs.close();
            ps.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return visitId;
    }
    public static void main(String[] args) {
        new Order("");
    }
            
}

