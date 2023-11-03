package MENU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuWindow extends JFrame {
    public MenuWindow(Boolean isAdmin) {
        ImageIcon logo = new ImageIcon("src/main/resources/MENU/logo1.jpg");
        setTitle("CC-A24-G02-Ankit");
        setSize(800, 600);
        setIconImage(logo.getImage());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        JButton signup = new JButton("Admin Signup");
        signup.setBorder(null);
        signup.setFocusPainted(false);
        signup.setContentAreaFilled(false);
        signup.setPreferredSize(new Dimension(150, 20));
        signup.setFont(new Font("Consolas", 1, 19));

        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginWindow login = new LoginWindow(false);
            }
        });

        //3 main tabs
        OrderHistory orderHistoryPanel = new OrderHistory();
        MenuOrdering menuOrderingPanel = new MenuOrdering(orderHistoryPanel);
        AdminDashboard adminDashboardPanel = new AdminDashboard();

        menuOrderingPanel.setBackground(Color.WHITE);
        orderHistoryPanel.setBackground(Color.WHITE);
        adminDashboardPanel.setBackground(new Color(240, 240, 240));

        tabbedPane.addTab("Menu Ordering", menuOrderingPanel);
        if (isAdmin) {
            tabbedPane.addTab("Order History", orderHistoryPanel);
            tabbedPane.addTab("Admin Dashboard", adminDashboardPanel);
            tabbedPane.addTab("", null);
            tabbedPane.setTabComponentAt(3, signup);
        }

        // tab title size
        tabbedPane.setFont(new Font("Consolas", 1, 19));

        getContentPane().add(tabbedPane);

        tabbedPane.setBackgroundAt(0, new Color(185, 180, 199));
        if (isAdmin) {
            tabbedPane.setBackgroundAt(1, new Color(185, 180, 199));
            tabbedPane.setBackgroundAt(2, new Color(185, 180, 199));
            tabbedPane.setBackgroundAt(3, new Color(185, 180, 199));
        }

        setLocationRelativeTo(null);
        setVisible(true);

        //refresh window every time dispose on save
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
                MenuWindow menu = new MenuWindow(isAdmin);
            }
        });
    }
}
