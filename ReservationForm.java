package projectgui2025;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationForm extends JFrame {
    JLabel image, dateLabel, timeLabel, patientIdLabel, doctorIdLabel, notesLabel, title;
    JDateChooser reservationDate;
    JSpinner timeSpinner;
    JTextField patientIdField;
    JComboBox doctorName;
    JTextArea notesArea;
    JButton btnAdd, btnUpdate, btnDelete, btnClear;

    // Nav bar buttons
    JButton btnBack, btnHome, btnSave;

    JPanel panel, navBar;

    ReservationForm() {
        setTitle("ReservationForm");
        setSize(1300, 700);
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
        navBar.setBackground(new Color(0, 0, 0, 200));
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
        title = new JLabel("Reservation Form");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        dateLabel = new JLabel("Reservation Date");
        timeLabel = new JLabel("Reservation Time");
        patientIdLabel = new JLabel("Patient ID");
        doctorIdLabel = new JLabel("Doctor Name");
        notesLabel = new JLabel("Notes");

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        dateLabel.setFont(labelFont);
        timeLabel.setFont(labelFont);
        patientIdLabel.setFont(labelFont);
        doctorIdLabel.setFont(labelFont);
        notesLabel.setFont(labelFont);

        reservationDate = new JDateChooser();
        reservationDate.setDateFormatString("dd/MM/yyyy");

        // Time spinner
        SpinnerDateModel model = new SpinnerDateModel();
        timeSpinner = new JSpinner(model);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date()); // default current time

        patientIdField = new JTextField();
    
        notesArea = new JTextArea();
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);

        // Action buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        doctorName=new JComboBox();
        doctorName.addItem("Select Doctor");
        try {
                Class.forName("com.mysql.jdbc.Driver");
                String myUrl="jdbc:mysql://localhost:3306/cms";
                java.sql.Connection con=(java.sql.Connection)DriverManager.getConnection(myUrl,"root","");
                java.sql.Statement s=(java.sql.Statement) con.createStatement();
                String sql=("select doctorname from doctor");
                ResultSet rs=s.executeQuery(sql);
                while(rs.next()){
                    
                    doctorName.addItem(rs.getString("doctorname"));
                    
                }
             s.close();
             con.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NurseForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(NurseForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reservationDate.setDate(null);
                timeSpinner.setValue(new Date());
                patientIdField.setText("");
                doctorName.setSelectedIndex(0);
                notesArea.setText("");
            }
        });

        btnAdd.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (reservationDate.getDate() == null || patientIdField.getText().isEmpty()
                || doctorName.getSelectedItem()==null|| notesArea.getText().isEmpty()
                    ) {

                JOptionPane.showMessageDialog(null, "Please fill in all required fields ⚠️");
                return;
            }

            Class.forName("com.mysql.jdbc.Driver");
            String myUrl = "jdbc:mysql://localhost:3306/cms";
            Connection con = DriverManager.getConnection(myUrl, "root", "");

            java.sql.Date sqlDate = new java.sql.Date(reservationDate.getDate().getTime());

            Date timeValue = (Date) timeSpinner.getValue();
            java.sql.Time sqlTime = new java.sql.Time(timeValue.getTime());
            
            int patid = Integer.parseInt(patientIdField.getText());
String text = notesArea.getText();
String doctname=doctorName.getSelectedItem().toString();


String depSql = "SELECT doctorid FROM doctor WHERE doctorname=?";
PreparedStatement pstDep = con.prepareStatement(depSql);
pstDep.setString(1, doctname);
ResultSet rsDep = pstDep.executeQuery();

if (rsDep.next()) {
    int doctorid = rsDep.getInt("doctorid");
    String sql1 = "INSERT INTO reservation "
               + "(reservationdate, reservationtime, patientid, doctorid, notes) "
               + "VALUES (?,?,?,?,?)";
    PreparedStatement pst = con.prepareStatement(sql1);
    pst.setDate(1, sqlDate);
    pst.setTime(2, sqlTime);
    pst.setInt(3, patid);
    pst.setInt(4, doctorid);
    pst.setString(5, text);

    pst.executeUpdate();
    JOptionPane.showMessageDialog(null, "Reservation added successfully ✅");
}         


        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Driver not found: " + ex.getMessage());
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Patient ID and Doctor ID must be valid numbers ⚠️");
        }
    }
});
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new ReceptionPage("");
               setVisible(false);
            }
        });
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new HomePage();
               setVisible(false);
            }
        });
        
        // Panel for form
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        // Set bounds
        title.setBounds(450, 10, 300, 30);
        dateLabel.setBounds(50, 50, 200, 30);
        reservationDate.setBounds(240, 50, 200, 30);
        timeLabel.setBounds(500, 50, 200, 30);
        timeSpinner.setBounds(680, 50, 200, 30);
        patientIdLabel.setBounds(50, 110, 200, 30);
        patientIdField.setBounds(240, 110, 200, 30);
        doctorIdLabel.setBounds(500, 110, 200, 30);
        doctorName.setBounds(680, 110, 200, 30);
        notesLabel.setBounds(50, 170, 200, 30);
        notesArea.setBounds(240, 170, 640, 100);

        btnAdd.setBounds(100, 300, 100, 40);
        btnUpdate.setBounds(300, 300, 100, 40);
        btnDelete.setBounds(500, 300, 100, 40);
        btnClear.setBounds(700, 300, 100, 40);

        // Add components to panel
        panel.add(title);
        panel.add(dateLabel);
        panel.add(reservationDate);
        panel.add(timeLabel);
        panel.add(timeSpinner);
        panel.add(patientIdLabel);
        panel.add(patientIdField);
        panel.add(doctorIdLabel);
        panel.add(doctorName);
        panel.add(notesLabel);
        panel.add(notesArea);
        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);

        
        add(panel);
        add(navBar);
        add(image);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ReservationForm();
    }
}