package projectgui2025;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

public class ViewAppoitement extends JFrame {
    JLabel image, title, dateLabel;
    JDateChooser dateChooser;
    JButton btnSearch;

    JTable table;
    DefaultTableModel tableModel;

    JButton btnBack, btnHome, btnLogout;
    JPanel panel, navBar;

    public ViewAppoitement() {
        setTitle("View Reservations");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background image
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, getWidth(), getHeight());
        image.setLayout(null);

        // Navigation bar
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

        // Title and date search
        title = new JLabel("View Reservations");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        dateLabel = new JLabel("Select Date:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        btnSearch = new JButton("Search");

        // Table setup (no Action column here)
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells read-only
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tableModel.setColumnIdentifiers(new Object[]{
            "Reservation ID", "Date", "Time", "Patient ID", "Patient Name", "Doctor ID", "Doctor Name", "Notes"
        });

        // Layout
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        title.setBounds(400, 10, 400, 30);
        dateLabel.setBounds(50, 50, 200, 30);
        dateChooser.setBounds(250, 50, 200, 30);
        btnSearch.setBounds(480, 50, 100, 30);
        scrollPane.setBounds(50, 100, 1000, 300);

        panel.add(title);
        panel.add(dateLabel);
        panel.add(dateChooser);
        panel.add(btnSearch);
        panel.add(scrollPane);

        // Button actions
        btnSearch.addActionListener(e -> searchReservations());
        btnHome.addActionListener(e -> {
            new HomePage();
            setVisible(false);
        });
        btnLogout.addActionListener(e -> {
            new login("User");
            setVisible(false);
        });
        btnBack.addActionListener(e -> {
            new ReceptionPage("");
            setVisible(false);
        });

        // Add panel and nav
        image.add(navBar);
        image.add(panel);
        add(image);

        setVisible(true);
    }

    private void searchReservations() {
        java.util.Date date = dateChooser.getDate();
        if (date == null) {
            JOptionPane.showMessageDialog(null, "Please select a date.");
            return;
        }
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "");

            // join with patient + doctor to get names
            String query =
                "SELECT r.reservationid, r.reservationdate, r.reservationtime, " +
                "p.patientid, p.patientname, d.doctorid, d.doctorname, r.notes " +
                "FROM reservation r " +
                "JOIN patient p ON r.patientid = p.patientid " +
                "JOIN doctor d ON r.doctorid = d.doctorid " +
                "WHERE r.reservationdate=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, formattedDate);
            ResultSet rs = ps.executeQuery();

            tableModel.setRowCount(0);
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("reservationid"));
                row.add(rs.getString("reservationdate"));
                row.add(rs.getString("reservationtime"));
                row.add(rs.getInt("patientid"));
                row.add(rs.getString("patientname"));
                row.add(rs.getInt("doctorid"));
                row.add(rs.getString("doctorname"));
                row.add(rs.getString("notes"));
                tableModel.addRow(row);
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching reservations.");
        }
    }

    public static void main(String[] args) {
        new ViewAppoitement(); // Example run
    }
}