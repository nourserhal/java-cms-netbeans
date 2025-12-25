package projectgui2025;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

public class AppointmentCheck extends JFrame {
    JLabel image, title, dateLabel;
    JDateChooser dateChooser;
    JButton btnSearch;
    JTable table;
    DefaultTableModel tableModel;
    JButton btnBack, btnHome, btnLogout;
    JPanel panel, navBar;

    private String role;        // "Nurse" or "Doctor"
    private int nurseId;        // logged-in nurse ID

    public AppointmentCheck(String role, int nurseId) {
        this.role = role;
        this.nurseId = nurseId;

        setTitle("Reservation Check");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

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
        title = new JLabel("Check Reservations");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        dateLabel = new JLabel("Select Date:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        btnSearch = new JButton("Search");

        // Table setup
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // only Action column editable
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tableModel.setColumnIdentifiers(new Object[]{
            "Reservation ID", "Patient ID", "Patient Name", "Time", "Doctor Name", "Notes", "Action"
        });

        // Add custom renderer for button
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

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
            new MainDashboard();
            setVisible(false);
        });
        btnLogout.addActionListener(e -> {
            new login(role);
            setVisible(false);
        });
        btnBack.addActionListener(e -> {
            new AdminPage(""); // or previous page
            setVisible(false);
        });

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

            String query =
                "SELECT r.reservationid, r.reservationtime, p.patientid, p.patientname, d.doctorname, r.notes " +
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
                row.add(rs.getInt("patientid"));
                row.add(rs.getString("patientname"));
                row.add(rs.getString("reservationtime"));
                row.add(rs.getString("doctorname"));
                row.add(rs.getString("notes"));
                row.add(role.equals("Nurse") ? "Add Vitals" : "View Vitals");
                tableModel.addRow(row);
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching reservations.");
        }
    }

    // Renderer for Action column
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor for Action column
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) {
                int reservationId = Integer.parseInt(table.getValueAt(row, 0).toString());
                int patientId = Integer.parseInt(table.getValueAt(row, 1).toString());

                if (role.equals("Nurse")) {
                    new VitalsSigns(patientId, reservationId, true, nurseId);
                } else {
                    new VitalsSigns(patientId, reservationId, false, 0); // nurseId not needed for doctor
                }
                setVisible(false);
            }
            clicked = false;
            return label;
        }
    }

    public static void main(String[] args) {
        new AppointmentCheck("Nurse", 1); // Example: nurse with ID=1
    }
}