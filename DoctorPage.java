package projectgui2025;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorPage extends JFrame {
    JButton btnAppointment, btnReport, btnPatientHistory, btnLabRequest;
    JButton backBtn, homeBtn, logoutBtn;
    int doctorId;

    public DoctorPage(String username) {
        doctorId = getDoctorIdByUsername(username);

        setTitle("Doctor Page");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900,700));

        JLabel bgLabel = new JLabel(new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg")));
        bgLabel.setBounds(0,0,1300,700);

        JPanel navBar = new JPanel(new GridLayout(1,3));
        navBar.setBackground(new Color(0,0,0,180));
        navBar.setBounds(0,0,1300,60);

        backBtn = new JButton("BACK");
        homeBtn = new JButton("HOMEPAGE");
        logoutBtn = new JButton("LOGOUT");

        JButton[] navButtons = {backBtn,homeBtn,logoutBtn};
        for(JButton b: navButtons){
            b.setFont(new Font("Arial",Font.BOLD,18));
            
            b.setFocusable(false);
        }

        navBar.add(backBtn);
        navBar.add(homeBtn);
        navBar.add(logoutBtn);
        backBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainDashboard();
            }
            
        });
        homeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage();
            }
            
        });
        logoutBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new login("");
            }
            
        });

        JPanel centerPanel = new JPanel(new GridLayout(2,2,20,20));
        centerPanel.setOpaque(false);
        centerPanel.setBounds(200,250,900,200);

        btnAppointment = new JButton("APPOINTMENT");
        btnReport = new JButton("REPORT");
        btnPatientHistory = new JButton("PATIENT HISTORY");
        btnLabRequest = new JButton("MEDICAL LAB REQUEST");
        btnReport.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new SickLeave(username);
                setVisible(false);
            }
            
        });
        btnLabRequest.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new Order(username);
                setVisible(false);
            }
            
        });
        btnPatientHistory.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                new PatientHistoryPage();
                setVisible(false);
            }
            
        });
        
        btnAppointment.addActionListener(e -> {
    // Open DoctorAppointmentCheck for this doctor
    new DoctorAppoitementCheck(doctorId,0);
    dispose(); // optional: close the doctor page
});

        JButton[] centerButtons = {btnAppointment, btnReport, btnPatientHistory, btnLabRequest};
        for(JButton b: centerButtons){
            b.setFont(new Font("Arial",Font.BOLD,30));
            b.setBackground(Color.WHITE);
            b.setFocusable(false);
        }

        centerPanel.add(btnAppointment);
        centerPanel.add(btnReport);
        centerPanel.add(btnPatientHistory);
        centerPanel.add(btnLabRequest);

        layeredPane.add(bgLabel, Integer.valueOf(0));
        layeredPane.add(navBar, Integer.valueOf(1));
        layeredPane.add(centerPanel, Integer.valueOf(2));

        setContentPane(layeredPane);
        setVisible(true);
    
    }

    private int getDoctorIdByUsername(String username){
        int id = -1;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
            String sql = "SELECT doctorid FROM doctor WHERE doctorusername=?";
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            java.sql.ResultSet rs = ps.executeQuery();
            if(rs.next()) id = rs.getInt("doctorid");
            con.close();
        }catch(Exception ex){ ex.printStackTrace(); }
        return id;
    }
    public static void main(String[] args) {
        new DoctorPage("");
    }
}