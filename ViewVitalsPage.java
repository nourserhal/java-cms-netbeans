package projectgui2025;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewVitalsPage extends JFrame {
    JTable table;
    JLabel nurseLabel;
    

    public ViewVitalsPage(int patientId, int appointmentId) {
        setTitle("View Vitals");
        setSize(1300, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
      

        // Table
        String[] columns = {"Temperature", "Blood Pressure", "Weight", "Height", "Heart Beat", "Respiration Rate", "Note", "Created At"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);

        // Nurse name label
        nurseLabel = new JLabel("Nurse: N/A");
        nurseLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(nurseLabel, BorderLayout.SOUTH);

        loadVitals(patientId, appointmentId, model);

        setVisible(true);
    }

    private void loadVitals(int patientId, int appointmentId, DefaultTableModel model) {
        try (Connection con = DBHelper.getConnection()) {
            String sql = "SELECT v.*, u.nursename AS nurse_name " +
                         "FROM vitalsigns v " +
                         "LEFT JOIN nurse u ON v.nurseid = u.nurseid " +
                         "WHERE v.patientid=? AND v.appointmentid=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, patientId);
            ps.setInt(2, appointmentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("temperature"),
                    rs.getString("bloodpressure"),
                    rs.getString("weight"),
                    rs.getString("height"),
                    rs.getString("heartrate"),
                    rs.getString("respirationrate"),
                    rs.getString("note"),
                    rs.getTimestamp("created_at")
                    
                });
                nurseLabel.setText("Nurse: " + rs.getString("nurse_name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading vitals.");
        }
    }
private int getNurseIdByUsername(String username) {
        int nurseId = 0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms","root","");
            String sql = "SELECT nurseid FROM nurses WHERE nurseusername = ?";
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
        new ViewVitalsPage(1, 1); // test with patientId=1, appointmentId=1
    }
}