package projectgui2025;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class SearchPatient extends JFrame {
    JLabel image, title, idLabel;
    JTextField patientIdField;
    JButton btnSearch;

    JTable table;
    DefaultTableModel tableModel;

    JButton btnBack, btnHome, btnLogout;
    JPanel panel, navBar;

    public SearchPatient() {
        setTitle("Search Patient");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/cq5dam.web.1440.617.jpeg"));
        image = new JLabel(img);
        image.setBounds(0, 0, 1300, 700);
        image.setLayout(null);

        // Top Nav Bar
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

        // Title & Search Fields
        title = new JLabel("Search Patient");
        title.setFont(new Font("Arial", Font.BOLD, 30));

        idLabel = new JLabel("Patient Name:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 20));
        patientIdField = new JTextField();

        btnSearch = new JButton("Search");

        // Table
        tableModel = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Columns
        tableModel.setColumnIdentifiers(new Object[] {
            "Patient ID", "Name", "Phone", "Address",
            "Gender", "Nationality", "Status", "Birthday",
            "Registration Date", "Blood Type"
        });

        // Layout Panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBounds(50, 100, 1100, 500);

        title.setBounds(450, 10, 400, 30);
        idLabel.setBounds(310, 50, 150, 30);
        patientIdField.setBounds(450, 50, 150, 30);
        btnSearch.setBounds(600, 50, 120, 30);
        scrollPane.setBounds(50, 100, 1000, 330);

        panel.add(title);
        panel.add(idLabel);
        panel.add(patientIdField);
        panel.add(btnSearch);
        panel.add(scrollPane);

        // Button Actions
        btnSearch.addActionListener(e -> searchPatientByName());
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

        image.add(navBar);
        image.add(panel);
        add(image);

        setVisible(true);
    }

    private void searchPatientByName() {
        String pname = patientIdField.getText().trim();

        if (pname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Patient Name ⚠️");
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cms", "root", ""
            );

            String sql =
                "SELECT patientid, patientname, patientphone, patientaddress, " +
                "       patientgender, patientnationality, patientstatus, " +
                "       patientbirthday, patientregistrationdate, patientbloodtype " +
                "FROM patient " +
                "WHERE patientname LIKE ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + pname + "%");
            ResultSet rs = ps.executeQuery();

            // Clear table
            tableModel.setRowCount(0);

            boolean any = false;
            while (rs.next()) {
                any = true;
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("patientid"));
                row.add(rs.getString("patientname"));
                row.add(rs.getString("patientphone"));
                row.add(rs.getString("patientaddress"));
                row.add(rs.getString("patientgender"));
                row.add(rs.getString("patientnationality"));
                row.add(rs.getString("patientstatus"));
                row.add(rs.getString("patientbirthday"));          // ✅ safe
                row.add(rs.getString("patientregistrationdate")); // ✅ safe
                row.add(rs.getString("patientbloodtype"));

                tableModel.addRow(row);
            }

            if (!any) {
                JOptionPane.showMessageDialog(this, "No patient found with this name ❌");
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while searching ❌");
        }
    }

    // Renderer for wrapping text inside JTable cells
    class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            return this;
        }
    }

    public static void main(String[] args) {
        new SearchPatient();
    }
}