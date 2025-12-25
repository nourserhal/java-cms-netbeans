
package projectgui2025;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame {
    
    HomePage() {
        // === Menu Bar ===
        JMenuBar menu = new JMenuBar();
        JMenu admin = new JMenu("Administration");
        JMenu doct = new JMenu("Doctors");
        JMenu nurse = new JMenu("Nurses");
        JMenu rece = new JMenu("Reception");

        JMenu addct = new JMenu("Manage Users");
     
        JMenuItem upd1 = new JMenuItem("ADD");
        JMenuItem upd2 = new JMenuItem("Update");
        JMenuItem upd3 = new JMenuItem("Delete");
        JMenuItem dct1=new JMenuItem("View Appointement");
        JMenuItem dct2=new JMenuItem(" Report");
        JMenuItem dct3=new JMenuItem("Patient History");
        JMenuItem dct4=new JMenuItem("Medical Lab Request");
        JMenuItem vtls=new JMenuItem("Add Vitals Signs");
        JMenuItem addpa=new JMenuItem("Add Patient");
        JMenuItem takeapp=new JMenuItem("Take Appointement");
        JMenuItem searpat=new JMenuItem("Search Patient");
        JMenuItem vapp=new JMenuItem("View Appointement");

   
        admin.add(addct);
        rece.add(dct1);
        rece.add(searpat);
        addct.add(upd1);
        addct.add(upd2);
        addct.add(upd3);
        doct.add(dct1);
        doct.add(dct2);
        doct.add(dct3);
        doct.add(dct4);
        nurse.add(vtls);
        rece.add(addpa);
        rece.add(takeapp);
        rece.add(vapp);
        rece.add(searpat);
        
        
        admin.setFont(new Font("Arial", Font.BOLD, 18));
        doct.setFont(new Font("Arial", Font.BOLD, 18));
        nurse.setFont(new Font("Arial", Font.BOLD, 18));
        rece.setFont(new Font("Arial", Font.BOLD, 18));
        addct.setFont(new Font("Arial", Font.PLAIN, 16));
      
        upd1.setFont(new Font("Arial", Font.PLAIN, 13));
        upd2.setFont(new Font("Arial", Font.PLAIN, 13));
        upd3.setFont(new Font("Arial", Font.PLAIN, 13));
        dct1.setFont(new Font("Arial", Font.PLAIN, 16));
        dct2.setFont(new Font("Arial", Font.PLAIN, 16));
        dct3.setFont(new Font("Arial", Font.PLAIN, 16));
        dct4.setFont(new Font("Arial", Font.PLAIN, 16));
        vtls.setFont(new Font("Arial", Font.PLAIN, 16));
        addpa.setFont(new Font("Arial", Font.PLAIN, 16));
        takeapp.setFont(new Font("Arial", Font.PLAIN, 16));
        vapp.setFont(new Font("Arial", Font.PLAIN, 16));
        searpat.setFont(new Font("Arial", Font.PLAIN, 16));

        // إضافة القوائم مع مسافات بين الكلمات
        menu.add(admin);
        menu.add(Box.createHorizontalStrut(100)); // مسافة بين الكلمات
        menu.add(doct);
        menu.add(Box.createHorizontalStrut(100));
        menu.add(nurse);
        menu.add(Box.createHorizontalStrut(100));
        menu.add(rece);

        menu.setPreferredSize(new Dimension(0, 40)); // مينيو بار طويل شوي
        this.setJMenuBar(menu);

        // === Background Image ===
        ImageIcon img = new ImageIcon(getClass().getResource("/projectgui2025/Dr-Plus-Clinic-Image-2.jpg"));
        JLabel background = new JLabel(img);
        background.setLayout(new BorderLayout());

        // Panel overlay فوق الصورة
        JPanel overlay = new JPanel(new BorderLayout());
        overlay.setOpaque(false); // شفاف

        // === Title بالنص ===
        /*JLabel title = new JLabel("Clinic Management Information System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.BLACK); 
        overlay.add(title, BorderLayout.NORTH); // بالنص فوق الصورة*/

        // === Button لتحت ===
        JButton btn = new JButton("Click here to continue");
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(Color.WHITE);
        btn.setFocusable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0)); // مسافة من تحت
        buttonPanel.add(btn);

        overlay.add(buttonPanel, BorderLayout.SOUTH);

        // ضيف overlay فوق الصورة
        background.add(overlay, BorderLayout.CENTER);

        // === Frame Settings ===
        this.setContentPane(background);
        setSize(1300, 700);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // === Action ===
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainDashboard();
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
