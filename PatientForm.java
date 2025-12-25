package projectgui2025;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class PatientForm extends JFrame {
    JLabel image, pname, padress, pphone, pregisternb, pgender, pnationality,
            pstatus, pbirth, presedate, pbloodtype, title;
    JTextField name, address, phone, reginb;
    JComboBox status, nationality, gender, bloodtype;
    JDateChooser birth, date;
    JButton btn1, btn2, btn3, btn4;

    // Nav bar buttons
    JButton btnBack, btnHome, btnSave;

    JPanel panel, navBar;

    PatientForm() {
        setTitle("PatientForm");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, getWidth(), getHeight());
        image.setOpaque(true);

        // Navigation bar
        navBar = new JPanel(new GridLayout(1, 3));
        navBar.setBackground(new Color(0, 0, 0, 200)); // semi-transparent black
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
        pname = new JLabel("Patient Name");
        padress = new JLabel("Patient Address");
        pphone = new JLabel("Patient Phone");
        pregisternb = new JLabel("Register Number");
        pgender = new JLabel("Patient Gender");
        pnationality = new JLabel("Patient Nationality");
        pstatus = new JLabel("Patient Status");
        pbirth = new JLabel("Patient Birthday");
        presedate = new JLabel("Patient Reservation Date");
        pbloodtype = new JLabel("Patient Blood Type");
        title = new JLabel("Patient Form");

        birth = new JDateChooser();
        date = new JDateChooser();
        birth.setDateFormatString("dd/MM/yyyy");
        date.setDateFormatString("dd/MM/yyyy");

        name = new JTextField(20);
        address = new JTextField(20);
        phone = new JTextField(20);
        reginb = new JTextField(20);

        String[] stat = {" ", "Single", "Married", "Divorced"};
        String[] natio = {" ", "Lebanon", "Spanish", "France", "America", "Egypt", "Syria", "Iraq", "UAE", "Algeria"};
        String[] gend = {" ", "Male", "Female"};
        String[] bll = {" ", "A+", "B+", "O+", "AB", "A-", "B-", "O-"};

        status = new JComboBox(stat);
        nationality = new JComboBox(natio);
        gender = new JComboBox(gend);
        bloodtype = new JComboBox(bll);

        btn1 = new JButton("Add");
        btn2 = new JButton("Update");
        btn3 = new JButton("Delete");
        btn4 = new JButton("Clear");

        // Fonts
        title.setFont(new Font("Arial", Font.BOLD, 30));
        pname.setFont(new Font("Arial", Font.BOLD, 20));
        padress.setFont(new Font("Arial", Font.BOLD, 20));
        pphone.setFont(new Font("Arial", Font.BOLD, 20));
        pregisternb.setFont(new Font("Arial", Font.BOLD, 20));
        pgender.setFont(new Font("Arial", Font.BOLD, 20));
        pnationality.setFont(new Font("Arial", Font.BOLD, 20));
        pstatus.setFont(new Font("Arial", Font.BOLD, 20));
        presedate.setFont(new Font("Arial", Font.BOLD, 20));
        pbloodtype.setFont(new Font("Arial", Font.BOLD, 20));
        pbirth.setFont(new Font("Arial", Font.BOLD, 20));

        // Panel for form
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        // Bounds for labels and fields
        title.setBounds(450, 10, 300, 30);
        pname.setBounds(50, 50, 200, 30);
        name.setBounds(240, 50, 200, 30);
        padress.setBounds(500, 50, 200, 30);
        address.setBounds(680, 50, 200, 30);
        pphone.setBounds(50, 110, 200, 30);
        phone.setBounds(240, 110, 200, 30);
        pregisternb.setBounds(500, 110, 200, 30);
        reginb.setBounds(680, 110, 200, 30);
        pgender.setBounds(50, 170, 200, 30);
        gender.setBounds(240, 170, 200, 30);
        pnationality.setBounds(500, 170, 200, 30);
        nationality.setBounds(680, 170, 200, 30);
        pstatus.setBounds(50, 230, 200, 30);
        status.setBounds(240, 230, 200, 30);
        pbirth.setBounds(500, 230, 200, 30);
        birth.setBounds(680, 230, 200, 30);
        pbloodtype.setBounds(50, 290, 200, 30);
        bloodtype.setBounds(240, 290, 200, 30);
        presedate.setBounds(500, 290, 250, 30);
        date.setBounds(740, 290, 200, 30);

        // Buttons bounds
        btn1.setBounds(100, 360, 100, 40);
        btn2.setBounds(300, 360, 100, 40);
        btn3.setBounds(500, 360, 100, 40);
        btn4.setBounds(700, 360, 100, 40);

         btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainDashboard();
                setVisible(false);
                
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
                new ReceptionPage("");
                setVisible(false);
                
            }
        });
        
        // Clear button action
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.setText("");
                address.setText("");
                phone.setText("");
                reginb.setText("");
                gender.setSelectedIndex(0);
                status.setSelectedIndex(0);
                nationality.setSelectedIndex(0);
                bloodtype.setSelectedIndex(0);
                birth.setDate(null);
                date.setDate(null);
            }
        });

       btn1.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (name.getText().isEmpty() || address.getText().isEmpty() || phone.getText().isEmpty() 
                || reginb.getText().isEmpty() || birth.getDate() == null || date.getDate() == null) {
                
                JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
                return; 
            }

            Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = DriverManager.getConnection(myUrl, "root", "");

            String sql = "INSERT INTO patient (patientname, patientaddress, patientphone, registernumber, "
                       + "patientgender, patientnationality, patientstatus, patientbirthday, "
                       + "patientregistrationdate, patientbloodtype) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name.getText());
            ps.setString(2, address.getText());
            ps.setString(3, phone.getText());
            ps.setString(4, reginb.getText());
            ps.setString(5, gender.getSelectedItem().toString());
            ps.setString(6, nationality.getSelectedItem().toString());
            ps.setString(7, status.getSelectedItem().toString());
            ps.setDate(8, new java.sql.Date(birth.getDate().getTime()));
            ps.setDate(9, new java.sql.Date(date.getDate().getTime()));
            ps.setString(10, bloodtype.getSelectedItem().toString());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                // جلب الـ Patient ID يلي تولد
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int newPatientId = rs.getInt(1);
                    JOptionPane.showMessageDialog(
                        null, 
                        "Patient has been registered successfully! 🎉\nPatient ID = " + newPatientId,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error while saving patient information ❌");
            ex.printStackTrace();
        }
    }
});
      
        
        // Add components to panel
        panel.add(title);
        panel.add(pname);
        panel.add(name);
        panel.add(padress);
        panel.add(address);
        panel.add(pphone);
        panel.add(phone);
        panel.add(pregisternb);
        panel.add(reginb);
        panel.add(pgender);
        panel.add(gender);
        panel.add(pnationality);
        panel.add(nationality);
        panel.add(pstatus);
        panel.add(status);
        panel.add(pbirth);
        panel.add(birth);
        panel.add(pbloodtype);
        panel.add(bloodtype);
        panel.add(presedate);
        panel.add(date);
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);

        // Add to frame
        add(panel);
        add(navBar);
        add(image);

        setVisible(true);
        
        
    }

    public static void main(String[] args) {
        new PatientForm();
    }
}